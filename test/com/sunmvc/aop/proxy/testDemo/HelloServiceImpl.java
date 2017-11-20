package com.sunmvc.aop.proxy.testDemo;

public class HelloServiceImpl implements HelloService {

    @Override
    public void hello() {
        System.out.println("hello world!!1");
    }
}
