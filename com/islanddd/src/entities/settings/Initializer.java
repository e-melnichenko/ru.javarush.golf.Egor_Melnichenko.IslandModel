package com.islanddd.src.entities.settings;

import com.islanddd.src.entities.island.Coords;
import com.islanddd.src.entities.island.Island;
import com.islanddd.src.entities.island.Location;

public class Initializer {
    public Island createIsland() {
        int width = Settings.get().getIslandWidth();
        int height = Settings.get().getIslandHeight();

        Island island = new Island(width, height);
        generate(island);

        return island;
    }

    public void generate(Island island) {
        Location[][] area = island.getArea();

        for (int y = 0; y < Settings.get().getIslandHeight(); y++) {
            for (int x = 0; x < Settings.get().getIslandWidth(); x++) {
                area[x][y] = new Location(new Coords(x, y));
            }
        }
    }
}
