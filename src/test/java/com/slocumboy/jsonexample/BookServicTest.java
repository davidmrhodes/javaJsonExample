package com.slocumboy.jsonexample;

import javafx.util.Pair;
import org.junit.Test;

import javax.json.*;
import javax.json.stream.JsonParsingException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by davidrho on 4/2/17.
 */
public class BookServicTest extends JsonRestApiTest {

    @Test
    public void testBooks() throws Exception {
        final String host = "localhost";
        final int bookRestApiPort = 8080;
        final String booksPath = "/books";

        URL booksUrl = new URL("http", host, bookRestApiPort, booksPath);

        Pair<Integer, String> response = performGetRequest(booksUrl);

        assertEquals(200, response.getKey().intValue());

        JsonArray jsonArray = getJsonArrayFromString(response.getValue());

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

}
