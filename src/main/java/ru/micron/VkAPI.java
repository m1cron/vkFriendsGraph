package ru.micron;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.concurrent.*;

public class VkAPI extends MyProxy {
    private static String ACCESS_TOKEN;
    private final int maxDeep;
    private final ForkJoinPool threadPool;
    private final Graph<Integer> graph;

    public VkAPI(String token, int maxDeep) {
        super("100", false);
        ACCESS_TOKEN = token;
        this.maxDeep = maxDeep;
        graph = new Graph<>(48000, 0.75f);
        threadPool = new ForkJoinPool(200);
    }

    private void getFriends(Integer id, int deep) {
        if (deep - 1 == maxDeep) {
            return;
        }

        String stringJson = readStringFromURL(parseLink(id));
        JsonObject response = gson.fromJson(stringJson, JsonObject.class);
        if (response.isJsonNull() || response.has("error")) {
            return;
        }
        JsonArray friendsJsonArray = response.getAsJsonObject("response").getAsJsonArray("items");

        short friendsCounter = 0;
        for (JsonElement jsonElement : friendsJsonArray) {
            graph.addEdge(id, jsonElement.getAsInt(), true);
            friendsCounter++;
        }
        System.out.println("======= " + id + " Pars OK! ======= | DEEP >> " + deep + " | " + friendsCounter + " |\t" + graph.getMapSize() + " | \t\t" + Thread.currentThread().getName() + " |\tthreads count >>\t" + Thread.activeCount());

        for (JsonElement jsonElement : friendsJsonArray) {
            threadPool.invoke(new RecursiveAction() {
                @Override
                protected void compute() {
                    getFriends(jsonElement.getAsInt(), deep + 1);
                }
            });
        }
    }

    public void getDeepFriends(String id) {

        getFriends(Integer.parseInt(id), 1);
        System.out.println(graph.toString());
        /*graph.hasEdge(171728534, 143711919);
        graph.hasEdge(212538049, 171728534);
        graph.hasEdge(171728534, 212538049);*/

    }

    public static String parseLink(Integer id) {
        return new StringBuilder("https://api.vk.com/method/friends.get?v=5.124&access_token=").append(ACCESS_TOKEN).append("&user_id=").append(id).toString();
    }
}