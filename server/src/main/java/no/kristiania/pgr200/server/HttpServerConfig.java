package no.kristiania.pgr200.server;

import no.kristiania.pgr200.common.PropertyReader;

import java.io.IOException;
import java.util.HashSet;

public class HttpServerConfig {

    public static String WEB_ROOT, DEFAULT_FILE, FILE_NOT_FOUND, METHOD_NOT_SUPPORTED, SERVER_NAME;
    public static HashSet<String> SUPPORTED_METHODS = new HashSet<>();

    static {
        try {
            PropertyReader propertyReader = new PropertyReader("/innlevering.properties");
            WEB_ROOT = propertyReader.getProperty("WEB_ROOT").trim();
            DEFAULT_FILE = propertyReader.getProperty("DEFAULT_FILE").trim();
            FILE_NOT_FOUND = propertyReader.getProperty("FILE_NOT_FOUND").trim();
            METHOD_NOT_SUPPORTED = propertyReader.getProperty("METHOD_NOT_SUPPORTED").trim();
            SERVER_NAME = propertyReader.getProperty("SERVER_NAME").trim();
            SUPPORTED_METHODS.add("GET");
            SUPPORTED_METHODS.add("POST");

        } catch (IOException e) {
            System.out.println("innlevering.properties not found!");
        }
    }
}
