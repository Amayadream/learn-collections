package com.amayadream.rpc.common.utils;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 13:56
 */
public class ZKConstants {

    // 可以配置到数据库或资源文件，此处偷懒采用全局的静态字符串
    // 因我们的引用程序和zookeeper都在一个服务器（其实应该是和应用分开）
    // 因此总有一个localhost
    public final static String connectString = "127.0.0.1:2181";

}
