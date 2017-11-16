package com.sunmvc.beans.factory.factoryImpl;

import com.sun.org.apache.regexp.internal.RE;
import com.sunmvc.beans.definition.BeanDefinition;
import com.sunmvc.beans.factory.Property.BeanReference;
import com.sunmvc.beans.factory.Property.PropertyValue;
import com.sunmvc.beans.factory.Property.PropertyValues;
import com.sunmvc.beans.factory.support.BeanDefinitionRegistry;
import com.sunmvc.io.reader.AnnotationBeanDefinitionReader;
import com.sunmvc.io.reader.XmlBeanDefinitionReader;
import com.sunmvc.io.source.Resource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AutowireCapableBeanFactory extends DefaultBeanFactory implements BeanDefinitionRegistry{
    private Resource resource;
    /**
     * 增加bean处理程序：
     * 	例如通过AspectJAwareAdvisorAutoProxyCreator#postProcessAfterInitialization()实现AOP的织入
     */

    public AutowireCapableBeanFactory() {
    }
    public AutowireCapableBeanFactory(Resource resource) throws Exception {
        this.resource = resource;
//        refresh();
    }
    public AutowireCapableBeanFactory(String path) throws Exception {
        super(path);
    }

    protected class ResourceReaderBeanFactory extends AnnotationBeanDefinitionReader {
        public ResourceReaderBeanFactory(BeanDefinitionRegistry registry) {
            super(registry);
        }
    }
    // 好了，最后给定一个初始化方法
//    @Override
//    protected void refresh() throws Exception {
//        // 在这里，我们完成容器的初始化
//        // 1.资源我们已经在构造方法中获取
//        // 2.资源的解析
//        int count = new AutowireCapableBeanFactory.ResourceReaderBeanFactory(this).loadBeanDefinitions(resource);
//        // 3.容器的注册方法会被自动调用，此时注册就完成了
//        System.out.println("一共初注册了" + count + "个beanDefinition");
//    }

    @Override
    public void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws IllegalAccessException, NoSuchFieldException {
        System.out.println("Autowired apply properties");
        PropertyValues values = beanDefinition.getPropertyValues();
        if (values != null) {
            for (PropertyValue propertyValue : values.getPropertyValueList()) {
                // 如果属性是ref而不是value类型就先实例化那个ref的bean，然后装载到这个value里
                Object value = propertyValue.getValue();
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    // 先实例化ref的bean再装配进去
                    value = getBean(beanReference.getName());
                }
                try {
                    /**
                     * 例如：<bean id="dog" class="com.ysj.entity.Dog">
                     <property name="Id" value="101"></property>
                     </bean>
                     先获取dog对象的setId方法，然后通过反射调用该方法将value设置进去
                     getDeclaredMethod方法的第一个参数是方法名，第二个参数是该方法的参数列表
                     */
                    Method declaredMethod = bean.getClass().getDeclaredMethod(
                            // 拼接方法名
                            "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                    + propertyValue.getName().substring(1),  //获取setter
                            value.getClass());

				/*System.out.println("set" + propertyValue.getName().substring(0, 1).toUpperCase()
							  + propertyValue.getName().substring(1));*/
                    declaredMethod.setAccessible(true); //注入 private修饰的 method
                    declaredMethod.invoke(bean, value);//执行setter方法
                } catch (NoSuchMethodException e) {
                    //对于没有setter的bean直接注入
                    Field field = bean.getClass().getDeclaredField(propertyValue.getName());
                    field.setAccessible(true);//这个方法可以强行注入 private修饰的field
//                    field.set(bean, propertyValue.getValue());//这里要根据是否是ref参数选择；如果是ref参数，则注入经factory生成的bean;;;;如果按这种方式，则
//                                                                往service注入的是BeanReference,而不是impl；产生nullpointerexception
                    field.set(bean, value);
                    field.setAccessible(false); // 这一步必须有,注入完成，恢复field权限
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
