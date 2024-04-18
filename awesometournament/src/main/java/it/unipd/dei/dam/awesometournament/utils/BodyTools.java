package it.unipd.dei.dam.awesometournament.utils;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class BodyTools {
    public static Map<String, String> parsePostBody(BufferedReader reader) throws Exception{
        Map<String, String> map = new HashMap<>();
        String line = reader.readLine();
        if(line == null) {
            throw new Exception("Malformed body");
        }
        String[] params = line.split("&");
        for(String param: params) {
            String[] parts = param.split("=");
            if (parts.length != 2) {
                throw new Exception("Malformed body");
            }
            map.put(parts[0], parts[1]);
        }
        return map;
    }
    
}
