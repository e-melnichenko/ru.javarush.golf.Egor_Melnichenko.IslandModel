package com.island.src.entities.animal;

import com.island.src.island.Location;

public interface Movable {
    Location move(Location fromLocation);
    void resetMove();
}
