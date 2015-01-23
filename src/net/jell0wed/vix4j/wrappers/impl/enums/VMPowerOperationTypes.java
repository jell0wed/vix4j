package net.jell0wed.vix4j.wrappers.impl.enums;

import net.jell0wed.vix4j.vendors.IVixLibrary;

/**
 * Created by Administrator on 1/22/2015.
 */
public enum VMPowerOperationTypes {
    NORMAL(IVixLibrary.VIX_VMPOWEROP_NORMAL),
    FROM_GUEST(IVixLibrary.VIX_VMPOWEROP_FROM_GUEST),
    SUPRESS_SNAPSHOT_UPON_POWERON(IVixLibrary.VIX_VMPOWEROP_SUPPRESS_SNAPSHOT_POWERON),
    LAUNCH_GUI(IVixLibrary.VIX_VMPOWEROP_LAUNCH_GUI),
    LAUNCH_PAUSED(IVixLibrary.VIX_VMPOWEROP_START_VM_PAUSED);

    private int value;

    private VMPowerOperationTypes(int val) {
        this.value = val;
    }

    public int getValue()
    {
        return this.value;
    }
}
