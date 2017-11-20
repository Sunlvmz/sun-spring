package com.sunmvc.aop.support.registry;

import com.sunmvc.aop.support.registry.AdvisorAdapterRegistry;

public class GlobalAdvisorAdapterRegistry {
    private static final AdvisorAdapterRegistry INSTANCE = new DefaultAdvisorAdapterRegistry();
    public static AdvisorAdapterRegistry getInstance(){
        return INSTANCE;
    }
}
