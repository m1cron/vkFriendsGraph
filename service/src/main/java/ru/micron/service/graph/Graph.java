package ru.micron.service.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Graph<T> {

  private Map<T, List<T>> map = new HashMap<>(1_000);

  public void addVertex(T s) {
    map.put(s, new ArrayList<>());
  }

  public void addEdge(
      T source, T destination, boolean bidirectional
  ) {
    if (!map.containsKey(source)) {
      addVertex(source);
    }

    if (!map.containsKey(destination)) {
      addVertex(destination);
    }

    map.get(source).add(destination);
    if (bidirectional) {
      map.get(destination).add(source);
    }
  }

  public int getVertexCount() {
    return map.keySet().size();
  }

  public int getEdgesCount(boolean bidirection) {
    int count = 0;
    for (T v : map.keySet()) {
      count += map.get(v).size();
    }
    return bidirection
        ? count / 2
        : count;
  }

  public boolean hasVertex(T s) {
    return map.containsKey(s);
  }

  public boolean hasEdge(T s, T d) {
    return map.get(s).contains(d);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (T v : map.keySet()) {
      builder.append(v.toString()).append(": ");
      for (T w : map.get(v)) {
        builder.append(w.toString()).append(" ");
      }
      builder.append("\n");
    }
    return (builder.toString());
  }
}
