package net.jell0wed.vix4j.wrappers;

import net.jell0wed.vix4j.exceptions.VixException;

/**
 * Created by Administrator on 1/30/2015.
 */
public class VixVMSnapshotStatus
{
    private VixVM vmHandle;
    private int rootSnapshotIndex;
    private int rootSnapshotHandle;


    VixVMSnapshotStatus(VixVM _handle, int rootSnapshotIndex) throws VixException {
        this.vmHandle = _handle;
        this.rootSnapshotIndex = rootSnapshotIndex;
        this.rootSnapshotHandle = this.vmHandle.getWrapper().VixVM_GetRootSnapshot(this.vmHandle.getVMHandleId(), rootSnapshotIndex
        );
    }

}
