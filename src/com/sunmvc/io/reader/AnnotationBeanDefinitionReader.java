package com.sunmvc.io.reader;

import com.sunmvc.beans.definition.BeanDefinition;
import com.sunmvc.beans.definition.DefaultBeanDefinition;
import com.sunmvc.beans.factory.Property.BeanReference;
import com.sunmvc.beans.factory.Property.PropertyValue;
import com.sunmvc.beans.factory.support.BeanDefinitionRegistry;
import com.sunmvc.beans.factory.support.XmlParser;
import com.sunmvc.io.source.Resource;
import com.sunmvc.stereotype.Autowired;
import com.sunmvc.stereotype.Component;
import com.sunmvc.tools.PackageUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class AnnotationBeanDefinitionReader extends XmlBeanDefinitionReader {
    public AnnotationBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public int loadBeanDefinitions(Resource resource) throws ParserConfigurationException, SAXException, IOException {
        return doLoadBeanDefinitionsfromAnnotation(resource);
    }

    private int doLoadBeanDefinitionsfromAnnotation(Resource resource) throws IOException, SAXException, ParserConfigurationException {
        int count = super.doLoadBeanDefinitions(resource);
        List<String> packageNames = XmlParser.getComponentPackageNames();
        if (!packageNames.isEmpty()) {
            for (String packageName : packageNames) {
                // 获得包下的所有类名
                List<String> ClassNames = PackageUtil.getClassName(packageName);
                if (!ClassNames.isEmpty()) {
                    for (String ClassName : ClassNames) {
                        BeanDefinition beanDefinition = new DefaultBeanDefinition();
                        // 获得beanDefinition的beanClass
                        Class<?> beanClass = null;
                        try {
                            beanClass = Class.forName(ClassName);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        // 验证是否有Component注解
                        Component com = beanClass.getAnnotation(Component.class);
                        if (com != null) {
                            beanDefinition.setBeanClass(beanClass);
                            // 还要获取它的依赖
                            Field[] fields = beanClass.getDeclaredFields();
                            if (fields.length > 0) {
                                for (Field f : fields) {
                                    Autowired autowired = f.getAnnotation(Autowired.class);
                                    if (autowired != null) {
                                        // todo autowired result?
                                        BeanDefinition autoWiredBeanDefinition = new BeanDefinition();
                                        autoWiredBeanDefinition.setBeanClass(f.getType());
                                        String autoWiredBeanName = f.getType().toString();
                                        String autoWiredName = getBeanName(autoWiredBeanName);
                                        beanDefinitionMap.put(autoWiredName, autoWiredBeanDefinition); // todo  result
//                                        BeanReference beanReference = new BeanReference(f.getType());
//                                        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(autowired.value(), beanReference));
                                    }
                                }
                            }
                            // 默认使用全部小写的方式
                            String beanDefinitionName = getBeanName(ClassName);
                            beanDefinitionMap.put(beanDefinitionName, beanDefinition);
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    private String getBeanName(String className) {
        return (className.substring(className.lastIndexOf(".") + 1)).toLowerCase();
    }
}


