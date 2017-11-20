package com.sunmvc.aop.support.registry;

import com.sunmvc.aop.advisor.Advisor;

import com.sunmvc.aop.advisor.DefaultPointcutAdvisor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultAdvisorAdapterRegistry implements AdvisorAdapterRegistry {

    private final List<AdvisorAdapter> adapters = new ArrayList(3);

    public DefaultAdvisorAdapterRegistry() {
        this.registerAdvisorAdapter(new MethodBeforeAdviceAdapter());
//        this.registerAdvisorAdapter(new AfterReturningAdviceAdapter());
//        this.registerAdvisorAdapter(new ThrowsAdviceAdapter());
    }
    public Advisor wrap(Object adviceObject) { //把advice转换成advisor
        if (adviceObject instanceof Advisor) {
            return (Advisor)adviceObject;
        }
         else {
            Advice advice = (Advice)adviceObject;
            if (advice instanceof MethodInterceptor) {
                return new DefaultPointcutAdvisor(advice);
            } else {

                Iterator iterator = this.adapters.iterator();
                AdvisorAdapter adapter;
                do {
                    adapter = (AdvisorAdapter)iterator.next();
                } while(!adapter.supportsAdvice(advice));

                return new DefaultPointcutAdvisor(advice);
            }
        }
    }

    @Override
    public MethodInterceptor[] getInterceptors(Advisor advisor) {
        List<MethodInterceptor> interceptors = new ArrayList(3);
        Advice advice = advisor.getAdvice();
        if (advice instanceof MethodInterceptor) {
            interceptors.add((MethodInterceptor)advice);
        }

        Iterator iterator = this.adapters.iterator();

        while(iterator.hasNext()) {
            AdvisorAdapter adapter = (AdvisorAdapter)iterator.next();
            if (adapter.supportsAdvice(advice)) {
                interceptors.add(adapter.getInterceptor(advisor));
            }
        }
         return interceptors.toArray(new MethodInterceptor[interceptors.size()]);
        }

    public void registerAdvisorAdapter(AdvisorAdapter adapter) {
        this.adapters.add(adapter);
    }
}

