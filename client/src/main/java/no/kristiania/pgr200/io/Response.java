package no.kristiania.pgr200.io;

import com.google.gson.*;
import no.kristiania.pgr200.common.HttpClientResponse;

import java.util.Map;
import java.util.Set;


public class Response {
    private int statusCode;
    private String body, content_type;

    public Response(HttpClientResponse httpClientResponse) {
        setContent_type(httpClientResponse.getHeader("Content-Type"));
        setStatusCode(httpClientResponse.getStatusCode());
        setBody(httpClientResponse.getBody());
    }

    public Response(int statusCode, String body, String content_type) {
        setContent_type(content_type);
        setStatusCode(statusCode);
        setBody(body);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    private void setBody(String body) {
        if (getContent_type() != null && getContent_type().equals("application/json")) {
            this.body = toPrettyJson(body);
        } else {
            this.body = body;
        }
    }

    private String toPrettyJson(String jsonString) {
        StringBuilder sb = new StringBuilder();
        Object o = new Gson().fromJson(jsonString, JsonElement.class);
        if (o instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray) o;
            for (JsonElement j : jsonArray) {
                jsonObjectToString(sb, j.getAsJsonObject().entrySet());
            }
        } else if (o instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) o;
            jsonObjectToString(sb, jsonObject.entrySet());
        }

        return sb.toString();
    }

    private String jsonObjectToString(StringBuilder sb, Set<Map.Entry<String, JsonElement>> jsonSet) {
        for (Map.Entry entry : jsonSet) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    @Override
    public String toString() {
        return "Response{" +
                "statusCode=" + statusCode +
                ", body='" + body + '\'' +
                ", content_type='" + content_type + '\'' +
                '}';
    }
}
