package com.sunmvc.beans.factory.factoryImpl;

import com.sunmvc.beans.definition.BeanDefinition;
import com.sunmvc.beans.factory.HelloWorldServiceImpl;
import com.sunmvc.beans.factory.Property.BeanReference;
import com.sunmvc.beans.factory.Property.PropertyValue;
import com.sunmvc.beans.factory.Property.PropertyValues;
import org.junit.Test;

import static org.junit.Assert.*;


public class DefaultBeanFactoryTest {
@Test
    public void Step1() throws Exception {
        BeanFactory beanFactory=new DefaultBeanFactory();
        BeanDefinition beanDefinition=new BeanDefinition();
        beanDefinition.setBeanClassName("com.sunmvc.beans.factory.HelloWorldServiceImpl");
        ((DefaultBeanFactory)beanFactory).registerBeanDefinition("helloworld",beanDefinition);


        HelloWorldServiceImpl h=(HelloWorldServiceImpl) beanFactory.getBean("helloworld");
        h.helloWorld2();
    }
    @Test
    public void Step2(){

        AutowireCapableBeanFactory beanFactory=new AutowireCapableBeanFactory();
        BeanDefinition beanDefinition=new BeanDefinition();
        beanDefinition.setBeanClassName("com.sunmvc.beans.factory.HelloWorldServiceImpl");
        BeanDefinition beanDefinition2=new BeanDefinition();
        beanDefinition2.setBeanClassName("com.sunmvc.beans.factory.AnotherServiceImpl");
        BeanReference beanReference = new BeanReference("service");
        beanReference.setBean(beanDefinition2);

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("text", "hello spring"));
        propertyValues.addPropertyValue(new PropertyValue("value", new Integer(10)));
        propertyValues.addPropertyValue(new PropertyValue("service", beanReference));
        beanDefinition.setPropertyValues(propertyValues);
        beanFactory.registerBeanDefinition("service",beanDefinition2);
        beanFactory.registerBeanDefinition("helloworld",beanDefinition);

        HelloWorldServiceImpl h=(HelloWorldServiceImpl) beanFactory.getBean("helloworld");

        h.helloWorld();
    }

}