package net.jell0wed.vix4j.exceptions;

/**
 * Created by Administrator on 1/7/2015.
 */
public class VixException extends Exception {
    public VixException(String msg, Throwable src)
    {
        super(msg, src);
    }

    public VixException(String msg)
    {
        super(msg);
    }
}
