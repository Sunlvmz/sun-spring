package com.sunmvc.beans.factory.Property;

public class BeanReference {
    /**
     * 用于代表property标签的ref属性里的对象,即bean依赖的其他bean
     *
     */

        public String name;

        public Object bean;

        public BeanReference(String name){
            this.name = name;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getBean() {
            return bean;
        }

        public void setBean(Object bean) {
            this.bean = bean;
        }

    }

