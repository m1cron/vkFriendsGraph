package ru.micron;

import org.junit.Test;

public class SqlVkTest {

    @Test
    public void clear() {
        new SqlVk().clear();
    }

    @Test
    public void add() {
        SqlVk sql = new SqlVk();
        sql.add(new VkDeep(12345, 54321, 10));
        sql.clear();
    }

    @Test
    public void export() {
        SqlVk sql = new SqlVk();
        sql.export("result.csv");
    }
}