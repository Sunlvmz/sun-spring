package com.sunmvc.aop.advisor;

import com.sunmvc.aop.pointcut.Pointcut;
import com.sunmvc.aop.support.ClassFilter;
import com.sunmvc.aop.support.MethodMatcher;

public class TruePointcut implements Pointcut {
    public static final TruePointcut TRUE = new TruePointcut();
    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            @Override
            public boolean matches(Class targetClass) {
                return true;
            }
        };
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return null;
    }
}
