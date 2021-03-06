package com.sunmvc.io.reader;

import com.sunmvc.beans.factory.HelloWorldServiceImpl;
import com.sunmvc.beans.factory.factoryImpl.AutowireCapableBeanFactory;
import com.sunmvc.beans.factory.factoryImpl.BeanFactory;
import com.sunmvc.io.source.FileResource;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class XmlBeanDefinitionReaderTest {

    @Test
    public void test() throws Exception {

//        xmlBeanDefinitionReader.loadBeanDefinitions("application.xml");
//        Map<String, BeanDefinition> registry = xmlBeanDefinitionReader.getRegistry();

        FileResource fileResource = new FileResource("resources/application.xml");
        InputStream inputStream = fileResource.getInputStream();
        BeanFactory beanFactory = new AutowireCapableBeanFactory(fileResource);
//        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        HelloWorldServiceImpl h=(HelloWorldServiceImpl) beanFactory.getBean("helloWorldService");
        List list = beanFactory.getBeanDefinitionNames();
        h.helloWorld();
    }
}