package com.sunmvc.beans.factory;

import com.sunmvc.beans.factory.factoryImpl.AutowireCapableBeanFactory;
import com.sunmvc.beans.factory.factoryImpl.BeanFactory;
import com.sunmvc.io.source.FileResource;
import com.sunmvc.stereotype.Autowired;
import com.sunmvc.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        Class<?> cl = null;
        try {
            cl = Class.forName("com.sunmvc.beans.factory.HelloWorldServiceImpl");
            Component com = cl.getAnnotation(Component.class);
            if (com != null) {
                // 还要获取它的依赖
                Field[] fields = cl.getDeclaredFields();
                if (fields.length > 0) {
                    for (Field f : fields) {
                        Autowired autowired = f.getAnnotation(Autowired.class);
                        if (autowired != null) {
                            //
                            Class c=f.getType();
                            System.out.println(autowired.value()+"autowired");
                            System.out.println(f.getName());

                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
