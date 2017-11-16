package com.sunmvc.beans.factory.support;

import com.sunmvc.beans.definition.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
    void removeBeanDefinition(String beanNanme);
}
