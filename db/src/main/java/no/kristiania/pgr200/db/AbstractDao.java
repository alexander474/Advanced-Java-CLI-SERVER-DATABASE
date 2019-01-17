package no.kristiania.pgr200.db;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao {

  protected final DataSource dataSource;

  public AbstractDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public <T> T readOne(String sql, ResultSetMapper<T> mapper, long id) throws SQLException {
      PreparedStatement statement = makePreparedStatement(sql);
        statement.setLong(1, id);
        try (ResultSet rs = statement.executeQuery()) {
          if (rs.next()) {
            return mapper.mapResultSet(rs);
          }
          return null;
        }
  }

  protected <T> List<T> list(String sql, ResultSetMapper<T> mapper) throws SQLException {
      PreparedStatement statement = makePreparedStatement(sql);
        try (ResultSet rs = statement.executeQuery()) {
          List<T> result = new ArrayList<>();
          while (rs.next()) {
            result.add(mapper.mapResultSet(rs));
          }
          return result;
        }
  }

  protected long executeInsertAndReturnPrimaryKey(String sql, Object[] obj) throws SQLException {
    try (Connection conn = dataSource.getConnection()) {
      try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        for (int i = 0; i < obj.length; i++) {
          statement.setObject(i + 1, obj[i]);
        }
        int rows = statement.executeUpdate();

        if (rows == 0) {
          throw new SQLException("Ingen rader ble satt inn");
        }

        try (ResultSet keys = statement.getGeneratedKeys()) {
          keys.next();
          return keys.getLong(1);
        }
      }
    }
  }

  protected void deleteOneById(String sql, long id) throws SQLException {
      PreparedStatement statement = makePreparedStatement(sql);
        statement.setInt(1, (int) id);
        statement.executeUpdate();

  }

  protected <T> void updateOneById(String sql, T[] values) throws SQLException {
      PreparedStatement statement = makePreparedStatement(sql);
        for (int i = 0; i < values.length; i++) {
          statement.setObject(i + 1, values[i]);
        }
        statement.executeUpdate();
  }

  protected PreparedStatement makePreparedStatement(String sql) {
    try {
        return dataSource.getConnection().prepareStatement(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
