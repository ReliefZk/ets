package com.reliefzk.middleware.ets.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {

    public static String getCurrMachineIP(){
        InetAddress inet = null;

        try {
            inet = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return null;
        }

        return inet.getHostAddress();
    }

}
