package ru.micron;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.concurrent.*;

public class VkAPI extends MyProxy {
    private final String ACCESS_TOKEN;
    private final int MAXDEEP;
    private final ForkJoinPool threadPool;
    private final Graph<Integer> graph;

    public VkAPI(String token, int maxDeep) {
        super("100", false);
        this.ACCESS_TOKEN = token;
        this.MAXDEEP = maxDeep;
        threadPool = new ForkJoinPool(200);
        graph = new Graph<>(48000, 0.75f);
    }

    private void getFriends(Integer id, int deep) {
        if (deep - 1 == MAXDEEP) {
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
        System.out.println("======= " + id + " ======= | DEEP >> " + deep + " | " + friendsCounter + " |\t" + graph.getMapSize() + " | " + Thread.currentThread().getName() + " \t\t|\tthreads count >>\t" + Thread.activeCount());

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
        long startTime = System.currentTimeMillis();

        getFriends(Integer.parseInt(id), 1);

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");

        System.out.println(graph.toString());
        /*graph.hasEdge(171728534, 143711919);
        graph.hasEdge(212538049, 171728534);
        graph.hasEdge(171728534, 212538049);*/

    }

    private String parseLink(Integer id) {
        return new StringBuilder("https://api.vk.com/method/friends.get?v=5.124&access_token=").append(ACCESS_TOKEN).append("&user_id=").append(id).toString();
    }
}