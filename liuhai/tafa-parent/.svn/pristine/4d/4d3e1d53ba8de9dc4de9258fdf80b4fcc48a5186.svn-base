package com.tydic.test.utils.office;

import com.tydic.traffic.tafa.utils.date.DateUtil;
import com.tydic.traffic.tafa.utils.office.ExcelUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Think on 2017/6/27.
 */
public class ExcelUtilsTest {
    @Test
    public void testExportStringStringArrayStringArrayCollectionOfQextendsObjectOutputStreamString() throws Exception {
        String[] headStr = new String[3];
        headStr[0] = "city";
        headStr[1] = "street";
        headStr[2] = "create_time";

        String[] fileds = new String[3];
        fileds[0] = "city";
        fileds[1] = "street";
        fileds[2] = "create_time";

        List<Address> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Address address = new Address();
            address.setCity("深圳" + i);
            address.setStreet("深南大道" + i + "号");

            Date date = new Date();
            address.setCreate_time(date);
            list.add(address);
        }

        FileOutputStream fileOutputStream = new FileOutputStream("D://address.xls");
        ExcelUtils utils = new ExcelUtils();
        //utils.export("地址", headStr, fileds, list, fileOutputStream, "yyyy-MM-DD");
        utils.export("地址", headStr, fileds, list, fileOutputStream, "");
    }
}
