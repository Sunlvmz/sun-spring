package com.sunmvc.aop.advisor;

//把List advisor 封装起来

import java.util.List;

public interface Advised {
    Advisor[] getAdvisors();

    void addAdvisors(Advisor[] advisors);

}
