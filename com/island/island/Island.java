package com.island.island;

import com.island.animal.Animal;
import com.island.animal.AnimalAlreadyExistException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Island {
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;
    public static final int MAX_CHANCE_BOUND = 101;
    private final Location[][] area;
    private int animalsCount = 0;

    public Island() {
        area = new Location[WIDTH][HEIGHT];
        generate();
    }

    public int getAnimalId() {
        return ++animalsCount;
    }

    public Location getLocation(Coords coords) {
        return area[coords.x][coords.y];
    }

    public Stream<Location> locationStream() {
        return Arrays.stream(area).flatMap(Arrays::stream);
    }

    public void print() {
        for (int y = area.length - 1; y >= 0; y--) {
            for (int x = 0; x < area[y].length; x++) {
                System.out.print("\u001B[31m" + x + ":" + y + " " + "\033[0m" + area[x][y] + " ");
                if (x == WIDTH - 1) {
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

                growVegetation();
                System.out.println("after vegetation grow");
                print();

                herbivoresFeed();
                System.out.println("after herbivores feed");
                print();

                hunt();
                System.out.println("after hunting");
                print();

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void generate() {
        for (int y = 0; y < area.length; y++) {
            for (int x = 0; x < area[y].length; x++) {
                area[x][y] = new Location(new Coords(x, y), getAnimalId());
            }
        }
    }

    private void moveAnimals() {
        locationStream().forEach(location -> {
            location.animalListByKindStream().forEach(list -> {
                Iterator<? extends Animal> iterator = list.iterator();

                while (iterator.hasNext()) {
                    Animal animal = iterator.next();
                    if (animal.moved) continue;

                    Location newLocation = animal.move(location, this);

                    try {
                        newLocation.addAnimal(animal);
                        iterator.remove();
                    } catch (AnimalAlreadyExistException e) {
//                        System.out.println(e.getMessage());
                    }

                    animal.moved = true;
                }
            });
        });

// todo method reset
        locationStream()
            .flatMap(Location::animalsStream)
            .forEach(Animal::resetMove);
    }
    private void hunt() {
        locationStream().forEach(Location::startHunting);
    }
    private void growVegetation() {
        locationStream().forEach(Location::vegetationGrow);
    }
    private void herbivoresFeed() {
        locationStream().forEach(Location::feedHerbivores);
    }
}
