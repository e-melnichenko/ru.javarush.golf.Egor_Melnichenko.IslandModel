package com.island.island;

public enum Direction {
    LEFT(-1, 0),
    TOP(0, 1),
    RIGHT(1, 0),
    BOTTOM(0, -1);
    private final CoordsDelta delta;
    Direction(int x, int y) {
        delta = new CoordsDelta(x, y);
    }

    public CoordsDelta getDelta() {
        return delta;
    }
}
