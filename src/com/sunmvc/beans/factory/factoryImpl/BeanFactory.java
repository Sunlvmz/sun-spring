package com.sunmvc.beans.factory.factoryImpl;

import java.util.List;

public interface BeanFactory {

   Object getBean(String beanName);

   boolean containsBean(String beanName);

   List getBeanDefinitionNames();

}
