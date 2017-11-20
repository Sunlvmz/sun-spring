package com.sunmvc.aop.support;

import com.sunmvc.aop.advisor.Advised;
import com.sunmvc.aop.advisor.Advisor;
import com.sunmvc.aop.proxy.AdvisorChainFactory;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * AdvisedSupport封装了TargetSource, MethodInterceptor和MethodMatcher
 *
 */
public class AdvisedSupport implements Advised {

	// 要拦截的对象
	public   static  TargetSource targetSource;
		
	/**
	 * 方法拦截器
     * Spring的AOP只支持方法级别的调用，所以其实在AopProxy里，我们只需要将MethodInterceptor放入对象的方法调用
	 */
//    private MethodInterceptor methodInterceptor;
    
    // 方法匹配器，判断是否是需要拦截的方法
    private MethodMatcher methodMatcher;

    private AdvisorChainFactory advisorChainFactory;
    private List<Advisor> advisors;
    private Advisor[] advisorArray;
    
    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

//    public MethodInterceptor getMethodInterceptor() {
//        return methodInterceptor;
//    }

//    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
//        this.methodInterceptor = methodInterceptor;
//    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Advisor[] getAdvisors() {
        return advisorArray;
    }

    @Override
    public void addAdvisors(Advisor[] advisors) {
        this.advisorArray = advisors;
    }

    public AdvisorChainFactory getAdvisorChainFactory() {
        return advisorChainFactory;
    }

    public void setAdvisorChainFactory(AdvisorChainFactory advisorChainFactory) {
        this.advisorChainFactory = advisorChainFactory;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {

        List<Object> chain= this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this, method, targetClass);

        return chain;
    }
}
