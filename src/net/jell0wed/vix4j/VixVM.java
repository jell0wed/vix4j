package net.jell0wed.vix4j;

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

}
