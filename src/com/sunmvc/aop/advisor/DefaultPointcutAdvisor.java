package com.sunmvc.aop.advisor;

import com.sunmvc.aop.pointcut.Pointcut;
import org.aopalliance.aop.Advice;

public class DefaultPointcutAdvisor implements PointcutAdvisor {
    private Pointcut pointcut ;
    private Advice advice;
    public DefaultPointcutAdvisor(Advice advice) {
        this.pointcut = TruePointcut.TRUE;
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {

        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
