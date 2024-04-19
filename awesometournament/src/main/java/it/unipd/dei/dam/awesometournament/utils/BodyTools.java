package it.unipd.dei.dam.awesometournament.utils;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class BodyTools {
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
    
}