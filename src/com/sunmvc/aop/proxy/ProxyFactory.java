package com.sunmvc.aop.proxy;


import com.sunmvc.aop.proxy.AopProxy;
import com.sunmvc.aop.proxy.Cglib2AopProxy;
import com.sunmvc.aop.support.AdvisedSupport;

public class ProxyFactory extends AdvisedSupport implements AopProxy {

	// 获取代理对象
	@Override
	public Object getProxy() {
		return createAopProxy().getProxy();
	}

	// 动态代理：通过Cglib代理提供代理对象
	protected final AopProxy createAopProxy() {
		return new Cglib2AopProxy(this);
	}

}
