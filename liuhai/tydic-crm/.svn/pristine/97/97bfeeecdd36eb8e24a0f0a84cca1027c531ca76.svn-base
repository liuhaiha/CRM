package com.tydic.traffic.crm.core.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by acer on 2017-07-22.
 */
@ConfigurationProperties(prefix = "tafa.uploadFile")
public class FileUploadProperties {

    /**
     * Enable support of multipart uploads.
     */
    private boolean enabled = true;

    /**上传时临时文件存放目录*/
    private String tmpLocation;

    /**文件刷盘的阈值*/
    private Integer fileSizeThreshold;

    /**允许上文件最大值*/
    private Integer maxFileSize;

    /**文件存放路径*/
    private String storeLocation;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTmpLocation() {
        return tmpLocation;
    }

    public void setTmpLocation(String tmpLocation) {
        this.tmpLocation = tmpLocation;
    }

    public Integer getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    public void setFileSizeThreshold(Integer fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }

    public Integer getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(Integer maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }
}
