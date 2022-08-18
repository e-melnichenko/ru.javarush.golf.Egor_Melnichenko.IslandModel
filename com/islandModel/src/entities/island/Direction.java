package com.islandModel.src.entities.island;

public enum Direction {
    LEFT(-1, 0),
    TOP(0, 1),
    RIGHT(1, 0),
    BOTTOM(0, -1);
    private final Coords delta;
    Direction(int x, int y) {
        delta = new Coords(x, y);
    }

    public Coords getDelta() {
        return delta;
    }
}
