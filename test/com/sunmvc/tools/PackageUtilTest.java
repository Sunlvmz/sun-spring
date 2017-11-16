package com.sunmvc.tools;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PackageUtilTest {
    @Test
    public void getClassName() throws Exception {
       List<String> lists =  PackageUtil.getClassName("com.sunmvc.beans.factory");
        System.out.println("dd");
    }

}