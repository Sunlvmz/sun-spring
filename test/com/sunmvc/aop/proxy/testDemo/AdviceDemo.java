package com.sunmvc.aop.proxy.testDemo;

import com.sunmvc.aop.advisor.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class AdviceDemo implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] var2, Object var3) throws Throwable {
        System.out.println("method" + method.getName());
    }
}
