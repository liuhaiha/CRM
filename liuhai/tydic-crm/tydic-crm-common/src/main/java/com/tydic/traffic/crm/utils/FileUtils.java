package com.tydic.traffic.crm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * Created by acer on 2017-07-22.
 */
public abstract  class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final int defaultCapacity = 1024 * 1024;

    public static String replacePath(String path){
        String replaPath =  path.replaceAll("[/\\\\]", "/");
        replaPath=replaPath.replaceAll("/{2,}", "/");
        return replaPath;
    }

    public static void copyFile(File targetFile, OutputStream out, long startPos, long endPos) throws IOException {

        if (null == targetFile) {
            return;
        }

        RandomAccessFile sourceFile = null;

        try {
            sourceFile = new RandomAccessFile(targetFile, "r");
            sourceFile.seek(startPos);

            while (startPos < endPos) {

                long length = endPos - startPos;

                int bufferSize = length < defaultCapacity ? (int) length : defaultCapacity;

                byte[] buffer = new byte[bufferSize];

                int size = sourceFile.read(buffer);

                if (size < buffer.length) {
                    out.write(buffer, 0, size);
                }
                else {
                    out.write(buffer, 0, buffer.length);

                }
                startPos += size;
            }

        }finally {
            try {
                if (null != sourceFile) {
                    sourceFile.close();
                }
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
            catch (Exception e) {
               //ignore
            }

        }

    }

}
