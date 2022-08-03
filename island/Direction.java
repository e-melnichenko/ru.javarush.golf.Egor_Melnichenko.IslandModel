package island;

public enum Direction {
    LEFT(0, -1),
    TOP(-1, 0),
    RIGHT(0, 1),
    BOTTOM(1, 0);
    private final int[] delta;
    Direction(int x, int y) {
        delta = new int[]{x, y};
    }

    public int[] getDelta() {
        return delta;
    }
}
