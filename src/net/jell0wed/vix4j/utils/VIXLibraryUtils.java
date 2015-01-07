package net.jell0wed.vix4j.utils;

import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.vendors.IVixLibrary;
import org.apache.log4j.Logger;


/**
 * Created by Administrator on 1/7/2015.
 */
public class VIXLibraryUtils {
    public static boolean passivelyHandleVixJob(AbstractVixConnection connection, int jobHandle)
    {
        try
        {
            handleVixJob(connection, jobHandle);
            return true;
        }
        catch(VixException e)
        {
            Logger.getLogger(AbstractVixConnection.class).debug("Catched VIX Error while validating job handle.", e);
            return false;
        }
    }

    public static void handleVixJob(AbstractVixConnection connection, int jobHandle) throws VixException
    {
        int errCode = connection.getVixInterface().VixJob_Wait(jobHandle,
                IVixLibrary.VIX_PROPERTY_JOB_RESULT_HANDLE,
                connection.getHostHandle(),
                IVixLibrary.VIX_PROPERTY_NONE);

        if(errCode != IVixLibrary.VIX_OK)
        {
            String vixErrorStr = connection.getVixInterface().Vix_GetErrorText(errCode, null);
            throw new VixException(String.format("Error while executing VIX command : %s", vixErrorStr));
        }
    }
}
