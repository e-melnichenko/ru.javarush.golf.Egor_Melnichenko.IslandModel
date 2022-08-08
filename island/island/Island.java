package island.island;

import island.animal.AnimalKind;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;

public class Island {
//    todo move to config
    public static final int WIDTH = 3;
    public static final int HEIGHT = 4;
    public static final Area AREA = new Area(WIDTH, HEIGHT);

    public Island() {
        AREA.generate();
    }

    public void print() {
//        todo print stats
        Supplier<HashMap<AnimalKind, Integer>> supplier =
                () -> new HashMap<>();

        BiConsumer<HashMap<AnimalKind, Integer>, AnimalKind> accumulator =
                (animalKindToInteger, item) -> {
                    animalKindToInteger.merge(item, 1, Integer::sum);
        };
        BiConsumer<HashMap<AnimalKind, Integer>, HashMap<AnimalKind, Integer>> combiner =
                (map1, map2) -> map1.putAll(map2);

        AtomicReference<Float> vegetationCount = new AtomicReference<>((float) 0);

        HashMap<AnimalKind, Integer> statsMap = AREA.locationStream()
                .flatMap(location -> {
                    vegetationCount.updateAndGet(v -> (float) (v + location.vegetation.value));
                    return location.animalsMap.values().stream();
                })
                .flatMap(ArrayList::stream)
                .map(animal -> animal.base.kind)
                .collect(supplier, accumulator, combiner);

        System.out.println("vegetation: " + vegetationCount);
        System.out.println(statsMap);
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
                reproduction();
                feed();
                clear();
                growVegetation();
                print();

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void moveAnimals() {
        forEachLocation(Location::moveAnimals);
    }

    private void growVegetation() {
        forEachLocation(Location::vegetationGrow);
    }

    private void clear() {
        forEachLocation(Location::clear);
    }
    private void reproduction() {
        forEachLocation(Location::reproduction);
    }

    private void feed() {
        forEachLocation(Location::feed);
    }
    private void forEachLocation(Consumer<Location> consumer) {
        AREA.locationStream().forEach(consumer);
    }
}
