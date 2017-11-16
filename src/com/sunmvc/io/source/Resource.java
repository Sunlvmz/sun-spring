package com.sunmvc.io.source;

import java.io.IOException;
import java.io.InputStream;

/**
 * Resource是spring内部定位资源的接口。接口
 */
public interface Resource {
    //spring.core.io中有很多种的Resource，对应不同情况； resource中也有不同描述方法
    InputStream getInputStream() throws IOException;
    String getDescription();
}
