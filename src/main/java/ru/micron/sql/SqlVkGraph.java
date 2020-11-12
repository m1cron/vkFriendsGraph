package ru.micron.sql;

import ru.micron.Config;
import ru.micron.VkDeep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlVkGraph {
    protected final ConnectionFactory connectionFactory;

    public SqlVkGraph(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void clearVk() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM vk_graph")) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(VkDeep vk) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO vk_graph (src_id, dest_id, deep) VALUES (?, ?, ?)")) {
            ps.setInt(1, vk.getSrcId());
            ps.setInt(2, vk.getDestId());
            ps.setInt(3, vk.getDeep());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SqlVkGraph sql = new SqlVkGraph(Config.get().getDb_url(),
                        Config.get().getDb_user(),
                        Config.get().getDb_password());

        sql.add(new VkDeep(123, 12345, 1337));
    }
}
