package ru.micron.service.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph {

  private Map<Vertex, List<Vertex>> adjVertices;

  List<Vertex> getAdjVertices(String label) {
    return adjVertices.get(new Vertex(label));
  }

  public void addVertex(String label) {
    adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
  }

  public void removeVertex(String label) {
    var vertex = new Vertex(label);
    adjVertices.values().forEach(e -> e.remove(vertex));
    adjVertices.remove(new Vertex(label));
  }

  public void addEdge(String label1, String label2) {
    var v1 = new Vertex(label1);
    var v2 = new Vertex(label2);
    adjVertices.get(v1).add(v2);
    adjVertices.get(v2).add(v1);
  }

  public void removeEdge(String label1, String label2) {
    Vertex v1 = new Vertex(label1);
    Vertex v2 = new Vertex(label2);
    List<Vertex> eV1 = adjVertices.get(v1);
    List<Vertex> eV2 = adjVertices.get(v2);
    if (eV1 != null)
      eV1.remove(v2);
    if (eV2 != null)
      eV2.remove(v1);
  }
}
