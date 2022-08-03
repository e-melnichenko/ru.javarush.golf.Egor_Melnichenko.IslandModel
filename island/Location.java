package island;


import animals.herbivores.Horse;

import java.util.ArrayList;
import java.util.List;

public class Location {
    public final List<Herbivour> herbivourList = new ArrayList<>();
    final private int x;
    final private int y;
    final public DirectionBunch availableDirections;
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        int islandWidthFromZero = Island.WIDTH-1;
        int islandHeightFromZero = Island.HEIGHT-1;

//        availableDirections = DirectionBunch.ALL;
//        todo improve - ALL is the first condition
        if(x == 0 && y == 0 ) {
            availableDirections = DirectionBunch.TOP_LEFT_EDGE;
        } else if(x == islandHeightFromZero && y == 0) {
            availableDirections = DirectionBunch.TOP_RIGHT_EDGE;
        } else if(y == 0) {
            availableDirections = DirectionBunch.TOP_EDGE;
        } else if(x == 0 && y == islandHeightFromZero) {
            availableDirections = DirectionBunch.BOTTOM_LEFT_EDGE;
        } else if(x == islandWidthFromZero && y == islandHeightFromZero) {
            availableDirections = DirectionBunch.BOTTOM_RIGHT_EDGE;
        } else if(y == islandHeightFromZero) {
            availableDirections = DirectionBunch.BOTTOM_EDGE;
        } else if(x == 0) {
            availableDirections = DirectionBunch.LEFT_EDGE;
        } else if(x == islandWidthFromZero) {
            availableDirections = DirectionBunch.RIGHT_EDGE;
        } else {
            availableDirections = DirectionBunch.ALL;
        }

//        todo random filling
        herbivourList.add(new Horse());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        herbivourList.forEach(item -> stringBuilder.append(item.getClass().getSimpleName() + " "));
        return stringBuilder.toString();
    }
}
