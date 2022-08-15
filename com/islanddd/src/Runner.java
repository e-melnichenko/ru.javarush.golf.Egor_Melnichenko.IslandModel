package com.islanddd.src;

import com.islanddd.src.entities.Game;
import com.islanddd.src.entities.island.Island;
import com.islanddd.src.entities.settings.Initializer;
import com.islanddd.src.services.GameWorker;

public class Runner {
    public static void main(String[] args) {
        Initializer initializer = new Initializer();
        Island island = initializer.createIsland();
        Game game = new Game(island);
        GameWorker gameWorker = new GameWorker(game);
        gameWorker.start();
    }
}
