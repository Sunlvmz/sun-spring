package com.sunmvc.aop.support;

/**
 * 类匹配器
 *因为有时候ClassFilter、MethodMatcher 并不止一个，所以需要多个，也就是ComposablePointcut应该设计成List<ClassFilter>的形式，
 * 但是这样设计的话，又没法实现Pointcut接口的ClassFilter getClassFilter()方法，即到底返回哪一个ClassFilter，
 * 没法更好的描述。所以ComposablePointcut就只有一个ClassFilter，但是它实现了多个ClassFilter的功能，在
 * Spring框架里，这种形式与写法也非常常见，所以我们应该好好学学。
 */
public interface ClassFilter {

	/**
	 * 用于匹配targetClass是否是要拦截的类
	 * @param targetClass
	 * @return
	 */
	boolean matches(Class targetClass);
	
}
