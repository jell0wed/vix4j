package net.jell0wed.vix4j.wrappers;

import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.wrappers.impl.enums.VMPowerOperationTypes;

/**
 * Created by Administrator on 1/12/2015.
 */
public class VixVM {

    private int vmHandleId;
    private VixWrapper wrapperInstance;

    VixVM(VixWrapper instance, String url, int handleId)
    {
        this.wrapperInstance = instance;
        this.vmHandleId = handleId;
    }

    public void powerOn(VMPowerOperationTypes type) throws VixException {
        this.wrapperInstance.VixVM_PowerOn(this.vmHandleId, type);
    }

    public void powerOn() throws VixException {
        this.powerOn(VMPowerOperationTypes.NORMAL);
    }

    public void powerOff(boolean force) throws VixException
    {
        this.wrapperInstance.VixVM_PowerOff(this.vmHandleId, force == true ? VMPowerOperationTypes.NORMAL : VMPowerOperationTypes.FROM_GUEST);
    }

}
