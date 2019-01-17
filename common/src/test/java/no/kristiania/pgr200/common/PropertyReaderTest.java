package no.kristiania.pgr200.common;

import no.kristiania.pgr200.common.PropertyReader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PropertyReaderTest {

  PropertyReader pr;

  @Before
  public void getTestsReady() throws IOException {
    pr  = new PropertyReader("/innlevering.properties");
  }

  @Test
  public void shouldCheckForExceptionInProperties() {
    assertThatThrownBy(() -> {
      new PropertyReader(null);
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Path is not valid.");
  }

  @Test
  public void shouldSetProperty() {
    assertThat(pr.getProperty("URL")).isEqualTo("jdbc:postgresql://localhost/conference");
    pr.setProperty("URL", "new value");
    assertThat(pr.getProperty("URL")).isNotEqualTo("jdbc:postgresql://localhost/conference");
  }
}
