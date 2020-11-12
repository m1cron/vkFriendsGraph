package ru.micron;

import javafx.util.Pair;

import java.util.*;

public class Graph<T> {
    private final Map<T, Pair<Integer, List<T> > > MAP;

    public Graph() {
        MAP = Collections.synchronizedMap(new HashMap<>());
    }

    public Graph(int capacity, float loadFactor) {
        MAP = Collections.synchronizedMap(new HashMap<>(capacity, loadFactor));
    }

    public void addVertex(T s, int deepLen) {
        MAP.put(s, new Pair<>(deepLen, new LinkedList<>()));
    }

    public synchronized void addEdge(int deepLen,
                                     T source,
                                     T destination,
                                     boolean bidirectional) {

        if (!MAP.containsKey(source))
            addVertex(source, deepLen);

        if (!MAP.containsKey(destination))
            addVertex(destination, deepLen);
        MAP.get(source).getValue().add(destination);
        if (bidirectional) {
            MAP.get(destination).getValue().add(source);
        }
    }

    public int getVertexCount() {
        return MAP.keySet().size();
    }

    public int getEdgesCount(boolean bidirection) {
        int count = 0;
        for (T v : MAP.keySet()) {
            count += MAP.get(v).getValue().size();
        }
        if (bidirection) {
            count = count / 2;
        }
        return count;
    }

    public boolean hasVertex(T s) {
        return MAP.containsKey(s);
    }

    public boolean hasEdge(T s, T d) {
        return MAP.get(s).getValue().contains(d);
    }

    public int getMapSize() {
        return MAP.size();
    }

    public Map<T, Pair<Integer, List<T>>> getMap() {
        return MAP;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T v : MAP.keySet()) {
            builder.append(v.toString()).append(": ");
            builder.append(MAP.get(v).getKey().toString()).append("  ");
            for (T w : MAP.get(v).getValue()) {
                builder.append(w.toString()).append(" ");
            }
            builder.append("\n");
        }
        return (builder.toString());
    }
}