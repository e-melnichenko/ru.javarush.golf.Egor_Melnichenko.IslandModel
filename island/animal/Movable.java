package island.animal;

import island.island.Island;
import island.island.Location;

public interface Movable {
    Location move(Location fromLocation, Island island);
    void resetMove();
}