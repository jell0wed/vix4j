package net.jell0wed.vix4j.connections;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.utils.NativeSizes;
import net.jell0wed.vix4j.utils.VIXLibraryLoader;
import net.jell0wed.vix4j.vendors.IVixLibrary;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 1/7/2015.
 */
public abstract class AbstractVixConnection
{
    private static Logger LOG = Logger.getLogger(AbstractVixConnection.class);
    private IVixLibrary vixInstance;
    private Pointer hostHandlePtr;

    protected AbstractVixConnection()
    {
        this.vixInstance = VIXLibraryLoader.getLibraryInstance();
        this.hostHandlePtr = this.initializeHostHandlePtr();
        LOG.debug("Initializing VIX Connection : created VIX host handle pointer.");
    }

    public void connect() throws VixException {
        LOG.debug("Connecting to VIX Host.");
        this.connectToVixInstance();
    }

    protected abstract void connectToVixInstance() throws VixException;
    protected Pointer getHostHandlePointer() { return this.hostHandlePtr; }

    public void disconnect()
    {
        LOG.debug("Disconnecting from VIX Host.");
        this.vixInstance.VixHost_Disconnect(this.getHostHandle());
        this.vixInstance.Vix_ReleaseHandle(this.getHostHandle());
        this.hostHandlePtr = this.initializeHostHandlePtr();
        LOG.debug("Disconnected : realeased VIX Handle");
    }

    public IVixLibrary getVixInterface()
    {
        return this.vixInstance;
    }

    public int getHostHandle()
    {
        return this.hostHandlePtr.getInt(0);
    }

    private Pointer initializeHostHandlePtr()
    {
        return new Memory(NativeSizes.NATIVE_INT_BYTES_SIZE);
    }
}
