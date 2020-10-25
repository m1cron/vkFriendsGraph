package ru.micron;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class MyProxy {
    protected final Gson gson;
    private final String proxyApi;
    private volatile Proxy proxy;

    public MyProxy(String proxyPing) {
        this.proxyApi = "https://www.proxyscan.io/api/proxy?format=json&uptime=75&last_check=600&ping=" + proxyPing;
        this.gson = new Gson();
        //getNewProxy();
    }

    public synchronized Proxy getNewProxy() {
        JsonArray proxyList = gson.fromJson(readStringFromURL(proxyApi), JsonArray.class);
        JsonObject proxyObj = (JsonObject) proxyList.get(0);

        String ip = proxyObj.get("Ip").toString().replace("\"", " ").trim();
        int port = proxyObj.get("Port").getAsInt();
        String proxyMode = proxyObj.getAsJsonArray("Type").get(0).toString();

        System.setProperty("socksProxyVersion", proxyMode.contains("4") ? "4" : "5");
        proxy = new Proxy(proxyMode.contains("SOCKS") ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        System.out.println("getNewProxy >> connect to " + ip + "\t\t\t" + port + "\t\t" + proxyMode + "\t\tfrom  " + Thread.currentThread().getName());
        return proxy;
    }

    public String readStringFromURL(String url) {
        URLConnection urlCon;
        try {
            urlCon = new URL(url).openConnection();
            urlCon.setConnectTimeout(2000);
            urlCon.setReadTimeout(3000);
            return scanInStream(urlCon.getInputStream());
        } catch (IOException | IllegalArgumentException e) {
            return readStringFromURL(url, this);
        }
    }

    public String readStringFromURL(String url, MyProxy myProxy) {
        URLConnection urlCon;
        try {
            try {
                urlCon = new URL(url).openConnection(myProxy.getProxy());
                urlCon.setConnectTimeout(2000);
                urlCon.setReadTimeout(3000);
            } catch (IllegalArgumentException e) {
                myProxy.getNewProxy();
                return readStringFromURL(url, myProxy);
            }
            return scanInStream(urlCon.getInputStream());
        } catch (IOException e) {
            return readStringFromURL(url, myProxy);
        }
    }

    private String scanInStream(InputStream stream) {
        try (Scanner scanner = new Scanner(stream, Charset.defaultCharset()).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Proxy getProxy() {
        return proxy;
    }
}