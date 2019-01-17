package no.kristiania.pgr200.db;

import no.kristiania.pgr200.common.PropertyReader;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.io.IOException;

public class LocalDataSource {

  private DataSource dataSource;
  private PropertyReader propertyreader;

  public LocalDataSource() throws IOException {
    propertyreader = new PropertyReader("/innlevering.properties");
    this.dataSource = createDataSource();
  }

  public DataSource createDataSource(){
    PGPoolingDataSource dataSource = new PGPoolingDataSource();
    dataSource.setUrl(propertyreader.getProperty("URL"));
    dataSource.setUser(propertyreader.getProperty("USER"));
    dataSource.setPassword(propertyreader.getProperty("PASSWORD"));

    Flyway flyway = Flyway.configure().dataSource(dataSource).load();
    flyway.migrate();
    return dataSource;
  }

  public DataSource resetDataSource() {
    PGPoolingDataSource dataSource = new PGPoolingDataSource();
    dataSource.setUrl(propertyreader.getProperty("URL"));
    dataSource.setUser(propertyreader.getProperty("USER"));
    dataSource.setPassword(propertyreader.getProperty("PASSWORD"));

    Flyway flyway = Flyway.configure().dataSource(dataSource).load();
    flyway.clean();
    flyway.migrate();
    return dataSource;
  }

  public static void main(String[] args) throws IOException {
    DataSource localDataSource = new LocalDataSource().createDataSource();
  }
}
