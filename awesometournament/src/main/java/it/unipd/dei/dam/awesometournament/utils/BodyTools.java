package it.unipd.dei.dam.awesometournament.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Provides utility methods for handling HTTP request bodies.
 */
public class BodyTools {
    /**
     * Parses the POST body from a BufferedReader into a map of form data.
     *
     * @param reader The BufferedReader containing the POST body.
     * @return A map of form data extracted from the POST body.
     * @throws Exception If an error occurs while parsing the POST body.
     */
    public static Map<String, String> parsePostBody(BufferedReader reader) throws Exception{
        String line = reader.readLine();

        Map<String, String> formData = new HashMap<>();
        String[] pairs = line.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                try {
                    String key = URLDecoder.decode(keyValue[0], "UTF-8");
                    String value = URLDecoder.decode(keyValue[1], "UTF-8");
                    formData.put(key, value);
                } catch (UnsupportedEncodingException e) {
                    throw new Exception("Malformed body");
                }
            }
        }
        return formData;
    }

    /**
     * Reads the request body from an HttpServletRequest.
     *
     * @param req The HttpServletRequest containing the request body.
     * @return The request body as a string.
     * @throws IOException If an I/O error occurs while reading the request body.
     */
    public static String getRequestBody(HttpServletRequest req) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }
}
