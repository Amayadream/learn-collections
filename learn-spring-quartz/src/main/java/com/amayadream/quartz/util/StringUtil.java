package com.amayadream.quartz.util;

import java.util.UUID;

/**
 * @author :  Amayadream
 * @date :  2016.09.26 15:56
 */
public class StringUtil {

    /**
     * 生成32位UUID
     */
    public static String generateGuid(){
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

}
