package com.sunmvc.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    enum RequestMethod{
        GET,
        POST,
        PUT,
        DELETE
    }
    String path();

    String method();
}
