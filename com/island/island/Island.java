package com.island.island;

import com.island.animals.Herbivore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Island {
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;
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

    public void forEachLocation(Consumer<Location> func) {
        for (Location[] locations : area) {
            for (Location location : locations) {
                func.accept(location);
            }
        }
    }

    public void print() {
        for (int y = area.length - 1; y >= 0; y--) {
            for (int x = 0; x < area[y].length; x++) {
                System.out.print("\033[0;32m" + x + ":" + y + " " + "\033[0m" + area[x][y] + " ");
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
                moveAnimals();
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
//        todo not only for herbivore
        forEachLocation(location -> {
            location.forEachHerbivoreKindList(list -> {
                Iterator<Herbivore> iterator = list.iterator();

                while (iterator.hasNext()) {
                    Herbivore herbivore = iterator.next();
                    if (herbivore.moved) continue;

                    Location newLocation = herbivore.move(location, this);
                    ArrayList<Herbivore> animalKindList = newLocation.herbivoresMap.get(herbivore.base.kind);

                    if (!animalKindList.contains(herbivore)) {
                        animalKindList.add(herbivore);
                        iterator.remove();
                    } else {
                        System.out.println("start and end equals");
                    }

                    herbivore.moved = true;
                }
            });
        });

        forEachLocation(location -> {
            location.forEachHerbivore(herbivore -> herbivore.moved = false);
        });
    }
}
