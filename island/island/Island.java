package island.island;

import island.animal.Animal;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Island {
//    todo move to config
    public static final int WIDTH = 3;
    public static final int HEIGHT = 4;
    public static final Area AREA = new Area(WIDTH, HEIGHT);

    public Island() {}

//    public Location getLocation(Coords coords) {
//        return area[coords.x][coords.y];
//    }

//    public Stream<Location> locationStream() {
//        return Arrays.stream(area).flatMap(Arrays::stream);
//    }

    public void print() {
        for (int y = AREA.height - 1; y >= 0; y--) {
            for (int x = 0; x < AREA.width; x++) {
                System.out.print(AREA.getLocation(new Coords(x, y)));
//                System.out.print("\u001B[31m" + x + ":" + y + " " + "\033[0m" + area[x][y] + " ");
                if (x == AREA.width - 1) {
                    System.out.println();
                }
            }
        }

        System.out.println("----------------------------");
    }

    public void start() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        Runnable task = () -> {
            moveAnimals();
            print();
        };

        scheduledExecutorService.scheduleWithFixedDelay(task, 0, 2, TimeUnit.SECONDS);
    }

    public void testStart() {
        while (true) {
            try {
//                todo God class or Runner or Runnable task ???
                moveAnimals();
                System.out.println("after move");
                print();

                reproduction();
                System.out.println("after reproduction");
                print();

                growVegetation();
//                System.out.println("after vegetation grow");
//                print();

                feed();
//                System.out.println("after feed");
//                print();

                clear();
//                System.out.println("after clear");
//                print();

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    private void generate() {
//        for (int y = 0; y < area.length; y++) {
//            for (int x = 0; x < area[y].length; x++) {
//                area[x][y] = new Location(new Coords(x, y));
//            }
//        }
//    }

    private void moveAnimals() {
//        todo code smell
        AREA.locationStream().forEach(location -> {
            location.animalsMap.entrySet().forEach(entry -> {
                ArrayList<Animal> newAnimalList = new ArrayList<>();

                entry.getValue().forEach(animal -> {
                    Location newLocation = animal.move(location, this);
//                    compare by link is okay
                    if(newLocation == location) {
                        newAnimalList.add(animal);
                    } else {
                        newLocation.addAnimal(animal);
                    }
                    animal.moved = true;
                });

                entry.setValue(newAnimalList);
            });
        });
// todo method reset
        AREA.locationStream()
                .flatMap(Location::animalsStream)
                .forEach(Animal::resetMove);
    }

    private void growVegetation() {
        AREA.locationStream().forEach(Location::vegetationGrow);
    }

    private void clear() {
        AREA.locationStream().forEach(Location::clear);
    }
    private void reproduction() {
        AREA.locationStream().forEach(Location::reproduction);
    }

    private void feed() {
        AREA.locationStream().forEach(Location::feed);
    }
}
