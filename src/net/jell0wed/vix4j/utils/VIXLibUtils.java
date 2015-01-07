package net.jell0wed.vix4j.utils;

import com.sun.jna.Native;
import net.jell0wed.vix4j.vendors.IVixLibrary;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;


/**
 * Created by Administrator on 1/7/2015.
 */
public class VIXLibUtils
{
    public static final String DEFAULT_WINDOWS_x86_VIX_PATH = "C:\\Program Files\\VMware\\VMware VIX";
    public static final String DEFAULT_WINDOWS_x64_VIX_PATH = "C:\\Program Files (x86)\\VMware\\VMware VIX";
    public static final String DEFAULT_LINUX_VIX_PATH = "/usr/lib/vmware-vix";

    public static final String WINDOWS_x86_LIBRARY_NAME = "Vix86AllProductsDyn";
    public static final String WINDOWS_x64_LIBRARY_NAME = "Vix64AllProductsDyn";
    public static final String LINUX_LIBRARY_NAME = "vixAllProducts";

    private static String loadedLibraryPath = null;
    private static String libraryName;

    private static Logger LOG = Logger.getRootLogger();
    private static IVixLibrary currentInstance = null;

    static
    {
        if(SystemUtils.IS_OS_WINDOWS)
        {
            libraryName = SystemUtils.OS_ARCH.contains("x64") ? WINDOWS_x64_LIBRARY_NAME : WINDOWS_x86_LIBRARY_NAME;
        }
        else if(SystemUtils.IS_OS_LINUX)
        {
            libraryName = LINUX_LIBRARY_NAME;
        } else { throw new RuntimeException("Your OS is currently not supported by the VIX4j Wrapper."); }
    }

    public static IVixLibrary getLibraryInstance()
    {
        if(currentInstance == null)
        {
            currentInstance = loadLibrary();
        }

        return currentInstance;
    }

    private static void setJnaLibraryPath()
    {
        String libraryPath = "";
        if(SystemUtils.IS_OS_WINDOWS)
        {
            libraryPath = SystemUtils.OS_ARCH.contains("x64") ? DEFAULT_WINDOWS_x64_VIX_PATH : DEFAULT_WINDOWS_x86_VIX_PATH;
        }
        else if(SystemUtils.IS_OS_LINUX)
        {
            libraryPath = DEFAULT_LINUX_VIX_PATH;
        }

        if(!libraryPath.isEmpty())
        {
            LOG.debug(String.format("Setting JNA library path to '%s'", libraryPath));
            System.setProperty("jna.library.path", libraryPath);
        }
    }

    private static IVixLibrary loadLibrary()
    {
        LOG.debug(String.format("Loading native '%s' library.", libraryName));
        setJnaLibraryPath();
        return (IVixLibrary) Native.loadLibrary(libraryName, IVixLibrary.class);
    }


}
