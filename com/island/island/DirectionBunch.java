package com.island.island;

public enum DirectionBunch {
    TOP_LEFT_EDGE(Direction.RIGHT, Direction.BOTTOM),
    TOP_RIGHT_EDGE(Direction.LEFT, Direction.BOTTOM),
    TOP_EDGE(Direction.RIGHT, Direction.BOTTOM, Direction.LEFT),
    BOTTOM_LEFT_EDGE(Direction.TOP, Direction.RIGHT),
    BOTTOM_RIGHT_EDGE(Direction.TOP, Direction.LEFT),
    BOTTOM_EDGE(Direction.LEFT, Direction.RIGHT, Direction.TOP),
    LEFT_EDGE(Direction.RIGHT, Direction.BOTTOM, Direction.TOP),
    RIGHT_EDGE(Direction.LEFT, Direction.BOTTOM, Direction.TOP),
    ALL(Direction.LEFT, Direction.RIGHT, Direction.BOTTOM, Direction.TOP);

    public final Direction[] directionList;

    DirectionBunch(Direction... directionList) {
        this.directionList = directionList;
    }
}
