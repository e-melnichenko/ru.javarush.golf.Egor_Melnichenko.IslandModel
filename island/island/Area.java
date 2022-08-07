package island.island;


import java.util.Arrays;
import java.util.stream.Stream;

public class Area {
    private final Location[][] value;
    public final int width;
    public final int height;

    public Area(int width, int height) {
        this.width = width;
        this.height = height;
        this.value = new Location[width][height];
        generate();
    }

    public Location getLocation(Coords coords) {
        return value[coords.x][coords.y];
    }

    public Stream<Location> locationStream() {
        return Arrays.stream(value).flatMap(Arrays::stream);
    }

    private void generate() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                value[x][y] = new Location(new Coords(x, y));
            }
        }
    }
}
