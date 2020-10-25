package ru.micron;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import javafx.util.Pair;

import javax.naming.InsufficientResourcesException;
import javax.swing.plaf.TableHeaderUI;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VkAPI extends MyProxy {
    private static String ACCESS_TOKEN;
    private final List<List<Integer>> ids;
    private final List<Thread> threadsList;
    private final int maxDeep;
    private final ExecutorService executorService;
    private final Lock lock;

    public VkAPI(String token, String id) {
        super("100");
        lock = new ReentrantLock();
        ACCESS_TOKEN = token;
        ids = Collections.synchronizedList(new ArrayList<>(16000));
        maxDeep = 2;


        threadsList = new ArrayList<>(1000);
        executorService = Executors.newFixedThreadPool(100);
        Map<Integer, List<Integer>> map = new HashMap<>();


        Integer intId = Integer.parseInt(id);

        getFriends(intId, 1);
        while (!executorService.isShutdown()) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        System.out.println(searchInList(178597474));
    }

    public boolean searchInList(Integer id) {
        for (List<Integer> integers : ids) {
            for (Integer integer : integers) {
                return (integer.equals(id));
            }
        }
        return false;
    }

    public void getFriends(Integer id, int deep) {
        if (deep - 1 == maxDeep) {
            return;
        }

        String stringJson = readStringFromURL(parseLink(id));
        JsonObject response = gson.fromJson(stringJson, JsonObject.class);
        if (response.isJsonNull() || response.has("error")) {
            return;
        }
        response = response.getAsJsonObject("response");
        int idArrSize = response.get("count").getAsInt();
        List<Integer> idArr = new ArrayList<>(idArrSize);
        JsonArray arr = response.getAsJsonArray("items");

        for (int i = 0; i < arr.size(); i++) {
            idArr.add(arr.get(i).getAsInt());
        }
        ids.add(idArr);
        System.out.println("========== " + id + " Pars OK! ==========DEEP >> " + deep + "\t" + ids.size() + "\t" + Thread.currentThread().getName() + "\tthreads count >>\t" + Thread.activeCount());

        for (Integer integer : idArr) {
            executorService.execute(() -> getFriends(integer, deep + 1));
        }
    }

    public static String parseLink(Integer id) {
        return new StringBuilder("https://api.vk.com/method/friends.get?v=5.124&access_token=").append(ACCESS_TOKEN).append("&user_id=").append(id).toString();
    }
}