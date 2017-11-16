package com.sunmvc.io.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileResource implements Resource{

        private final File file;

        private final String path;

        public FileResource(File file){
            this.file = file;
            this.path = file.getAbsolutePath();
        }

        public FileResource(String path){
            this.path = path;
            this.file = new File(path);
        }
        /*
         * 实现接口Resource中的getDescription()方法，(资源的描述)
         */
        @Override
        public String getDescription(){
            return "资源的描述："+file.getName()+
                    "  "+"地址为："+path;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new FileInputStream(this.file);
        }

        public File getFile() throws IOException {
            return this.file;
        }

    }

