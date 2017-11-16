package com.sunmvc.io.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLResource implements Resource {
    private URL url;

    public URLResource(URL url) {
        this.url = url;
    }
    public URL getURL() throws IOException {
        return this.url;
    }
    @Override
    public String getDescription() {
        return "URL [" + this.url + "]";
    }
    @Override
    //根据URL载入输入流
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();//todo spring.io中还对此处进行异常处理，在connection是httpconnection时要finally关闭资源
    }
}
