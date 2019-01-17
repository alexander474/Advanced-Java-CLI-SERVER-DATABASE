package no.kristiania.pgr200.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyReader {

  private Properties properties;

  public PropertyReader(String path) throws IOException {
    if(path == null) throw new IllegalArgumentException("Path is not valid.");
    this.properties = new Properties();
    properties.load(new InputStreamReader(PropertyReader.class.getResourceAsStream(path)));
  }

  public String getProperty(String name) {
    return this.properties.getProperty(name);
  }

  public void setProperty(String key, String value){
    this.properties.setProperty(key, value);
  }

}
