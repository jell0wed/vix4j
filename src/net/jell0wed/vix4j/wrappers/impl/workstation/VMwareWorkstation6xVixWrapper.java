package net.jell0wed.vix4j.wrappers.impl.workstation;

import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.wrappers.VixVM;
import net.jell0wed.vix4j.wrappers.VixWrapper;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Created by Administrator on 1/22/2015.
 */
public class VMwareWorkstation6xVixWrapper extends VixWrapper {
    protected VMwareWorkstation6xVixWrapper(AbstractVixConnection connection) {
        super(connection);
    }

    @Override
    public final VixVM openVM(String url) throws VixException {
        return this.VixVM_Open(url); // vmware player use the old deprecated way to open vm
    }

    @Override
    public final void VixVM_Pause(int vmHandle) throws VixException
    {
        throw new NotImplementedException("VMware Workstation 6.x products doesn't support pause operations !");
    }
}
