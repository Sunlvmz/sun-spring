package com.sunmvc.beans.factory.Property;

import com.sunmvc.beans.factory.Property.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<PropertyValue>();
    }

    public void addPropertyValue(PropertyValue pv) {
        //TODO:这里可以对于重复propertyName进行判断，直接用list没法做到  所以看似多此一举，其实是为了给与spring更多的权限；比如说xml里定义了同名的property
        /**
        <bean id="userService" class="com.bjsxt.services.UserService" >
             <property name="userDao" bean="u" />
             <property name="userDao" bean="u" />
         </bean>
         */
        propertyValueList.add(pv);
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }
}
