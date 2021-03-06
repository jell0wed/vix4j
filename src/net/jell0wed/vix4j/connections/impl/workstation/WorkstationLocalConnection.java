package net.jell0wed.vix4j.connections.impl.workstation;

import com.sun.jna.Memory;
import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.vendors.IVixLibrary;
import net.jell0wed.vix4j.wrappers.VixWrapper;

/**
 * Created by Administrator on 1/7/2015.
 */
public final class WorkstationLocalConnection extends AbstractVixConnection
{
    private String connectUsername = null;
    private String connectPassword = null;

    public void setConnectionCredentials(String username, String password)
    {
        this.connectUsername = username;
        this.connectPassword = password;
    }

    @Override
    protected void connectToVixInstance() throws VixException {
        int jobHandle = this.getVixInterface().VixHost_Connect(
                IVixLibrary.VIX_API_VERSION,
                IVixLibrary.VIX_SERVICEPROVIDER_VMWARE_WORKSTATION,
                null,
                0,
                this.connectUsername,
                this.connectPassword,
                0,
                IVixLibrary.VIX_INVALID_HANDLE,
                null,
                null);

        VixWrapper.handleVixJobWithOneResult(this, jobHandle, IVixLibrary.VIX_PROPERTY_JOB_RESULT_HANDLE, (Memory) this.getHostHandlePointer());
    }
}
