package com.island.animal;

import com.island.island.Island;
import com.island.island.Location;

public interface Movable {
    Location move(Location fromLocation, Island island);
    void resetMove();
}
