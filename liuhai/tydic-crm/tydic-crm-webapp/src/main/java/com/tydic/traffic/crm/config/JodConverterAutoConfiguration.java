package com.tydic.traffic.crm.config;

import org.jodconverter.office.LocalOfficeManager.Builder ;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jodconverter.DocumentConverter;
import org.jodconverter.LocalConverter;
import org.jodconverter.boot.autoconfigure.JodConverterProperties;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({DocumentConverter.class})
@ConditionalOnProperty(
    prefix = "jodconverter",
    value="enabled",
    havingValue = "true",
    matchIfMissing = true
)
@EnableConfigurationProperties({JodConverterProperties.class})
public class JodConverterAutoConfiguration {
	
    @Bean(initMethod = "start",destroyMethod = "stop")
    @ConditionalOnMissingBean
    public OfficeManager createOfficeManager(@Autowired JodConverterProperties properties) {
        Builder builder = LocalOfficeManager.builder();
        if (!StringUtils.isBlank(properties.getPortNumbers())) {
            Set<Integer> iports = new HashSet();
            String[] var3 = StringUtils.split(properties.getPortNumbers(), ", ");
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String portNumber = var3[var5];
                iports.add(NumberUtils.toInt(portNumber, 2002));
            }

            builder.portNumbers(ArrayUtils.toPrimitive((Integer[])iports.toArray(new Integer[iports.size()])));
        }

        builder.officeHome(properties.getOfficeHome());
        builder.workingDir(properties.getWorkingDir());
        builder.templateProfileDir(properties.getTemplateProfileDir());
        builder.killExistingProcess(properties.isKillExistingProcess());
        builder.processTimeout(properties.getProcessTimeout());
        builder.processRetryInterval(properties.getProcessRetryInterval());
        builder.taskExecutionTimeout(properties.getTaskExecutionTimeout());
        builder.maxTasksPerProcess(properties.getMaxTasksPerProcess());
        builder.taskQueueTimeout(properties.getTaskQueueTimeout());
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({OfficeManager.class})
    public DocumentConverter jodConverter(OfficeManager officeManager) {
        return LocalConverter.make(officeManager);
    }
}