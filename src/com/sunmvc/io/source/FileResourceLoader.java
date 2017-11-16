package com.sunmvc.io.source;

import java.net.URL;

public class FileResourceLoader implements ResourceLoader{

    //获取资源
    private String path;
    public FileResourceLoader(){}

    public FileResourceLoader(String path) {
        this.path = path;
    }
//    public Resource getUrlResource(String path) {
//        URL resource = this.getClass().getClassLoader().getResource(path);
//        return new URLResource(resource);
//    }
    @Override
    public Resource getResource(String path) {
        FileResource fileResource = new FileResource(path);
        return fileResource;
    }
}
