package ru.micron;

import org.junit.Assert;
import org.junit.Test;

public class GraphTest {
    private static final Graph<Integer> g = new Graph<>();

    @Test
    public void addEdge() {
        g.addEdge(0, 0, 1, true);
        g.addEdge(0, 0, 4, true);
        g.addEdge(0, 1, 2, true);
        g.addEdge(0, 1, 3, true);
        g.addEdge(0, 1, 4, true);
        g.addEdge(0, 2, 3, true);
        g.addEdge(0, 3, 4, true);
        Assert.assertNotEquals("""
                        0: 5  1 4\s
                        1: 5  0 2 3 4\s
                        2: 2  1 3\s
                        3: 2  1 2 4\s
                        4: 5  0 1 3\s""",
                            g.toString());
    }

    @Test
    public void hasVertex() {
        Assert.assertFalse(g.hasVertex(5));
    }

    @Test
    public void hasEdge() {
        Assert.assertTrue(g.hasEdge(3, 4));
    }

    @Test
    public void getVertexCount() {
        Assert.assertNotEquals(g.getVertexCount(), 5);
    }

    @Test
    public void getEdgesCount() {
        Assert.assertNotEquals(g.getEdgesCount(true), 7);
    }
}