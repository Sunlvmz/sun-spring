package com.sunmvc.beans.factory.support;

import com.sunmvc.beans.definition.BeanDefinition;
import com.sunmvc.beans.factory.Property.BeanReference;
import com.sunmvc.beans.factory.Property.PropertyValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlParser {
    private static Document doc;
    private static Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private static List<String> ComponentPackageNames = new ArrayList<String>();

    public XmlParser(Document doc, Map beanDefinitionMap) {
        this.doc = doc;
        this.beanDefinitionMap = beanDefinitionMap;
    }
  public static Map<String, BeanDefinition> parse() {
      Element root = doc.getDocumentElement();
      return parseBeanDefinitions(root);
  }

    private static Map<String, BeanDefinition> parseBeanDefinitions(Element root) {
        NodeList nodeList = root.getChildNodes();
        for(int i =0;i<nodeList.getLength();i++) {
            Node node = nodeList.item(i);
            BeanDefinition beanDefinition =  null;
            if (node instanceof Element) {
                Element element = (Element) node;
                //包名处理，为基于注解的IOC
                String nodeName = element.getNodeName();
                if (nodeName.equals("package-scan")) { // 别用while 否则死循环
                    String packageName = element.getAttribute("packagename");
                    if (!packageName.isEmpty()) {
                    ComponentPackageNames.add(packageName);
                    }
                    else {
                    System.out.println("为填入包名");
                     }
                }
                else if (nodeName.equals("bean")) {
                    beanDefinition = new BeanDefinition();
                    processBeanDefinition(element,beanDefinition);
                }
            }
        }
        return beanDefinitionMap;
    }

    //获取id和classname
    private static void processBeanDefinition(Element element,BeanDefinition beanDefinition) {
        String name = element.getAttribute("id");
        String className = element.getAttribute("class");
        //注册Class
        if(className==null)
        {throw new NullPointerException("className is not define");}

        beanDefinition.setBeanClassName(className);

        //处理属性
        processProperty(element, beanDefinition);
        //
        beanDefinitionMap.put(name, beanDefinition);
    }
    //为每个bean注册属性
    private static BeanDefinition processProperty(Element element, BeanDefinition beanDefinition) {
        NodeList propertyNodes = element.getElementsByTagName("property");
        for(int i =0;i<propertyNodes.getLength();i++) {
            Node node = propertyNodes.item(i);
            if (node instanceof Element) {
                Element property = (Element) node;
                String name = property.getAttribute("name");
                String value = property.getAttribute("value");
                PropertyValue propertyValue = new PropertyValue(name, value);
                if (null != value && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(propertyValue);//记一次BUG记录 beanDefinition的getPropertyValues() 返回的是一个null PropertyValues对象
                }else{
                    //todo value和ref不能同时有值或同时没值
                    String ref = property.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("Configuration problem: <property> element for property '"
                                + name + "' must specify a ref or value");
                    }else {
                        BeanReference beanReference = new BeanReference(ref);
                        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                    }
                }
            }
        }
        return beanDefinition;
    }

    public static BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }
    public static List<String> getComponentPackageNames() {
        return ComponentPackageNames;
    }
}
