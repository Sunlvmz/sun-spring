package com.sunmvc.aop.support;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

import com.sunmvc.aop.support.registry.InterceptorAndDynamicMethodMatcher;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 封装被代理对象的方法
 *
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

	// 原对象
	protected Object target;

    protected Method method;

    protected Object[] arguments;
	protected Class targetClass;
	protected List<Object> interceptorsAndDynamicMethodMatchers;

	private int currentInterceptorIndex = -1;//迭代的flag

	public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
		this.target = target;
		this.method = method;
		this.arguments = arguments;
	}

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] args, Class targetClass, List<Object> chain) {
		this.target = target;
		this.method = method;
		this.arguments = arguments;
		this.targetClass = targetClass;
		this.interceptorsAndDynamicMethodMatchers = chain;
	}

    @Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return method;
	}

	@Override
	public Object getThis() {
		return target;
	}

	/**
	 * 调用被拦截对象的方法
	 */
	@Override
	public Object proceed() throws Throwable {
		// tiny-spring这里是调用原始对象的方法
		// 不支持拦截器链
		/*
			为了支持拦截器链，可以做出以下修改：
				将 proceed() 方法修改为调用代理对象的方法 method.invoke(proxy,args)。
				在代理对象的 InvocationHandler 的 invoke 函数中，查看拦截器列表，如果有拦截器，则调用第一个拦截器并
				  返回，否则调用原始对象的方法。
		*/
		if (this.currentInterceptorIndex == interceptorsAndDynamicMethodMatchers.size() - 1) { //通过序号判断是否执行到链的结尾，如果是则直接调用目标方法，否则执行拦截器
			return method.invoke(target, arguments);
		}else {
			Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);//拿到当前interceptor并把序号加一
			if (interceptorOrInterceptionAdvice instanceof InterceptorAndDynamicMethodMatcher) { //判断是否是标准interceptor
				InterceptorAndDynamicMethodMatcher dm = (InterceptorAndDynamicMethodMatcher)interceptorOrInterceptionAdvice;
//			todo	return dm.methodMatcher.matches(this.method, this.targetClass, this.arguments) ? dm.interceptor.invoke(this) : this.proceed();  没有方法匹配器
				return dm.interceptor.invoke(this);
				//如果方法匹配成功则执行当前interceptor.invoke()否则 跳过继续下一个interceptor
			} else {
				return ((MethodInterceptor)interceptorOrInterceptionAdvice).invoke(this);
			}
		}
		
	}

	@Override
	public Method getMethod() {
		return method;
	}

}
