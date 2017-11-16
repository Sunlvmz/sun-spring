package com.sunmvc.io.reader;

import com.sunmvc.beans.factory.support.BeanDefinitionRegistry;
import com.sunmvc.io.source.Resource;
import com.sunmvc.io.source.FileResourceLoader;

public interface BeanDefinitionReader {
    BeanDefinitionRegistry getBeanDefinitionRegistry();

    FileResourceLoader getFileResourceLoader();

    int loadBeanDefinitions(Resource... resources) throws Exception;

    int loadBeanDefinitions(Resource resource) throws  Exception;
}
