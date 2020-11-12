package ru.micron;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class MyProxy {
    protected final Gson GSON;
    private final String PROXY_API_URL;
    private Proxy proxy;
    protected final boolean USE_PROXY;

    public MyProxy(String proxyPing, boolean useProxy) {
        this.PROXY_API_URL = "https://www.proxyscan.io/api/proxy?format=json&uptime=75&last_check=600&ping=" + proxyPing;
        this.GSON = new Gson();
        if ((this.USE_PROXY = useProxy)) {
            getNewProxy();
        }
    }

    public synchronized Proxy getNewProxy() {
        JsonArray proxyList = GSON.fromJson(readStringFromURL(PROXY_API_URL), JsonArray.class);
        JsonObject proxyObj = (JsonObject) proxyList.get(0);

        String ip = proxyObj.get("Ip").toString().replace("\"", "");
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
        } catch (IOException e) {
            System.out.println("Enabled proxy!");
            return readStringFromURL(url, getNewProxy());
        }
    }

    public String readStringFromURL(String url, Proxy myProxy) {
        URLConnection urlCon;
        try {
            urlCon = new URL(url).openConnection(myProxy);
            urlCon.setConnectTimeout(2000);
            urlCon.setReadTimeout(3000);
            return scanInStream(urlCon.getInputStream());
        } catch (IOException e) {
            return readStringFromURL(url, getNewProxy());
        }
    }

    private String scanInStream(InputStream stream) {
        String inputLine;
        StringBuilder response = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public Proxy getProxy() {
        return proxy;
    }
}