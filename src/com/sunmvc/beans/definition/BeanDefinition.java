package com.sunmvc.beans.definition;

import com.sunmvc.beans.factory.Property.PropertyValues;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition implements AbstractBeanDefinition {
    private Object bean;
    private Object beanClass;
    private String beanClassName;
    private PropertyValues propertyValues;

    private final String SCOPE_DEFAULT = "single";
    private String scope = SCOPE_DEFAULT;

    List<String> dependentBeanDefinitions=new ArrayList<>(); //bean依赖的其他类
    public BeanDefinition(Object object){
        this.bean=object;
    }

    public BeanDefinition() {
    }

    public BeanDefinition(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public Object getBean() {
        return bean;
    }
    @Override
    public void setBean(Object bean) {
        this.bean = bean;
    }
    @Override
    public List<String> getDepends() {
        return dependentBeanDefinitions;
    }

    @Override
    public void addDepend(String dependName) {
        dependentBeanDefinitions.add(dependName);
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean isSingleton() {
        return this.scope.equals(SCOPE_DEFAULT);
    }

    @Override
    public String getDescription() {
        return null;
    }

    public Class getBeanClass() {
        Object beanClassObject = this.beanClass;
        if (beanClassObject == null) {
            throw new IllegalStateException("No bean class specified on bean definition");
        }
        if (!(beanClassObject instanceof Class)) {
            throw new IllegalStateException(
                    "Bean class name [" + beanClassObject + "] has not been resolved into an actual Class");
        }
        return (Class<?>) beanClassObject;
    }

    @Override
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        // 根据类名设置类
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PropertyValues getPropertyValues() {
//        this.propertyValues = new PropertyValues();//这里也不能直接加啊 不然在后边getBean时得到的也是空的
        if (this.propertyValues == null) {
            this.propertyValues = new PropertyValues();
        }
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues pvs) {
        this.propertyValues = pvs;
    }

    public static class BeanDefinitionHolder {
        private final BeanDefinition beanDefinition;
        private final String beanName;

        public BeanDefinitionHolder(String beanName, BeanDefinition beanDefinition) {
            this.beanName = beanName;
            this.beanDefinition = beanDefinition;
        }

        public BeanDefinition getBeanDefinition() {
            return this.beanDefinition;
        }

        public String getBeanName() {
            return this.beanName;
        }
    }
}
