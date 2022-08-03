package com.island.animals;

import com.island.island.Coords;
import com.island.island.Island;
import com.island.island.Location;

public interface Movable {
    Location move(Location fromLocation, Island island);
}
