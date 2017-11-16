package com.sunmvc.beans.factory;

import com.sunmvc.stereotype.Autowired;
import com.sunmvc.stereotype.Component;

@Component
public class HelloWorldServiceImpl {
    private String text;
    private int value;
    @Autowired
    private AnotherServiceImpl service;
        public void helloWorld2() {
            System.out.println("hello");
        }
        public void helloWorld(){
            service.say(text+"  fff");
            System.out.println(text+value+" ss");
        }
    }
