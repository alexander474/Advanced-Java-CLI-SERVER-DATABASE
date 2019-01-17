package no.kristiania.pgr200.server.requesthandlers;

import no.kristiania.pgr200.server.HttpServerRequest;
import no.kristiania.pgr200.server.HttpServerResponse;
import no.kristiania.pgr200.server.HttpServerRouter;
import no.kristiania.pgr200.server.controllers.AbstractController;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

public class HttpServerRequestHandlerCapi implements HttpServerRequestHandler {

    Map<String, Integer> params = new HashMap<>();
    List<String> patterns = Arrays.asList("capi/conference/:id", "capi/conference", "capi/track/:id", "capi/track", "capi/talk/:id", "capi/talk");
    DataSource dataSource;
    public HttpServerRequestHandlerCapi(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean handleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {

        for (String pattern : patterns) {
            if (requestMatches(request.getPath(), pattern, params)) {
                if(routeRequest(request, response)) {
                    response.setContentType("application/json");
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private boolean requestMatches(String path, String pattern, Map<String, Integer> params) {
        params.clear();
        String actualParts[] = path.split("/");
        String patternParts[] = pattern.split("/");
        if (actualParts.length != patternParts.length) {
            return false;
        }
        for (int i = 0; i < patternParts.length; i++) {
            if (patternParts[i].startsWith(":")) {
                try {
                    params.put(patternParts[i], Integer.parseInt(actualParts[i]));
                } catch (NumberFormatException e) {
                    return false;
                }

            } else if (!actualParts[i].equals(patternParts[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean routeRequest(HttpServerRequest request, HttpServerResponse response) {
        AbstractController controller = new HttpServerRouter().route(request.getURL(), this.dataSource);
        if(controller != null) {
            controller.handleRequest(request, response, params);
            return true;
        }
        return false;
    }
}
