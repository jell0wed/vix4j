package net.jell0wed.vix4j.utils;

import com.sun.jna.Native;

/**
 * Created by Administrator on 1/7/2015.
 */
public class NativeSizes
{
    public static final int NATIVE_BYTE_BYTES_SIZE = 1;
    public static final int NATIVE_SHORT_BYTES_SIZE = 2;
    public static final int NATIVE_INT_BYTES_SIZE = 4;
    public static final int NATIVE_LONG_BYTES_SIZE = 6;

    public static int sizeof(byte b) { return NATIVE_BYTE_BYTES_SIZE; }
    public static int sizeof(short s) { return NATIVE_SHORT_BYTES_SIZE; }
    public static int sizeof(int i) { return NATIVE_INT_BYTES_SIZE; }
    public static int sizeof(long i) { return NATIVE_LONG_BYTES_SIZE; }
    public static int sizeof(Object obj) { return Native.getNativeSize(obj.getClass()); }
}
