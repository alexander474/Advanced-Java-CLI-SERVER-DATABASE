package no.kristiania.pgr200.db;

import java.sql.SQLException;
import java.util.List;

public interface DataAccessObject<T> {

  void create(T object) throws SQLException;

  T readOne(long id) throws SQLException;

  List<T> readAll() throws SQLException;

  void deleteOneById(long id) throws SQLException;

  void updateOneById(T object) throws SQLException;

}