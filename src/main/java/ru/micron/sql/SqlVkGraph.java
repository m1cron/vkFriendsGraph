package ru.micron.sql;

import ru.micron.Config;
import ru.micron.VkDeep;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SqlVkGraph {
    private final SqlHelper sqlHelper;

    public SqlVkGraph(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    public void clearVk() {
        sqlHelper.execute("DELETE FROM vk_graph");
    }

    public void add(VkDeep vk) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO vk_graph (src_id, dest_id, deep) VALUES (?, ?, ?)")) {
                ps.setInt(1, vk.getSrcId());
                ps.setInt(2, vk.getDestId());
                ps.setInt(3, vk.getDeep());
                ps.execute();
            }
            return null;
        });
    }

    public static void main(String[] args) {
        SqlVkGraph sql = new SqlVkGraph(Config.get().getDb_url(),
                        Config.get().getDb_user(),
                        Config.get().getDb_password());

        sql.add(new VkDeep(123, 12345, 1337));
    }
}
