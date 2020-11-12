package ru.micron;

import ru.micron.sql.SqlHelper;

import java.io.*;
import java.sql.*;

public class SqlVk extends SqlHelper {

    public void clear() {
        execute("DELETE FROM vk_graph");
    }

    public void add(VkDeep vk) {
        transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO vk_graph (src_id, dest_id, deep) VALUES (?, ?, ?)")) {
                ps.setInt(1, vk.getSrcId());
                ps.setInt(2, vk.getDestId());
                ps.setInt(3, vk.getDeep());
                ps.execute();
            }
            return null;
        });
    }

    public void export(String csvName) {
        execute("SELECT * FROM vk_graph", conn -> {
            try (ResultSet result = conn.executeQuery();
                 BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvName))) {
                fileWriter.write("src_id,dest_id,deep\n");
                while (result.next()) {
                    fileWriter.write(String.format("%d,%d,%d\n",
                            result.getInt("src_id"),
                            result.getInt("dest_id"),
                            result.getInt("deep")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}