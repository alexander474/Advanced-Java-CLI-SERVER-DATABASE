package no.kristiania.pgr200.db;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DataSourceTest {

  @Test
  public void shouldReturnCorrectTestUser() throws SQLException {
    DataSource dataSource = new TestDataSource().createDataSource();
    assertThat(dataSource.getConnection().getMetaData().getUserName()).isEqualTo("SA");
    assertThat(dataSource.getConnection().getMetaData().getURL()).isEqualTo("jdbc:h2:mem:test");
  }


}
