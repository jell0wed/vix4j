package net.jell0wed.vix4j.wrappers.exceptions;

/**
 * Created by Administrator on 1/22/2015.
 */
public class VixWrapperException extends Exception {
    public VixWrapperException(String msg)
    {
        super(msg);
    }

    public VixWrapperException(String msg, Exception src)
    {
        super(msg, src);
    }
}
