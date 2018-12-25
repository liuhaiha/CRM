package com.tydic.traffic.core.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * 针对springboot的自动装配进行过滤,移除springboot不必要的自动装配
 *
 * @author acer
 * @since 2017-07-13
 */
public class TafaAutoConfigurationImportFilter implements AutoConfigurationImportFilter,EnvironmentAware {
    private static final Logger logger= LoggerFactory.getLogger(TafaAutoConfigurationImportFilter.class);

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }

    @Override
    public boolean[] match(final String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] isMatch = new boolean[autoConfigurationClasses.length];
        for(int i=0,len=isMatch.length;i<len;i++){
           isMatch[i]=true;
        }

        String skipClassNames=environment.getProperty("tafa.core.skipClassNames");
        if(StringUtils.isEmpty(skipClassNames)){
            return isMatch;
        }

        skipClassNames= StringUtils.trimAllWhitespace(skipClassNames);
        skipClassNames=","+skipClassNames;
        skipClassNames+=",";

        for(int i=0,len=autoConfigurationClasses.length;i<len;i++){
            if(skipClassNames.indexOf(","+autoConfigurationClasses[i]+",")!=-1){
                if(logger.isDebugEnabled()){
                    logger.debug("find "+ autoConfigurationClasses[i]+" in the skipClassNames="+skipClassNames
                    +" it's will ignore");
                }

                isMatch[i]=false;
            }
        }

        return isMatch;
    }

}
