package com.sunmvc.beans.factory.support;

import com.sunmvc.beans.definition.BeanDefinition;
import com.sunmvc.io.source.FileResource;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class XmlParserTest {
    @Test
    public void parse() throws Exception {
        Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
        FileResource fileResource = new FileResource("resources/application.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = null;
        doc = docBuilder.parse(fileResource.getInputStream());
        XmlParser parser = new XmlParser(doc,beanDefinitionMap);
        parser.parse();
    }

}