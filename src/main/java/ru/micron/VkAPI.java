package ru.micron;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.*;

public class VkAPI extends MyProxy {
    private final String ACCESS_TOKEN;
    private final int MAX_DEEP;
    private final ForkJoinPool THREAD_POOL;
    private final Graph<Integer> GRAPH;

    public VkAPI(String maxDeep, String token) {
        super("100", false);
        this.ACCESS_TOKEN = token;
        this.MAX_DEEP = Integer.parseInt(maxDeep);
        THREAD_POOL = new ForkJoinPool(200);
        GRAPH = new Graph<>(48000, 0.75f);
    }

    private void getFriends(Integer id, int deep) {
        if (deep - 1 == MAX_DEEP) return;

        String stringJson = readStringFromURL(parseLink(id));
        JsonObject response = GSON.fromJson(stringJson, JsonObject.class);
        if (response.isJsonNull() || response.has("error")) return;

        JsonArray friendsJsonArray = response.getAsJsonObject("response").getAsJsonArray("items");
        for (JsonElement jsonElement : friendsJsonArray) {
            GRAPH.addEdge(deep, id, jsonElement.getAsInt(), true);
        }
        System.out.println("======= " + id + " ======= | DEEP >> " + deep + " |\t" + GRAPH.getMapSize() + "\t| threads count >>\t" + Thread.activeCount());

        for (JsonElement jsonElement : friendsJsonArray) {
            THREAD_POOL.invoke(new RecursiveAction() {
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
            SqlVk.add(new VkDeep(ids.get(0), ids.get(i), GRAPH.hasVertex(ids.get(i)) ? GRAPH.getMap().get(ids.get(i)).getKey() : 0));
        }
    }

    private String parseLink(Integer id) {
        return new StringBuilder("https://api.vk.com/method/friends.get?v=5.124&access_token=").append(ACCESS_TOKEN).append("&user_id=").append(id).toString();
    }
}