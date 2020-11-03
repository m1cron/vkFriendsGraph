package ru.micron;

import javafx.util.Pair;

import java.util.*;

public class Graph<T> {

    // We use Hashmap to store the edges in the graph
    private final Map<T, Pair<Integer, List<T> > >map;

    public Graph() {
        map = Collections.synchronizedMap(new HashMap<>());
    }

    public Graph(int capacity, float loadFactor) {
        map = Collections.synchronizedMap(new HashMap<>(capacity, loadFactor));
    }

    // This function adds a new vertex to the graph
    public void addVertex(T s, int deepLen) {
        map.put(s, new Pair<>(deepLen, new LinkedList<>()));
    }

    // This function adds the edge
    // between source to destination
    public synchronized void addEdge(int deepLen,
                                        T source,
                                        T destination,
                                        boolean bidirectional) {

        if (!map.containsKey(source))
            addVertex(source, deepLen);

        if (!map.containsKey(destination))
            addVertex(destination, deepLen);
        map.get(source).getValue().add(destination);
        if (bidirectional) {
            map.get(destination).getValue().add(source);
        }
    }

    // This function gives the count of vertices
    public void getVertexCount() {
        System.out.println("The graph has " + map.keySet().size() + " vertex");
    }

    // This function gives the count of edges
    public void getEdgesCount(boolean bidirection) {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).getValue().size();
        }
        if (bidirection) {
            count = count / 2;
        }
        System.out.println("The graph has " + count + " edges.");
    }

    // This function gives whether
    // a vertex is present or not.
    public void hasVertex(T s) {
        if (map.containsKey(s)) {
            System.out.println("The graph contains " + s + " as a vertex.");
        } else {
            System.out.println("The graph does not contain " + s + " as a vertex.");
        }
    }

    // This function gives whether an edge is present or not.
    public void hasEdge(T s, T d) {
        if (map.get(s).getValue().contains(d)) {
            System.out.println("The graph has an edge between " + s + " and " + d + " deep " + map.get(s).getKey());
        } else {
            System.out.println("The graph has no edge between " + s + " and " + d + " deep " + map.get(s).getKey());
        }
    }

    public int getMapSize() {
        return map.size();
    }

    // Prints the adjancency list of each vertex.
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