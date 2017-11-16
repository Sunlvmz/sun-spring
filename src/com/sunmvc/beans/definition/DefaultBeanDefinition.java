package com.sunmvc.beans.definition;

public class DefaultBeanDefinition extends BeanDefinition{
    public DefaultBeanDefinition(Object object) {
        super(object);
    }

    public DefaultBeanDefinition() {

    }

    @Override
    public String getDescription() {
        return getBeanClass().getName();
    }

}
