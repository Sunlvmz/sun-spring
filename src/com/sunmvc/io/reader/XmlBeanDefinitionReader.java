package com.sunmvc.io.reader;

import com.sunmvc.beans.definition.BeanDefinition;
import com.sunmvc.beans.definition.DefaultBeanDefinition;
import com.sunmvc.beans.factory.support.BeanDefinitionRegistry;
import com.sunmvc.beans.factory.support.XmlParser;
import com.sunmvc.io.source.Resource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    //暂时保存beanDefinition，稍后在doLoadBeanDefinitions方法中注册到beanFactory
    protected Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    // 默認使用DefaultBeanDefinition
    // TODO 本來是應該講這個beanDefinition對象傳遞給xmlparser，在這裡先簡單實現
    @SuppressWarnings("unused")
    private BeanDefinition beanDefinition;


    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }

    public void setBeanDefinitionMap(Map<String, BeanDefinition> beanDefinitionMap) {
        this.beanDefinitionMap = beanDefinitionMap;
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
        beanDefinition = new DefaultBeanDefinition();
    }
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, BeanDefinition beanDefinition) {
        super(registry);
        this.beanDefinition = beanDefinition;
    }



    @Override
    public int loadBeanDefinitions(Resource resource) throws Exception {
       return doLoadBeanDefinitions(resource);
    }

    protected int doLoadBeanDefinitions(Resource resource) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = null;
        doc = docBuilder.parse(resource.getInputStream());
        // todo 解析bean
        XmlParser xmlParser = new XmlParser(doc, beanDefinitionMap);
        beanDefinitionMap= xmlParser.parse();
//        List packages = xmlParser.getComponentPackageNames();
        // 再次可以选择注入一个什么类
//        for (Entry<String, BeanDefinition> beanDefinition : beanDefinitionMap.entrySet()) {
//            // 講這個bean進行註冊,這是一個藉口方法，當某個容器需要註冊功能的時候，在繼承這個類
//            // key is the name of bean,value is the beanDefinition
//            registry.registerBeanDefinition(beanDefinition.getKey(),beanDefinition.getValue());
//        }
        resource.getInputStream().close();
        return beanDefinitionMap.size();
    }


}
