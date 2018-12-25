package com.tydic.traffic.tafa.utils.uuid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by acer on 2017-06-12.
 */
public abstract class UUIDUtil {

    private static final Logger logger= LoggerFactory.getLogger(UUIDUtil.class);

    /**
     * 获取原生态的uuid
     * @param toLowerCase
     * @return
     */
    public static String getNativeUUID(boolean toLowerCase){
        String uuid=UUID.randomUUID().toString().replaceAll("-","");
        if(toLowerCase){
            uuid=uuid.toLowerCase();
        }

        return uuid;
    }

    /**
     * 获取由当前时间和随机字符串组成的uuid字符串
     * @return
     */
    public static  String getUUID() {
        long time = System.currentTimeMillis();

        String timePattern = Long.toHexString(time);
        int leftBit = 14 - timePattern.length();
        if (leftBit > 0) {
            timePattern = "0000000000".substring(0, leftBit) + timePattern;
        }

        String uuid = timePattern + Long.toHexString(Double.doubleToLongBits(Math.random()))
                + Long.toHexString(Double.doubleToLongBits(Math.random())) + "000000000000000000";

        uuid = uuid.substring(0, 32).toUpperCase();

        if(logger.isDebugEnabled()){
            logger.debug("UUID generation has complete:" + uuid);
        }

        return uuid;
    }
}
