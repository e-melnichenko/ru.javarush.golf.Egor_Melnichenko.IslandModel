package com.island.src.creators;

import com.island.src.island.Coords;
import com.island.src.island.Island;
import com.island.src.island.Location;
import com.island.src.entities.settings.Settings;

public class IslandCreator {
    public Island createIsland() {
        Island island = new Island(Settings.get().getIslandWidth(), Settings.get().getIslandHeight());
        generateLocations(island);

        return island;
    }

    private void generateLocations(Island island) {
        Location[][] area = island.getArea();

        for (int y = 0; y < area.length; y++) {
            for (int x = 0; x < area[y].length; x++) {
                area[x][y] = new Location(new Coords(x, y));
            }
        }
    }
}
