package com.sunmvc.aop.advisor;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends Advice {
    void before(Method method, Object[] var2, Object var3) throws Throwable;
}
