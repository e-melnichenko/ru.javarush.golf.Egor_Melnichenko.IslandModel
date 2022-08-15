package com.islanddd.src.entities.animal;

import com.islanddd.src.entities.island.Location;

public interface Movable {
    void move(Location fromLocation, Location[][] area);
}
