package net.jell0wed.vix4j.wrappers.exceptions;

/**
 * Created by Administrator on 1/22/2015.
 */
public class VixWrapperInvalidOperation extends Exception {
    public VixWrapperInvalidOperation(String msg)
    {
        super(msg);
    }

    public VixWrapperInvalidOperation(String msg, Exception src)
    {
        super(msg, src);
    }
}
