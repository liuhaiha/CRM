package com.tydic.traffic.crm.core.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;

/**
 * 自动注入apache文件上传组件
 * @author acer
 * @since 2017-07-22
 */
@Configuration
@ConditionalOnClass({Servlet.class,FileUploadBase.class})
@ConditionalOnProperty(prefix = "tafa.uploadFile", name = "enabled", matchIfMissing = false)
@EnableConfigurationProperties(FileUploadProperties.class)
public class ApacheFileUploadAutoConfiguration {

    private FileUploadProperties fileUploadProperties;

    public ApacheFileUploadAutoConfiguration(FileUploadProperties fileUploadProperties) {
        this.fileUploadProperties = fileUploadProperties;
    }

    private void prepareCheck() {
        Assert.hasText(this.fileUploadProperties.getStoreLocation(),"非法的参数,'storeLocation' 必须指定上传文件存放目录");
        Assert.hasText(this.fileUploadProperties.getTmpLocation(),"非法的参数,'tmpLocation' 必须指定文件上传时临时文件存放目录");

        if(!new File(this.fileUploadProperties.getTmpLocation()).isDirectory()){
            throw new BeanCreationException(DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME,"非法的参数,'tmpLocation' 必须为目录");
        }

        if(!new File(this.fileUploadProperties.getTmpLocation()).exists()){
            throw new BeanCreationException(DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME,"非法的参数,'tmpLocation' 目录不存在,请手动创建");
        }

        if(!new File(this.fileUploadProperties.getStoreLocation()).isDirectory()){
            throw new BeanCreationException(DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME,"非法的参数,'storeLocation' 必须为目录");
        }

        if(!new File(this.fileUploadProperties.getStoreLocation()).exists()){
            throw new BeanCreationException(DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME,"非法的参数,'storeLocation' 目录不存在,请手动创建");
        }
        if(!StringUtils.endsWith(File.separator,this.fileUploadProperties.getStoreLocation())){
            this.fileUploadProperties.setStoreLocation(this.fileUploadProperties.getStoreLocation()+File.separator);
        }
    }

    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean(MultipartResolver.class)
    public CommonsMultipartResolver multipartResolver() {

        prepareCheck();

        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver();

        try {
            multipartResolver.setUploadTempDir(new FileSystemResource(this.fileUploadProperties.getTmpLocation()));
        } catch (IOException e) {
            throw new BeanCreationException(DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME,"指定文件上传时临时文件存放目录,出现异常",e);
        }

        if(this.fileUploadProperties.getMaxFileSize()!=null){
            Assert.isTrue(this.fileUploadProperties.getMaxFileSize()>0,"非法的参数,'maxFileSize' 允许上传的最大文件大小应为大于0的正整数");
            multipartResolver.getFileUpload().setFileSizeMax(this.fileUploadProperties.getMaxFileSize());
        }

        if(this.fileUploadProperties.getFileSizeThreshold()!=null){
            Assert.isTrue(this.fileUploadProperties.getFileSizeThreshold()>0,"非法的参数,'fileSizeThreshold' 文件刷盘的阈值应为大于0的正整数");
            multipartResolver.getFileItemFactory().setSizeThreshold(this.fileUploadProperties.getFileSizeThreshold());
        }

        return multipartResolver;
    }

}
