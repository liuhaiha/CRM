package com.tydic.traffic.crm.codeunit;

/**
 * Created by acer on 2017-07-22.
 */
public interface ErrorCodeUnit {
    public static final String prefix="crm:";

    /**文件上传失败*/
    public static final String UPLOAD_ERR=prefix+"100000001";
    /**新增文件上传记录失败*/
    public static final String ADD_UPLOAD_RECORD_ERR=prefix+"100000002";
    /**项目不存在*/
    public static final String CST_PROJ_NOT_EXISIT=prefix+"100000003";
    /**下载文件失败*/
    public static final String DLN_FILE_FAIL=prefix+"100000004";
    /**文件不存在*/
    public static final String DLN_FILE_NOT_EXISIT=prefix+"100000005";
    /**下载异常*/
    public static final String DLN_FILE_EXC=prefix+"100000006";
}
