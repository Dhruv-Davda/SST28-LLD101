package com.example.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapDataSource {

    private static final String[] SHAPES = {"PIN", "CIRCLE", "SQUARE"};
    private static final String[] COLORS = {"RED", "BLUE", "GREEN", "ORANGE"};
    private static final int[] SIZES = {10, 12, 14, 16};

    private final MarkerStyleFactory factory = new MarkerStyleFactory();

    public List<MapMarker> loadMarkers(int count) {
        Random rand = new Random(7);
        List<MapMarker> markers = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            double lat = 12.9000 + rand.nextDouble() * 0.2000;
            double lng = 77.5000 + rand.nextDouble() * 0.2000;
            String label = "M-" + i;

            String shape = SHAPES[rand.nextInt(SHAPES.length)];
            String color = COLORS[rand.nextInt(COLORS.length)];
            int size = SIZES[rand.nextInt(SIZES.length)];
            boolean filled = rand.nextBoolean();

            MarkerStyle style = factory.get(shape, color, size, filled);

            markers.add(new MapMarker(lat, lng, label, style));
        }

        System.out.println("[factory] unique styles cached: " + factory.cacheSize());
        return markers;
    }
}
