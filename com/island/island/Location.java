package com.island.island;


import com.island.animals.Animal;
import com.island.animals.AnimalBase;
import com.island.animals.Herbivore;
import com.island.animals.herbivores.Horse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class Location {
    public final Coords coords;
    public final HashMap<String, ArrayList<Herbivore>> herbivoresMap = new HashMap<>();
    private final DirectionBunch availableDirections;

    public Location(Coords coords, int animalId) {
        this.coords = coords;

        int x = coords.x;
        int y = coords.y;

        int islandMaxX = Island.WIDTH - 1;
        int islandMaxY = Island.HEIGHT - 1;

        if (x == 0 && y == 0) {
            availableDirections = DirectionBunch.BOTTOM_LEFT_EDGE;
        } else if (x == islandMaxY && y == 0) {
            availableDirections = DirectionBunch.BOTTOM_RIGHT_EDGE;
        } else if (y == 0) {
            availableDirections = DirectionBunch.BOTTOM_EDGE;
        } else if (x == 0 && y == islandMaxY) {
            availableDirections = DirectionBunch.TOP_LEFT_EDGE;
        } else if (x == islandMaxX && y == islandMaxY) {
            availableDirections = DirectionBunch.TOP_RIGHT_EDGE;
        } else if (y == islandMaxY) {
            availableDirections = DirectionBunch.TOP_EDGE;
        } else if (x == 0) {
            availableDirections = DirectionBunch.LEFT_EDGE;
        } else if (x == islandMaxX) {
            availableDirections = DirectionBunch.RIGHT_EDGE;
        } else {
            availableDirections = DirectionBunch.ALL;
        }

//        todo random filling after all features
        for (AnimalBase value : AnimalBase.values()) {
            ArrayList<Herbivore> list = new ArrayList<>();

            if (animalId < 4) {
                list.add(new Horse(animalId, AnimalBase.Horse));
            }

            herbivoresMap.put(value.kind, list);
        }
    }

    public boolean hasFreeSpace(Animal animal) {
        ArrayList<? extends Animal> list = herbivoresMap.get(animal.base.kind);

        return list.size() < animal.base.maxOnLocation || list.contains(animal);
    }

    public DirectionBunch getAvailableDirections() {
        return availableDirections;
    }

    public void forEachHerbivoreKindList(Consumer<ArrayList<Herbivore>> consumer) {
//        maybe stream ?
        herbivoresMap.forEach((key, value) -> consumer.accept(value));

    }

    public void forEachHerbivore(Consumer<Herbivore> consumer) {
        forEachHerbivoreKindList(list -> list.forEach(consumer));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        forEachHerbivore(herbivore -> stringBuilder.append(herbivore).append(" "));
        return stringBuilder.toString();
    }
}
