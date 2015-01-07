package net.jell0wed.vix4j.connections.impl.workstation;

import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.utils.VIXLibraryUtils;
import net.jell0wed.vix4j.vendors.IVixLibrary;

/**
 * Created by Administrator on 1/7/2015.
 */
public final class WorkstationSharedConnection extends AbstractVixConnection {

    private String connectHostname;
    private int connectPort = 0;
    private String connectUsername = null;
    private String connectPassword = null;

    public WorkstationSharedConnection(String hostname, int port)
    {
        this.connectHostname = hostname;
        this.connectPort = port;
    }

    public WorkstationSharedConnection(String hostname)
    {
        new WorkstationSharedConnection(hostname, 0);
    }

    public void setConnectionCredentials(String username, String password)
    {
        this.connectUsername = username;
        this.connectPassword = password;
    }

    @Override
    protected void connectToVixInstance() throws VixException {
        int jobHandle = this.getVixInterface().VixHost_Connect(
                IVixLibrary.VIX_API_VERSION,
                IVixLibrary.VIX_SERVICEPROVIDER_VMWARE_WORKSTATION_SHARED,
                this.connectHostname,
                this.connectPort,
                this.connectUsername,
                this.connectPassword,
                0,
                IVixLibrary.VIX_INVALID_HANDLE,
                null,
                null);

        VIXLibraryUtils.handleVixJob(this, jobHandle);
    }
}
