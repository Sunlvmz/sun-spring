package com.sunmvc.io.source;

import java.net.URL;

public class ResourceLoader {
    //获取资源
    public Resource getUrlResource(String path) {
        URL resource = this.getClass().getClassLoader().getResource(path);
        return new URLResource(resource);
    }
    public Resource getFileResource(String path) {
        FileResource fileResource = new FileResource(path);
        return fileResource;
    }
}
