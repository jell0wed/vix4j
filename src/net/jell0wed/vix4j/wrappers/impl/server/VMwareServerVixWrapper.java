package net.jell0wed.vix4j.wrappers.impl.server;

import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.wrappers.VixVM;
import net.jell0wed.vix4j.wrappers.VixWrapper;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Created by Administrator on 1/22/2015.
 */
public class VMwareServerVixWrapper extends VixWrapper {
    protected VMwareServerVixWrapper(AbstractVixConnection connection) {
        super(connection);
    }

    @Override
    public final VixVM openVM(String url) throws VixException {
        return this.VixVM_Open(url); // vmware player use the old deprecated way to open vm
    }

    @Override
    public final void VixVM_Pause(int vmHandle) throws VixException
    {
        throw new NotImplementedException("VMware Servers products doesn't support pause operations !");
    }
}
