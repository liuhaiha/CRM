package com.tydic.traffic.tafa.daf.page.annotion;


import com.tydic.traffic.tafa.daf.page.Page;
import com.tydic.traffic.tafa.daf.page.exception.PageException;
import com.tydic.traffic.tafa.daf.page.starter.PageProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Configuration
@Aspect
public class PageProcessor {

    private Logger logger = LoggerFactory.getLogger(PageProcessor.class);

    @Autowired
    private PageProperties pageProperties;

    @SuppressWarnings("unchecked")
    @Around("@annotation(com.tydic.traffic.tafa.daf.page.annotion.Pageable)")
    public Object processor(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] objs = proceedingJoinPoint.getArgs();
        boolean hasPageParam = false;
        Page<Object> page = null;
        for (Object obj : objs) {
            if (obj instanceof Page) {
                page = (Page<Object>) obj;
                if (page.getPageNo() <= 0) {
                    if (pageProperties.getDefaultPageNo() > 0) {
                        page.setPageNo(pageProperties.getDefaultPageNo());
                        logger.warn(proceedingJoinPoint.getSignature() + " pageNo is null , set to default : " + pageProperties.getDefaultPageNo());
                    } else {
                        throw new PageException(proceedingJoinPoint.getSignature() + " pageNo is null!");
                    }
                }
                if (page.getPageSize() <= 0) {
                    if (pageProperties.getDefaultPageSize() > 0) {
                        page.setPageSize(pageProperties.getDefaultPageSize());
                        logger.warn(proceedingJoinPoint.getSignature() + " pageSize is null , set to default : " + pageProperties.getDefaultPageSize());
                    } else {
                        throw new PageException(proceedingJoinPoint.getSignature() + " pageSize is null!");
                    }
                }

                /** 读取方法中@Pageable注解中的id参数 */
                Signature signature = proceedingJoinPoint.getSignature();
                MethodSignature methodSignature = (MethodSignature)signature;
                Method targetMethod = methodSignature.getMethod();

                try {
                    Method realMethod = proceedingJoinPoint.getTarget().getClass().
                            getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
                    Pageable anno=realMethod.getAnnotation(Pageable.class);
                    if(anno!=null && !StringUtils.isEmpty(anno.id())){
                        page.setId(anno.id());
                    }
                } catch (NoSuchMethodException e) {
                    throw new PageException(e);
                }

                PageContext.setPage(page);
                hasPageParam = true;
                break;
            }
        }
        if (!hasPageParam) {
            throw new PageException("方法中没有传入Page参数  : " + proceedingJoinPoint.getSignature());
        }
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            logger.error("", e);
        } finally {
            PageContext.clear();
        }
        return null;
    }
}
