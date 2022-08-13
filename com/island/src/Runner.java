package com.island.src;

import com.island.src.entities.Game;
import com.island.src.island.Island;
import com.island.src.entities.settings.Initializer;

public class Runner {
    public static void main(String[] args) {
        Initializer initializer = new Initializer();
        Island island = initializer.createIsland();
        Game game = new Game(island);


        island.print();
        island.testStart();
    }
}
