package net.jell0wed.vix4j.wrappers;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.utils.NativeSizes;
import net.jell0wed.vix4j.utils.VIXLibraryLoader;
import net.jell0wed.vix4j.vendors.IVixEventProcCallback;
import net.jell0wed.vix4j.vendors.IVixLibrary;
import org.apache.log4j.Logger;

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

    public VixVM openVM(String url) throws VixException {
        Pointer vmHandleId = new Memory(NativeSizes.NATIVE_INT_BYTES_SIZE);
        int jobHandle = this.currentVix.VixHost_OpenVM(this.currentConnection.getHostHandle(),
                url,
                IVixLibrary.VIX_VMOPEN_NORMAL,
                IVixLibrary.VIX_INVALID_HANDLE,
                null,
                null);

        handleVixJobWithOneResult(this.currentConnection, jobHandle, IVixLibrary.VIX_PROPERTY_JOB_RESULT_HANDLE, (Memory) vmHandleId);

        return new VixVM(this, url, vmHandleId.getInt(0));
    }

    // ---

    private boolean asyncCheckJobCompletion(int jobHandle) throws VixException {
        IntByReference completionPtr = new IntByReference();
        int checkJobHandle = this.currentVix.VixJob_CheckCompletion(jobHandle, completionPtr);
        handleVixJob(this.currentConnection, checkJobHandle);

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
                        if(!passivelyHandleVixJob(currentConnection, jobHandle))
                        {
                            return;
                        }

                        String retrievedUrl = new String(urlPtr.getValue().getString(0));
                        itemsResult.add(retrievedUrl);
                        currentVix.Vix_FreeBuffer(urlPtr.getValue());
                    }
                }, null);

        handleVixJob(this.currentConnection, jobHandle);
        return itemsResult;
    }

    //
    // TODO : check if it's actually a good idea to implement those methods in static methods ... maybe they should be part of the vixwrapper instance instead
    // it's not that big of a deal for now since the VixJob_wait is available on any platform supported by vix... but may not be the best practice out there ...
    public static void handleVixJobWithOneResult(AbstractVixConnection connection, int jobHandle, int resultType, Memory resultPtr) throws VixException
    {
        int errCode;
        if(resultPtr == null)
        {
            errCode = connection.getVixInterface().VixJob_Wait(jobHandle,
                    IVixLibrary.VIX_PROPERTY_NONE);
        }
        else
        {
            errCode = connection.getVixInterface().VixJob_Wait(jobHandle,
                    resultType,
                    resultPtr,
                    IVixLibrary.VIX_PROPERTY_NONE);
        }


        if(errCode != IVixLibrary.VIX_OK)
        {
            String vixErrorStr = connection.getVixInterface().Vix_GetErrorText(errCode, null);
            throw new VixException(String.format("Error while executing VIX command : %s", vixErrorStr));
        }
    }

    public static boolean passivelyHandleVixJobWithOneResult(AbstractVixConnection connection, int jobHandle, int resultType, Memory resultPtr)
    {
        try
        {
            handleVixJobWithOneResult(connection, jobHandle, resultType, resultPtr);
            return true;
        }
        catch(VixException e)
        {
            Logger.getLogger(AbstractVixConnection.class).debug("Catched VIX Error while validating job handle.", e);
            return false;
        }
    }

    public static void handleVixJob(AbstractVixConnection connection, int jobHandle) throws VixException
    {
        handleVixJobWithOneResult(connection, jobHandle, -1, null);
    }

    public static boolean passivelyHandleVixJob(AbstractVixConnection connection, int jobHandle)
    {
        return passivelyHandleVixJobWithOneResult(connection, jobHandle, -1, null);
    }
}
