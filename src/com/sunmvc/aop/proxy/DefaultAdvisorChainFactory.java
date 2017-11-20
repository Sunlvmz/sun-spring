package com.sunmvc.aop.proxy;

import com.sunmvc.aop.advisor.Advised;
import com.sunmvc.aop.advisor.Advisor;
import com.sunmvc.aop.advisor.PointcutAdvisor;
import com.sunmvc.aop.support.MethodMatcher;
import com.sunmvc.aop.support.registry.AdvisorAdapterRegistry;
import com.sunmvc.aop.support.registry.GlobalAdvisorAdapterRegistry;
import com.sunmvc.aop.support.registry.InterceptorAndDynamicMethodMatcher;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DefaultAdvisorChainFactory implements AdvisorChainFactory {
    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Advised config, Method method, Class<?> targetClass) {
        List<Object> interceptorList = new ArrayList(config.getAdvisors().length);
        Class<?> actualClass = targetClass != null ? targetClass : method.getDeclaringClass();
        AdvisorAdapterRegistry registry = GlobalAdvisorAdapterRegistry.getInstance(); //单例模式
        Advisor[] advisors = config.getAdvisors();
        for(int i = 0;i<advisors.length;i++){
            Advisor advisor = advisors[i];
            MethodInterceptor[] methodInterceptors;
            if (advisor instanceof PointcutAdvisor) {
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                if (pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
                    methodInterceptors = registry.getInterceptors(advisor);
                    MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
                        if (mm==null||mm.matches(method, actualClass,null)) {
                            for(int j = 0;j<methodInterceptors.length;j++){
                                 MethodInterceptor methodInterceptor = methodInterceptors[j];
                                interceptorList.add(new InterceptorAndDynamicMethodMatcher(methodInterceptor,mm));
                         }
                      }
                }else
                    interceptorList.add(new InterceptorAndDynamicMethodMatcher((MethodInterceptor) advisor,null));// todo
            }

        }
        return interceptorList;
    }
}
