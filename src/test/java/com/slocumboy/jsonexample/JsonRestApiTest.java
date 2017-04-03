package com.slocumboy.jsonexample;

import javafx.util.Pair;
import org.xml.sax.SAXException;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by davidrho on 4/2/17.
 */
public class JsonRestApiTest {

    protected Pair<Integer, String> performGetRequest(URL url) throws Exception {

        System.out.println("Request url " + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        conn.setRequestProperty("Content-type", "application/json");

        String response = null;

        int status = conn.getResponseCode();

        if (status  >= 200 && status <= 299) {
            response = getGoodResponse(conn);
        } else {
            response = getErrorResponse(conn);
        }

        return new Pair<>(status, response);
    }

    private static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    protected JsonObject getJsonObjectFromString(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));

        return reader.readObject();
    }

    protected JsonArray getJsonArrayFromString(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));

        return reader.readArray();
    }


    private String getGoodResponse(HttpURLConnection conn) throws IOException, SAXException {
       return read(conn.getInputStream());
    }

    private String getErrorResponse(HttpURLConnection conn) throws IOException, SAXException {
        return read(conn.getErrorStream());
    }

    public String prettyPrint(JsonStructure json) {
        return jsonFormat(json, JsonGenerator.PRETTY_PRINTING);
    }

    public String jsonFormat(JsonStructure json, String... options) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Boolean> config = buildConfig(options);
        JsonWriterFactory writerFactory = Json.createWriterFactory(config);
        JsonWriter jsonWriter = writerFactory.createWriter(stringWriter);

        jsonWriter.write(json);
        jsonWriter.close();

        return stringWriter.toString();
    }

    private Map<String, Boolean> buildConfig(String... options) {
        Map<String, Boolean> config = new HashMap<String, Boolean>();

        if (options != null) {
            for (String option : options) {
                config.put(option, true);
            }
        }

        return config;
    }
}
