package com.sunmvc.io.source;

import java.net.URL;

public class UrlResourceLoader implements ResourceLoader {
    private String path;
    public UrlResourceLoader(){}

    public UrlResourceLoader(String path) {
        this.path = path;
    }
    public Resource getResource(String path) {
        URL resource = this.getClass().getClassLoader().getResource(path);
        return new URLResource(resource);
    }
}
