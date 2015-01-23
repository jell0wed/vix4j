package net.jell0wed.vix4j.wrappers.impl.workstation.shared;

import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.wrappers.impl.workstation.VMwareWorkstationVixWrapper;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Created by Administrator on 1/22/2015.
 */
public class VMwareSharedWorkstationVixWrapper extends VMwareWorkstationVixWrapper {
    protected VMwareSharedWorkstationVixWrapper(AbstractVixConnection connection) {
        super(connection);
    }

    @Override
    public final void VixVM_Pause(int vmHandle) throws VixException
    {
        throw new NotImplementedException("VMware shared workstation products doesn't support pause operations !");
    }
}
