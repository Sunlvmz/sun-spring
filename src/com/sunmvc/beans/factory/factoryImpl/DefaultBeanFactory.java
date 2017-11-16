package com.sunmvc.beans.factory.factoryImpl;

import com.sunmvc.beans.definition.BeanDefinition;
import com.sunmvc.beans.factory.support.BeanDefinitionRegistry;
import com.sunmvc.beans.factory.support.BeanPostProcessor;
import com.sunmvc.io.reader.XmlBeanDefinitionReader;
import com.sunmvc.io.source.Resource;
import com.sunmvc.io.source.ResourceLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory 的一种抽象类实现，规范了 IoC 容器的基本结构。
 *
 * IoC 容器的结构：DefaultBeanFactory 维护一个 beanDefinitionMap 哈希表用于保存类的定义信息（BeanDefinition）。
 *
 */
public class DefaultBeanFactory implements BeanFactory,BeanDefinitionRegistry{

//    private Map<String, Object> singletonBeanMap = new ConcurrentHashMap<>();
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    /**
     * 保存完成注册的bean的name
     */
    private final List<String> beanDefinitionNames = new ArrayList<String>();

    private Resource resource;
    public DefaultBeanFactory( ) {
    }
    public DefaultBeanFactory( Resource resource) throws Exception {
        this.resource = resource;
        refresh();
    }
    public DefaultBeanFactory(String path) throws Exception {
        ResourceLoader resourceLoader = new ResourceLoader();
        this.resource = resourceLoader.getFileResource(path); //todo 测试 UrlResource和FileResource的区别
        refresh();
    }
    /*
     * 资源的问题解决了，我们还得具备读取beandefinition的能力,本来是应该继承的容器中就具有了读取的能力，这里为了简便，我们使用内部类实现
     */
    //todo （spring中采用ApplicationContext类继承实现）
    protected class ResourceReaderBeanFactory extends XmlBeanDefinitionReader {
        public ResourceReaderBeanFactory(BeanDefinitionRegistry registry) {
            super(registry);
        }
    }
    // 好了，最后给定一个初始化方法
    protected void refresh() throws Exception {
        // 在这里，我们完成容器的初始化
        // 1.资源我们已经在构造方法中获取
        // 2.资源的解析
        int count = new ResourceReaderBeanFactory(this).loadBeanDefinitions(resource);
        // 3.容器的注册方法会被自动调用，此时注册就完成了
        System.out.println("一共初注册了" + count + "个beanDefinition");
    }
    /**
     * 增加bean处理程序：
     * 	例如通过AspectJAwareAdvisorAutoProxyCreator#postProcessAfterInitialization()实现AOP的织入
     */
     private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    @Override
    public Object getBean(String beanName) {
        // 获取该bean的定义
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        // 如果没有这个bean的定义beanDefinition就抛异常
        if (beanDefinition == null) {
            throw new IllegalArgumentException("No bean named " + beanName + " is defined");
        }
//        Object bean = this.getBeanSingleton(beanName);
//        if (bean == null) {
//            bean = doCreateBean(beanName, beanDefinitionMap.get(beanName));
//            singletonBeanMap.put(beanName, bean);
//        }
        Object bean = beanDefinition.getBean();
        // 如果还没有装配bean
        if (bean == null) {
            // 装配bean（实例化并注入属性）
            bean = doCreateBean(beanDefinition);
            // 初始化bean
            // 例如：生成相关代理类,用于实现AOP织入
            try {
                if(beanPostProcessors.size()>0)
                    bean = initializeBean(bean, beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            beanDefinition.setBean(bean);//单例模式逻辑层的体现
        }
        return bean;
    }

    /**
     * 预处理bean的定义，将bean的名字提前存好,实现Ioc容器中存储单例bean
     * @throws Exception
     */
    public void preInstantiateSingletons() throws Exception {
        Iterator it = this.beanDefinitionNames.iterator();
        while (it.hasNext()) {
            String beanName = (String) it.next();
            getBean(beanName);
        }
    }
    /**
     * 初始化bean
     * 可在此进行AOP的相关操作：例如生成相关代理类，并返回
//     * @param bean
//     * @param name
     * @return
     * @throws Exception
     */
    protected Object initializeBean(Object bean, String name) throws Exception {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        // 可以看看AutowireCapableBeanFactory的postProcessAfterInitialization()方法实现
        // 返回的可能是代理对象
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
        return bean;
    }
//     增加bean处理程序，例如AspectJAwareAdvisorAutoProxyCreator#postProcessAfterInitialization()
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public boolean containsBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            return true;
        }
        return false;
    }

//    private Object ObjectgetBeanSingleton(String beanName) {
//        return returnthis.singletonBeanMap.get(beanName);
//    }
    /**
     *将新加入的beanDefinition注册到beanDefinitionMap里
     *这里只是将beanDefinition的定义放入"注册表"(beanDefinitionMap) 至于beanDefinition是否有错误 以后再说 这里不管
     **/
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
        beanDefinitionNames.add(beanName);//为什么现在不检测beanDefinition的细节 例如有没有bean? layzload
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        if (beanDefinitionMap.get(beanName) != null) {
        beanDefinitionMap.remove(beanName);
        beanDefinitionNames.remove(beanName);
        }else {
           System.out.println("beanDefinition未注册" + beanName);
        }
    }

    private Object doCreateBean( BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition);
//            beanDefinition.setBean(bean); // 有没有必要？
            System.out.println("create a instance 。。。。。。。。");
            applyPropertyValues(bean,beanDefinition);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    private Object createBeanInstance(BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException {
        return  beanDefinition.getBeanClass().newInstance();
    }



    /**
     * 根据类型获取所有bean实例
     * @param type
     * @return
     * @throws Exception
     */
    public List getBeansForType(Class type) throws Exception {
        List beans = new ArrayList<Object>();
        for (String beanDefinitionName : beanDefinitionNames) {
            /**
             * boolean isAssignableFrom(Class<?> cls)
             * 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。
             */
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }
    @Override
    public List getBeanDefinitionNames(){
        return this.beanDefinitionNames;}

   protected   void applyPropertyValues (Object bean, BeanDefinition beanDefinition) throws Exception{
        // todo 开始权限设置错误，设置成private 导致AuwowireFactory无法覆盖，进不去applyProperty方法?
        System.out.println("default");
    }
}
