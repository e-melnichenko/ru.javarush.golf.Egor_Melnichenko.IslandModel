package com.islandModel.src.entities.animal;

import com.islandModel.src.entities.island.Location;

public interface Movable {
    void move(Location fromLocation, Location[][] area);
}
