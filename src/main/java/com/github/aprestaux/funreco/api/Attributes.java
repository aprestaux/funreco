package com.github.aprestaux.funreco.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Wrapper class that allow us to bind from json with spring mvc and jackson
 */
public class Attributes extends HashMap<String, List<String>> {
    public void put(String key, String... values) {
        super.put(key, Arrays.asList(values));
    }
}
