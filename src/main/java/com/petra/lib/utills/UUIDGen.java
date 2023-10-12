package com.petra.lib.utills;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDGen {

    private static final long MAC;

    static{
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
            byte[] hardwareAddress = ni.getHardwareAddress();
            ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
            buffer.put(hardwareAddress);
            buffer.flip();
            MAC = buffer.getLong();
        } catch (UnknownHostException | SocketException e) {
            throw new Error(e);
        }
    }

    public synchronized static UUID getUUID(){
        long most64SigBits = get64MostSignificantBitsForVersion1();
        return new UUID(most64SigBits, MAC);
    }


    private static long get64MostSignificantBitsForVersion1() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        return time_low | time_mid | version | time_hi;
    }
}
