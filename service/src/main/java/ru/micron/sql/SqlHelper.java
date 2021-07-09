package ru.micron.sql;

import ru.micron.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

  protected final ConnectionFactory CONN_FACTORY;

  public SqlHelper() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    CONN_FACTORY =
        () ->
            DriverManager.getConnection(
                Config.INSTANCE.getDb_url(),
                Config.INSTANCE.getDb_user(),
                Config.INSTANCE.getDb_password());
  }

  public void execute(String sql) {
    execute(sql, PreparedStatement::execute);
  }

  public <T> T execute(String sql, SqlExecutor<T> executor) {
    try (Connection conn = CONN_FACTORY.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      return executor.execute(ps);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public <T> T transactionalExecute(SqlTransaction<T> executor) {
    try (Connection conn = CONN_FACTORY.getConnection()) {
      try {
        conn.setAutoCommit(false);
        T res = executor.execute(conn);
        conn.commit();
        return res;
      } catch (SQLException e) {
        conn.rollback();
        throw e;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}