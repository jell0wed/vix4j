package net.jell0wed.vix4j.wrappers.impl.vsphere;

import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.wrappers.VixWrapper;
import org.apache.commons.lang3.NotImplementedException;

import javax.naming.OperationNotSupportedException;

/**
 * Created by Administrator on 1/22/2015.
 */
public class VMwareVSphereVixWrapper extends VixWrapper {
    protected VMwareVSphereVixWrapper(AbstractVixConnection connection) {
        super(connection);
    }

    @Override
    public final void VixVM_Pause(int vmHandle) throws VixException
    {
        throw new NotImplementedException("VMware vSphere products doesn't support pause operations !");
    }
}
