package com.island.island;

public class Coords {
    public final int x;
    public final int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coords evaluate(CoordsDelta delta) {
        return new Coords(x + delta.x, y + delta.y);
    }
}
