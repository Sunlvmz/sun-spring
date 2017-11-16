package com.sunmvc.context;

import com.sunmvc.beans.definition.BeanDefinition;
import com.sunmvc.beans.factory.factoryImpl.AutowireCapableBeanFactory;
import com.sunmvc.beans.factory.factoryImpl.DefaultBeanFactory;
import com.sunmvc.io.reader.AnnotationBeanDefinitionReader;
import com.sunmvc.io.reader.XmlBeanDefinitionReader;
import com.sunmvc.io.source.FileResourceLoader;

import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private String configLocation;

    public ClassPathXmlApplicationContext(String configLocation) throws Exception {
        // 默认是自动装载bean
        this(configLocation, new AutowireCapableBeanFactory(configLocation));
    }

    public ClassPathXmlApplicationContext(String configLocation, DefaultBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    /**
     * 加载出bean的定义，并保存到beanFactory中
     *
     * 注意：在 tiny-spring 的实现中，先用 BeanDefinitionReader 读取 BeanDefiniton 后，保存在内置的
     * registry （键值对为 String - BeanDefinition 的哈希表，通过 getRigistry() 获取）中，然后由
     *  ApplicationContext 把 BeanDefinitionReader 中 registry 的键值对一个个赋值给 BeanFactory
     *  中保存的 beanDefinitionMap。而在 Spring 的实现中，BeanDefinitionReader 直接操作 BeanDefinition
     *  ，它的 getRegistry() 获取的不是内置的 registry，而是 BeanFactory 的实例。如何实现呢？
     *  以 DefaultListableBeanFactory 为例，它实现了一个 BeanDefinitonRigistry 接口，该接口
     *  把 BeanDefinition 的 注册 、获取 等方法都暴露了出来，这样，BeanDefinitionReader 可以直接通过这些
     *  方法把 BeanDefiniton 直接加载到 BeanFactory 中去。
     */
    @Override
    protected void loadBeanDefinitions(DefaultBeanFactory beanFactory) throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new AnnotationBeanDefinitionReader(beanFactory);
        // 从类路径加载xml文件中bean的定义并注册到AbstractBeanDefinitionReader的registry中去
        xmlBeanDefinitionReader.loadBeanDefinitions(beanFactory.getResource());
        // 将加载出的bean定义从registry中注册到beanFactory中
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getBeanDefinitionMap().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }

    @Override
    public boolean containsBean(String beanName) {
        return false;
    }

    @Override
    public List getBeanDefinitionNames() {
        return null;
    }
}
