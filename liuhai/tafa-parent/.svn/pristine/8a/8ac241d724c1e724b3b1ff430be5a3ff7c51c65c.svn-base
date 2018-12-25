package com.tydic.test.utils.charset;

import com.tydic.traffic.tafa.utils.charset.CharsetUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by Think on 2017/6/26.
 */
public class CharsetUtilsTest {
    public static Logger logger = LoggerFactory.getLogger(CharsetUtilsTest.class);
    @Test
    public void testcChangeCharset(){
        String str = "HELLO";
        try {
            String newStr = CharsetUtils.changeCharset(str,CharsetUtils.GB2312);
            logger.debug("打印信息" + newStr);
            String willStr = CharsetUtils.changeCharset(newStr,CharsetUtils.GB2312,CharsetUtils.ISO_8859_1);
            logger.debug("打印最新的信息" + willStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
