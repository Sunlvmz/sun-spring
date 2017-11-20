package com.sunmvc.aop.support.registry;

import com.sunmvc.aop.advisor.Advisor;
import com.sunmvc.aop.advisor.MethodBeforeAdvice;
import com.sunmvc.aop.interceptor.MethodBeforeAdviceInterceptor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

public class MethodBeforeAdviceAdapter implements AdvisorAdapter {
    MethodBeforeAdviceAdapter() {
    }

    public boolean supportsAdvice(Advice advice) {
        return advice instanceof MethodBeforeAdvice;
    }

    public MethodInterceptor getInterceptor(Advisor advisor) {
        MethodBeforeAdvice advice = (MethodBeforeAdvice)advisor.getAdvice();
        return new MethodBeforeAdviceInterceptor(advice);
    }
}
