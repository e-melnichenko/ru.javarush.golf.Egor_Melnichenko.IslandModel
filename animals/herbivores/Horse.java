package animals.herbivores;

import island.Direction;
import island.Herbivour;
import island.Island;
import island.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Horse extends Herbivour {
    int speed = 1;
    @Override
    public void move(int[] from, Island island) {
//        todo getter
        Location currentLocation = island.area[from[0]][ from[1]];

        for (int i = 0; i < speed; i++) {
//            todo random direction
            Direction[] directions = currentLocation.availableDirections.directionList;
            int randomIndex = ThreadLocalRandom.current().nextInt(directions.length);
            int[] delta = directions[randomIndex].getDelta();
//            todo getter
            Location newLocation = island.area[from[0] + delta[0]][from[1] + delta[1]];

//            todo moved mark
            newLocation.herbivourList.add(this);

//            currentLocation.herbivourList.remove(this);
//            System.out.println("directions: " + from[0] + ":" + from[1] + currentLocation.availableDirections);
        }
    }
}
