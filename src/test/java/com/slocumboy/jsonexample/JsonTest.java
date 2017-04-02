package com.slocumboy.jsonexample;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParsingException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by davidrho on 4/2/17.
 */
public class JsonTest {

    @Test
    public void testBooks() throws Exception {
        final String host = "localhost";
        final int bookRestApiPort = 8080;
        final String booksPath = "/books";

        URL booksUrl = new URL("http", host, bookRestApiPort, booksPath);

        String response = performGetRequest(booksUrl);

        JsonArray jsonArray = getJsonArrayFromString(response);

        // Assert each book entry has a booKId which is a string.
        jsonArray.stream().
                forEach((value -> {
                    JsonObject jsonObject = (JsonObject) value;
                    assertNotNull(jsonObject.getString("bookId"));
                }));

        System.out.println("json response " + prettyPrint(jsonArray));

    }


    /*
      Test to make sure if malformed json throws exception.  Silly but just making sure.
     */
    @Test(expected = JsonParsingException.class)
    public void testBadJson() {
        String badJson = "bad json";

        JsonObject jsonObject = getJsonObjectFromString(badJson);

    }

    private JsonObject getJsonObjectFromString(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));

        return reader.readObject();
    }

    private JsonArray getJsonArrayFromString(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));

        return reader.readArray();
    }

    public static String prettyPrint(JsonStructure json) {
        return jsonFormat(json, JsonGenerator.PRETTY_PRINTING);
    }

    public static String jsonFormat(JsonStructure json, String... options) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Boolean> config = buildConfig(options);
        JsonWriterFactory writerFactory = Json.createWriterFactory(config);
        JsonWriter jsonWriter = writerFactory.createWriter(stringWriter);

        jsonWriter.write(json);
        jsonWriter.close();

        return stringWriter.toString();
    }

    private static Map<String, Boolean> buildConfig(String... options) {
        Map<String, Boolean> config = new HashMap<String, Boolean>();

        if (options != null) {
            for (String option : options) {
                config.put(option, true);
            }
        }

        return config;
    }

    protected String performGetRequest(URL url) throws Exception {

        System.out.println("Request url " + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        conn.setRequestProperty("Content-type", "application/json");

        String response = null;
        if (conn.getResponseCode() == 200) {
            response = getGoodResponse(conn);
        } else {
            processError(conn);
        }

        return response;
    }

    private String getGoodResponse(HttpURLConnection conn) throws IOException, SAXException {
        InputStreamReader read = new InputStreamReader(conn.getInputStream());
        // note that Content-Length is available at this point
        StringBuilder sb = new StringBuilder();
        int ch = read.read();
        while (ch != -1) {
            sb.append((char) ch);
            ch = read.read();
        }
        String response = sb.toString();
        read.close();
        System.out.println("Response");
        System.out.println(response);
        return response;
    }

    private void processError(HttpURLConnection conn) throws IOException, SAXException {
        System.out.println("Error code " + conn.getResponseCode());
        InputStreamReader read = new InputStreamReader(conn.getErrorStream());
        // note that Content-Length is available at this point
        StringBuilder sb = new StringBuilder();
        int ch = read.read();
        while (ch != -1) {
            sb.append((char) ch);
            ch = read.read();
        }
        String response = sb.toString();
        read.close();

        System.out.println(response);

    }
}
