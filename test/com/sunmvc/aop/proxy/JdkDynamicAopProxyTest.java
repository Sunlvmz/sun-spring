package com.sunmvc.aop.proxy;

import com.sunmvc.aop.advisor.Advisor;
import com.sunmvc.aop.proxy.testDemo.AdviceDemo;
import com.sunmvc.aop.proxy.testDemo.AdviceDemo2;
import com.sunmvc.aop.proxy.testDemo.HelloService;
import com.sunmvc.aop.proxy.testDemo.HelloServiceImpl;
import com.sunmvc.aop.support.AdvisedSupport;
import com.sunmvc.aop.support.TargetSource;
import com.sunmvc.aop.support.registry.AdvisorAdapterRegistry;
import com.sunmvc.aop.support.registry.GlobalAdvisorAdapterRegistry;
import com.sunmvc.beans.factory.HelloWorldServiceImpl;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

public class JdkDynamicAopProxyTest {
    @Test
    public void getProxy() throws Exception {
        AdvisedSupport advisedSupport = new AdvisedSupport();
        AdviceDemo adviceDemo = new AdviceDemo();
        AdviceDemo2 adviceDemo2 = new AdviceDemo2();
        AdvisorAdapterRegistry registry = GlobalAdvisorAdapterRegistry.getInstance();
       Advisor advisor1= registry.wrap(adviceDemo);
        Advisor advisor2= registry.wrap(adviceDemo2);
        Advisor[] advisors = new Advisor[2];
        advisors[0] = advisor1;
        advisors[1] = advisor2;

        HelloServiceImpl service = new HelloServiceImpl();
        TargetSource targetSource = new TargetSource(service, HelloServiceImpl.class,
                HelloService.class);
        DefaultAdvisorChainFactory factory = new DefaultAdvisorChainFactory();


        advisedSupport.addAdvisors(advisors);
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setAdvisorChainFactory(factory);

        Method method = service.getClass().getMethod("hello");
        List list = advisedSupport.getInterceptorsAndDynamicInterceptionAdvice(method, service.getClass());


        JdkDynamicAopProxy proxy = new JdkDynamicAopProxy(advisedSupport);
        HelloService serviceProxy = (HelloService) proxy.getProxy();
        serviceProxy.hello();




    }

}