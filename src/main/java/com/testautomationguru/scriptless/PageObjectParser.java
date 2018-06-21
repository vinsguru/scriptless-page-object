package com.testautomationguru.scriptless;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageObjectParser {

    public static Map<String, String> parse(String filename) {
        Map<String, String> result = null;
        try {
            result = new ObjectMapper().readValue(new File(filename), LinkedHashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
