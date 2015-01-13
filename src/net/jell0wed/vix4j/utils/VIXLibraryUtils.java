package net.jell0wed.vix4j.utils;

import com.sun.jna.Memory;
import net.jell0wed.vix4j.connections.AbstractVixConnection;
import net.jell0wed.vix4j.exceptions.VixException;
import net.jell0wed.vix4j.vendors.IVixLibrary;
import org.apache.log4j.Logger;


/**
 * Created by Administrator on 1/7/2015.
 */
public class VIXLibraryUtils {

    public static void handleVixJobWithOneResult(AbstractVixConnection connection, int jobHandle, int resultType, Memory resultPtr) throws VixException
    {
        int errCode;
        if(resultPtr == null)
        {
            errCode = connection.getVixInterface().VixJob_Wait(jobHandle,
                    IVixLibrary.VIX_PROPERTY_NONE);
        }
        else
        {
            errCode = connection.getVixInterface().VixJob_Wait(jobHandle,
                    resultType,
                    resultPtr,
                    IVixLibrary.VIX_PROPERTY_NONE);
        }


        if(errCode != IVixLibrary.VIX_OK)
        {
            String vixErrorStr = connection.getVixInterface().Vix_GetErrorText(errCode, null);
            throw new VixException(String.format("Error while executing VIX command : %s", vixErrorStr));
        }
    }

    public static boolean passivelyHandleVixJobWithOneResult(AbstractVixConnection connection, int jobHandle, int resultType, Memory resultPtr)
    {
        try
        {
            handleVixJobWithOneResult(connection, jobHandle, resultType, resultPtr);
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
        handleVixJobWithOneResult(connection, jobHandle, -1, null);
    }

    public static boolean passivelyHandleVixJob(AbstractVixConnection connection, int jobHandle)
    {
        return passivelyHandleVixJobWithOneResult(connection, jobHandle, -1, null);
    }
}
