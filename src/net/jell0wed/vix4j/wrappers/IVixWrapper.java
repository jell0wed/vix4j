package net.jell0wed.vix4j.wrappers;

import net.jell0wed.vix4j.exceptions.VixException;

import java.util.Collection;

/**
 * Created by Administrator on 1/22/2015.
 */
public interface IVixWrapper {
    public Collection<String> getRunningVMsUrl() throws VixException;
    public Collection<String> getRegisteredVMsUrl() throws VixException;
    public VixVM openVM(String url) throws VixException;
}
