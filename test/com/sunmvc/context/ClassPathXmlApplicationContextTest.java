package com.sunmvc.context;

import com.sunmvc.beans.factory.HelloWorldServiceImpl;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ClassPathXmlApplicationContextTest {
    @Test
    public void loadBeanDefinitions() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("resources/application.xml");
        Map beanMap = context.getBeanDefinitionMap();
        HelloWorldServiceImpl h=(HelloWorldServiceImpl) context.getBean("helloWorldService");
        h.helloWorld();
    }
}