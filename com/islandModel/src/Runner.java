package com.islandModel.src;

import com.islandModel.src.entities.Game;
import com.islandModel.src.entities.island.Island;
import com.islandModel.src.entities.settings.Initializer;
import com.islandModel.src.services.GameWorker;

public class Runner {
    public static void main(String[] args) {
        Initializer initializer = new Initializer();
        Island island = initializer.createIsland();
        Game game = new Game(island);
        GameWorker gameWorker = new GameWorker(game);
        gameWorker.start();
    }
}
