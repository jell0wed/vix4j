package net.jell0wed.vix4j.wrappers.impl.server;

import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.wrappers.VixVM;
import net.jell0wed.vix4j.wrappers.VixWrapper;

/**
 * Created by Administrator on 1/22/2015.
 */
public class VMwareServerVixWrapper extends VixWrapper {
    protected VMwareServerVixWrapper(AbstractVixConnection connection) {
        super(connection);
    }

    @Override
    public final VixVM openVM(String url)
    {
        throw new UnsupportedOperationException("VMWare server products cannot open VM !");
    }
}
