package com.islanddd.src.entities;

import com.islanddd.src.entities.animal.AnimalBase;
import com.islanddd.src.entities.island.Island;
import com.islanddd.src.entities.settings.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Game {
    private final Island island;

    public Game(Island island) {
        this.island = island;
    }

    public void printStats() {
        print();
//        testPrint();
    }

    private void print() {
        Supplier<HashMap<AnimalBase, Integer>> supplier =
                HashMap::new;

        BiConsumer<HashMap<AnimalBase, Integer>, AnimalBase> accumulator =
                (animalKindToInteger, item) -> {
                    animalKindToInteger.merge(item, 1, Integer::sum);
                };
        BiConsumer<HashMap<AnimalBase, Integer>, HashMap<AnimalBase, Integer>> combiner =
                HashMap::putAll;

        AtomicReference<Float> vegetationCount = new AtomicReference<>((float) 0);

        HashMap<AnimalBase, Integer> statsMap = island.locationsStream()
                .flatMap(location -> {
                    vegetationCount.updateAndGet(v -> v + location.vegetation.value);
                    return location.animalsMap.values().stream();
                })
                .flatMap(ArrayList::stream)
                .map(animal -> animal.base)
                .collect(supplier, accumulator, combiner);

        System.out.println("\uD83C\uDF3F: " + vegetationCount);
        statsMap.forEach((key, value) -> System.out.print(key.icon + key.kind + ": " + value + "/" + key.maxOnLocation * Settings.get().getIslandWidth() * Settings.get().getIslandHeight() + " "));
        System.out.println();
    }
    private void testPrint() {
        for (int y = Settings.get().getIslandHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < Settings.get().getIslandWidth(); x++) {
                System.out.print(island.getArea()[x][y]);
                if (x == Settings.get().getIslandWidth() - 1) {
                    System.out.println();
                }
            }
        }

        System.out.println("----------------------------");
    }

    public Island getIsland() {
        return island;
    }
}
