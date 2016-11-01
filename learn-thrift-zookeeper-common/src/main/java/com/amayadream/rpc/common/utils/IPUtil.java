package com.amayadream.rpc.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 16:37
 */
public class IPUtil {

    public static String IP() {

        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                // System.out.println(netInterface.getName());
                if ("lo".equals(netInterface.getName()))
                    continue;
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        // System.out.println("本机的IP = " + ip.getHostAddress());
                        // if (ip.getHostAddress().indexOf("192") >= 0) {
                        return ip.getHostAddress();
                        // }
                    }
                }
            }
        } catch (Exception e) {
            return "127.0.0.1";
        }
        return "127.0.0.1";

    }

    public static void main(String[] args) {
        System.out.println("本机的IP = " + IPUtil.IP());
    }

}
