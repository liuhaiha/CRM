package com.tydic.test.utils.date;

import com.tydic.traffic.tafa.utils.date.DateUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Think on 2017/6/27.
 */
public class DateUtilsTest {

    public static Logger logger = LoggerFactory.getLogger(DateUtilsTest.class);
    @Test
    public void getMonthMaxDay(){
        String str = DateUtil.getCurrentDate();
        logger.debug("-----------" + str);
        int i = DateUtil.getMonthMaxDay(str);
        logger.debug(i + "");
    }
    @Test
    public void getDaysBetweenTest(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        logger.debug("========="+ simpleDateFormat.format(null));

        int i = DateUtil.getDaysBetween(simpleDateFormat.format(new Date()),"2017-06-22");
        logger.debug("" + i);

    }



}
