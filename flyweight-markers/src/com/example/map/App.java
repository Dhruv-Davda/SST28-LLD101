package com.example.map;

import java.util.List;

public class App {

    public static void main(String[] args) {
        int n = 30_000;

        MapDataSource source = new MapDataSource();
        List<MapMarker> markers = source.loadMarkers(n);

        new MapRenderer().render(markers);

        System.out.println();
        System.out.println("Run QuickCheck to verify Flyweight sharing:");
        System.out.println("  java com.example.map.QuickCheck");
    }
}
