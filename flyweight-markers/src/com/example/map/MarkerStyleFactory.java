package com.example.map;

import java.util.HashMap;
import java.util.Map;

public class MarkerStyleFactory {

    private final Map<String, MarkerStyle> pool = new HashMap<>();

    public MarkerStyle get(String shape, String color, int size, boolean filled) {
        String cacheKey = shape + "|" + color + "|" + size + "|" + (filled ? "F" : "O");
        return pool.computeIfAbsent(cacheKey, k -> new MarkerStyle(shape, color, size, filled));
    }

    public int cacheSize() {
        return pool.size();
    }
}
