package com.island.src.entities.settings;

import com.island.src.island.Island;

public class Initializer {
    public Island createIsland() {
        int width = Settings.get().getIslandWidth();
        int height = Settings.get().getIslandHeight();

        return new Island(width, height);
    }
}
