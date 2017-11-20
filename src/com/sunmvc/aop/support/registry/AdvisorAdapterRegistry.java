package com.sunmvc.aop.support.registry;

import com.sunmvc.aop.advisor.Advisor;
import org.aopalliance.intercept.MethodInterceptor;


public interface AdvisorAdapterRegistry {
   MethodInterceptor[] getInterceptors(Advisor advisor) ;
   void registerAdvisorAdapter(AdvisorAdapter adapter);
   Advisor wrap(Object adviceObject);
}
