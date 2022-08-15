package com.islanddd.src.entities.island;

import java.util.*;
import java.util.stream.Stream;

public class Island {
    private final Location[][] area;

    public Island(int width, int height) {
        area = new Location[width][height];
    }

    public Location[][] getArea() {
        return area;
    }

    public Stream<Location> locationsStream() {
        return Arrays.stream(area).flatMap(Arrays::stream);
    }
}



