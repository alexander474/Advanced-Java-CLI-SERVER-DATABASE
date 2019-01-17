package no.kristiania.pgr200.db;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class TestDataSource {

  Flyway flyway;

  public DataSource createDataSource(){
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    dataSource.setUser("SA");

    flyway = Flyway.configure().dataSource(dataSource).load();
    flyway.migrate();

    return dataSource;
  }

  public void dropTables() {
    flyway.clean();
  }
}