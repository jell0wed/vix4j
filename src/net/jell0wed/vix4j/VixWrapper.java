package net.jell0wed.vix4j;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.utils.NativeSizes;
import net.jell0wed.vix4j.utils.VIXLibraryLoader;
import net.jell0wed.vix4j.utils.VIXLibraryUtils;
import net.jell0wed.vix4j.vendors.IVixEventProcCallback;
import net.jell0wed.vix4j.vendors.IVixLibrary;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Administrator on 1/12/2015.
 */
public abstract class VixWrapper
{
    private AbstractVixConnection currentConnection;
    private IVixLibrary currentVix;

    protected VixWrapper(AbstractVixConnection connection)
    {
        this.currentConnection = connection;
        this.currentVix = VIXLibraryLoader.getLibraryInstance();
    }

    AbstractVixConnection getConnection()
    {
        return this.currentConnection;
    }

    IVixLibrary getVixLibrary()
    {
        return this.currentVix;
    }

    public Collection<String> getRunningVMsUrl() throws VixException {
        return this.findItemsByParam(IVixLibrary.VIX_FIND_RUNNING_VMS);
    }

    public Collection<String> getRegisteredVMsUrl() throws VixException {
        return this.findItemsByParam(IVixLibrary.VIX_FIND_REGISTERED_VMS);
    }

    /*public VixVM openVM(String url)
    {
        Pointer vmHandleId = new Memory(NativeSizes.NATIVE_INT_BYTES_SIZE);
        int jobHandle = this.currentVix.VixHost_OpenVM(this.currentConnection.getHostHandle(),
                url,
                IVixLibrary.VIX_VMOPEN_NORMAL,
                IVixLibrary.VIX_INVALID_HANDLE,
                null,
                null);

    }*/

    // ---

    private boolean asyncCheckJobCompletion(int jobHandle) throws VixException {
        IntByReference completionPtr = new IntByReference();
        int checkJobHandle = this.currentVix.VixJob_CheckCompletion(jobHandle, completionPtr);
        VIXLibraryUtils.handleVixJob(this.currentConnection, checkJobHandle);

        return completionPtr.getValue() == 1;
    }

    private Collection<String> findItemsByParam(int param) throws VixException {
        final Collection<String> itemsResult = new LinkedList<String>();

        int jobHandle = this.currentVix.VixHost_FindItems(this.currentConnection.getHostHandle(),
                param,
                IVixLibrary.VIX_INVALID_HANDLE,
                -1,
                new IVixEventProcCallback() {
                    @Override
                    public void VixEventProc(int handle, int eventType, int moreEventInfo, Pointer clientData) {
                        PointerByReference urlPtr = new PointerByReference();

                        if(eventType != IVixLibrary.VIX_EVENTTYPE_FIND_ITEM)
                        {
                            return;
                        }

                        int jobHandle = currentVix.Vix_GetProperties(moreEventInfo, IVixLibrary.VIX_PROPERTY_FOUND_ITEM_LOCATION, urlPtr, IVixLibrary.VIX_PROPERTY_NONE);
                        if(!VIXLibraryUtils.passivelyHandleVixJob(currentConnection, jobHandle))
                        {
                            return;
                        }

                        String retrievedUrl = new String(urlPtr.getValue().getString(0));
                        itemsResult.add(retrievedUrl);
                        currentVix.Vix_FreeBuffer(urlPtr.getValue());
                    }
                }, null);

        VIXLibraryUtils.handleVixJob(this.currentConnection, jobHandle);
        return itemsResult;
    }
}
