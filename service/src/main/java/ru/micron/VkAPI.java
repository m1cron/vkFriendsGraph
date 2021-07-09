/*
package ru.micron;

import java.util.List;
import java.util.concurrent.*;

public class VkAPI {

  private final String ACCESS_TOKEN;
  private final int MAX_DEEP;
  private final ForkJoinPool threadPool = new ForkJoinPool(200);
  private final Graph GRAPH = new Graph<>();

  public VkAPI(String maxDeep, String token) {
    this.ACCESS_TOKEN = token;
    this.MAX_DEEP = Integer.parseInt(maxDeep);
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
    System.out.println(
        "======= "
            + id
            + " ======= | DEEP >> "
            + deep
            + " |\t"
            + "\t| threads count >>\t"
            + Thread.activeCount());

    for (JsonElement jsonElement : friendsJsonArray) {
      threadPool.invoke(
          new RecursiveAction() {
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
    System.out.println("Search time: " + (endTime - startTime) + "ms");
  }

  public void searchDeps(List<Integer> ids) {
    for (int i = 1; i < ids.size(); i++) {
      // add
sqlVk.add(
          new VkDeep(
              ids.get(0),
              ids.get(i),
              GRAPH.hasVertex(ids.get(i)) ? GRAPH.getMap().get(ids.get(i)).getKey() : 0));

    }
  }

  private String parseLink(Integer id) {
    return new StringBuilder("https://api.vk.com/method/friends.get?v=5.124&access_token=")
        .append(ACCESS_TOKEN)
        .append("&user_id=")
        .append(id)
        .toString();
  }
}
*/
