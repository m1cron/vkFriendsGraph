package ru.micron;

import java.util.*;
import org.apache.commons.lang3.tuple.Pair;

public class Graph<T> {

  private final Map<T, Pair<Integer, List<T>>> map;

  public Graph() {
    map = Collections.synchronizedMap(new HashMap<>());
  }

  public Graph(int capacity, float loadFactor) {
    map = Collections.synchronizedMap(new HashMap<>(capacity, loadFactor));
  }

  public void addVertex(T s, int deepLen) {
    map.put(s, new Pair<>(deepLen));
  }

  public synchronized void addEdge(int deepLen, T source, T destination, boolean bidirectional) {

    if (!map.containsKey(source)) addVertex(source, deepLen);

    if (!map.containsKey(destination)) addVertex(destination, deepLen);
    map.get(source).getValue().add(destination);
    if (bidirectional) {
      map.get(destination).getValue().add(source);
    }
  }

  public int getVertexCount() {
    return map.keySet().size();
  }

  public int getEdgesCount(boolean bidirection) {
    int count = 0;
    for (T v : map.keySet()) {
      count += map.get(v).getValue().size();
    }
    if (bidirection) {
      count = count / 2;
    }
    return count;
  }

  public boolean hasVertex(T s) {
    return map.containsKey(s);
  }

  public boolean hasEdge(T s, T d) {
    return map.get(s).getValue().contains(d);
  }

  public int getMapSize() {
    return map.size();
  }

  public Map<T, Pair<Integer, List<T>>> getMap() {
    return map;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (T v : map.keySet()) {
      builder.append(v.toString()).append(": ");
      builder.append(map.get(v).getKey().toString()).append("  ");
      for (T w : map.get(v).getValue()) {
        builder.append(w.toString()).append(" ");
      }
      builder.append("\n");
    }
    return (builder.toString());
  }
}