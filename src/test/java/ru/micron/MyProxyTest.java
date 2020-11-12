package ru.micron;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyProxyTest {

    @Test
    public void getNewProxy() {
        MyProxy myProxy = new MyProxy("100", false);
        for (int i = 0; i < 10; i++) {
            myProxy.getNewProxy();
            assertNotNull(myProxy.getProxy());
        }
    }

    @Test
    public void readStringFromURL() {
        String url = "https://api.github.com/";
        MyProxy myProxy = new MyProxy("100", false);
        for (int i = 0; i < 10; i++) {
            assertNotNull(myProxy.readStringFromURL(url));
        }
    }

    @Test
    public void testReadStringFromURL() {
        String url = "https://api.github.com/";
        MyProxy myProxy = new MyProxy("100", true);
        for (int i = 0; i < 10; i++) {
            assertNotNull(myProxy.readStringFromURL(url, myProxy.getProxy()));
        }
    }
}