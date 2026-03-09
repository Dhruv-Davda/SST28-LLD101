package com.example.map;

import java.util.List;

public class MapRenderer {

    public void render(List<MapMarker> markers) {
        System.out.println("Rendering " + markers.size() + " markers...");
        int count = 0;

        for (MapMarker m : markers) {
            if (count < 8) {
                System.out.println(format(m));
                count++;
            }
        }

        if (markers.size() > count) {
            System.out.println("... (" + (markers.size() - count) + " more not shown)");
        }
    }

    private String format(MapMarker marker) {
        return String.format("%s @ (%.4f, %.4f) style=%s",
                marker.getLabel(), marker.getLat(), marker.getLng(), marker.getStyle());
    }
}
