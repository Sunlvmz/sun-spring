package com.sunmvc.io.reader;

import com.sunmvc.beans.factory.factoryImpl.AutowireCapableBeanFactory;
import com.sunmvc.beans.factory.factoryImpl.BeanFactory;
import com.sunmvc.io.source.FileResource;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnnotationBeanDefinitionReaderTest {
    @Test
    public void loadBeanDefinitions() throws Exception {
        FileResource fileResource = new FileResource("resources/application.xml");
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory(fileResource);
        AnnotationBeanDefinitionReader reader = new AnnotationBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(fileResource);
        System.out.println("dd");
    }

}