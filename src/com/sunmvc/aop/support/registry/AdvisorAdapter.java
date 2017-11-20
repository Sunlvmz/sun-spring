package com.sunmvc.aop.support.registry;

import com.sunmvc.aop.advisor.Advisor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

public interface AdvisorAdapter {
    boolean supportsAdvice(Advice var1);
    MethodInterceptor getInterceptor(Advisor advisor);
}
