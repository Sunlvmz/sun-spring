package com.sunmvc.io.reader;

import com.sunmvc.beans.factory.support.BeanDefinitionRegistry;
import com.sunmvc.io.source.Resource;
import com.sunmvc.io.source.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader  {//未实现loadBeanDefiniton方法，所以要加abstract修饰

    protected final BeanDefinitionRegistry registry ; //


    protected ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry=registry;
//        this.resourceLoader = (ResourceLoader)this.registry;
    }

//    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
//        this.registry = registry;
//        this.resourceLoader = (ResourceLoader) this.registry;
//    }
    /* (non-Javadoc)
     * @see com.lonton.core.io.BeanDefinitionReader#getBeanDefinitionRegistry()
     */
    @Override
    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        // TODO Auto-generated method stub
        return this.registry;
    }

    /* (non-Javadoc)
     * @see com.lonton.core.io.BeanDefinitionReader#getResourceLoader()
     */
    @Override
    public ResourceLoader getResourceLoader() {
        // TODO Auto-generated method stub
        return this.resourceLoader;
    }

    /* (non-Javadoc)
     * @see com.lonton.core.io.BeanDefinitionReader#loadBeanDefinitions(com.lonton.core.io.Resource[])
     */
    @Override
    public int loadBeanDefinitions(Resource... resources) throws Exception{
        // 在这里先简单实现，return the number of bean definitions found
        //这个方法是个死循环，也就是说继承这个类的子类必须重写loadBeanDefinitions(resource)，
        //真正返回the number of bean definitions found,再调用父类loadBeanDefinitions
        //方法，返回所有definition的个数，在这里并没有实现loadBeanDefinitions(resource)方法，
        //真正的实现交给继承它的子类
        int count=0;
        for(Resource resource:resources){
            count+=loadBeanDefinitions(resource);
        }
        return count;
    }
}
