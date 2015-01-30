package net.jell0wed.vix4j.wrappers;

import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.vendors.IVixLibrary;
import net.jell0wed.vix4j.wrappers.exceptions.VixWrapperInvalidOperation;
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

    public void powerOn(VMPowerOperationTypes type) throws VixException
    {
        this.wrapperInstance.VixVM_PowerOn(this.vmHandleId, type);
    }

    public void powerOn() throws VixException, VixWrapperInvalidOperation {
        if(this.isPoweredOn()) { throw new VixWrapperInvalidOperation("You are attempting to power on a vm which is already powered on. PowerOff() it first."); }
        this.powerOn(VMPowerOperationTypes.NORMAL);
    }

    public void powerOff(boolean force) throws VixException, VixWrapperInvalidOperation {
        if(this.isPoweredOff()) { throw new VixWrapperInvalidOperation("You are attempting to power off a vm which is already powered off. PowerOn() it first."); }
        this.wrapperInstance.VixVM_PowerOff(this.vmHandleId, force ? VMPowerOperationTypes.NORMAL : VMPowerOperationTypes.FROM_GUEST);
    }

    public void reset(boolean force) throws VixException, VixWrapperInvalidOperation {
        if(this.isPoweredOff()) { throw new VixWrapperInvalidOperation("You are attempting to reset a vm which is not powered on. PowerOn() it first."); }
        this.wrapperInstance.VixVM_Reset(this.vmHandleId, force ? VMPowerOperationTypes.NORMAL : VMPowerOperationTypes.FROM_GUEST);
    }

    public void pause() throws VixException, VixWrapperInvalidOperation {
        if(this.isPoweredOff()) { throw new VixWrapperInvalidOperation("You are attempting to pause a vm which is not powered on. PowerOn() it first."); }
        if(this.isPaused()) { throw new VixWrapperInvalidOperation("You are attempting to pause a vm which is already paused. Unpause() it first."); }
        this.wrapperInstance.VixVM_Pause(this.vmHandleId);
    }

    public void resume() throws VixException, VixWrapperInvalidOperation {
        if(!this.isPoweredOn()) { throw new VixWrapperInvalidOperation("You are attempting to reset a vm which is not powered on."); }
        this.wrapperInstance.VixVM_UnPause(this.vmHandleId);
    }

    public void suspend() throws VixException
    {
        // TODO : check if vm is powered on
        this.wrapperInstance.VixVM_Suspend(this.vmHandleId);
    }

    public void deleteVM() throws VixException
    {
        this.wrapperInstance.VixVM_Delete(this.vmHandleId);
    }

    public void createSnapshot(String name, String description) throws VixException {
        this.createSnapshot(name, description, true);
    }

    public void createSnapshot(String name, String description, boolean includeMemory) throws VixException {
        this.wrapperInstance.VixVM_CreateSnapshot(this.vmHandleId, name, description, includeMemory);
    }

    public boolean isPoweredOn() throws VixException
    {
        return this.wrapperInstance.getPropertyValueAsInteger(this.vmHandleId, IVixLibrary.VIX_PROPERTY_VM_POWER_STATE) == IVixLibrary.VIX_POWERSTATE_POWERED_ON;
    }

    public boolean isPoweredOff() throws VixException
    {
        return this.wrapperInstance.getPropertyValueAsInteger(this.vmHandleId, IVixLibrary.VIX_PROPERTY_VM_POWER_STATE) == IVixLibrary.VIX_POWERSTATE_POWERED_OFF;
    }

    public boolean isPaused() throws VixException
    {
        return this.wrapperInstance.getPropertyValueAsInteger(this.vmHandleId, IVixLibrary.VIX_PROPERTY_VM_POWER_STATE) == IVixLibrary.VIX_POWERSTATE_PAUSED;
    }



}
