package com.sunmvc.aop.support.registry;

import com.sunmvc.aop.support.MethodMatcher;
import org.aopalliance.intercept.MethodInterceptor;

public class InterceptorAndDynamicMethodMatcher {
    public final MethodInterceptor interceptor;
    public final MethodMatcher methodMatcher;

    public InterceptorAndDynamicMethodMatcher(MethodInterceptor interceptor, MethodMatcher methodMatcher) {
        this.interceptor = interceptor;
        this.methodMatcher = methodMatcher;
    }
}
