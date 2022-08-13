package com.island.src.entities;

import com.island.src.island.Island;

public class Game {
    private final Island island;

    public Game(Island island) {
        this.island = island;
    }

    public void printStats() {
//        todo realization of method here
        island.print();
    }
}
