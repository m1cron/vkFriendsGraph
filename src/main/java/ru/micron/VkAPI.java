package ru.micron;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.*;

public class VkAPI extends MyProxy {
    private final String ACCESS_TOKEN;
    private final int MAXDEEP;
    private final ForkJoinPool threadPool;
    private final Graph<Integer> graph;

    public VkAPI(String maxDeep, String token) {
        super("100", false);
        this.ACCESS_TOKEN = token;
        this.MAXDEEP = Integer.parseInt(maxDeep);
        threadPool = new ForkJoinPool(200);
        graph = new Graph<>(48000, 0.75f);
    }

    private void getFriends(Integer id, int deep) {
        if (deep - 1 == MAXDEEP) return;

        String stringJson = readStringFromURL(parseLink(id));
        JsonObject response = gson.fromJson(stringJson, JsonObject.class);
        if (response.isJsonNull() || response.has("error")) return;

        JsonArray friendsJsonArray = response.getAsJsonObject("response").getAsJsonArray("items");
        for (JsonElement jsonElement : friendsJsonArray) {
            graph.addEdge(deep, id, jsonElement.getAsInt(), true);
        }
        System.out.println("======= " + id + " ======= | DEEP >> " + deep + " |\t" + graph.getMapSize() + "\t| threads count >>\t" + Thread.activeCount());

        for (JsonElement jsonElement : friendsJsonArray) {
            threadPool.invoke(new RecursiveAction() {
                @Override
                protected void compute() {
                    getFriends(jsonElement.getAsInt(), deep + 1);
                }
            });
        }
    }

    public void findFriends(Integer id) {
        long startTime = System.currentTimeMillis();
        getFriends(id, 1);
        long endTime = System.currentTimeMillis();
        System.out.println("Search time: " + (endTime-startTime) + "ms");
    }

    public void searchDeps(List<Integer> ids) {
        for (int i = 1; i < ids.size(); i++) {
            // graph.hasEdge(ids.get(0), ids.get(i));
            // TODO write in PostgreSQL
        }
    }

    private String parseLink(Integer id) {
        return new StringBuilder("https://api.vk.com/method/friends.get?v=5.124&access_token=").append(ACCESS_TOKEN).append("&user_id=").append(id).toString();
    }
}