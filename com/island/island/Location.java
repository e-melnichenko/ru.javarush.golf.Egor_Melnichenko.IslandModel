package com.island.island;

import com.island.animal.*;
import com.island.animal.herbivore.Herbivore;
import com.island.animal.herbivore.Horse;
import com.island.animal.predator.Predator;
import com.island.animal.predator.Wolf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Location {
    public final Coords coords;
    public final HashMap<AnimalKind, ArrayList<Herbivore>> herbivoresMap = new HashMap<>();
    public final HashMap<AnimalKind, ArrayList<Predator>> predatorsMap = new HashMap<>();
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
//            for dev
//            todo method with generics or single map for animals ???
            if(value.animalClass == AnimalClass.HERBIVORE) {
                ArrayList<Herbivore> list = new ArrayList<>();
//                if(animalId == 1) {
                    list.add(new Horse(animalId, AnimalBase.Horse));
//                }
                herbivoresMap.put(value.kind, list);
            } else {
                ArrayList<Predator> list = new ArrayList<>();
//                if(animalId == 1) {
                    list.add(new Wolf(animalId, AnimalBase.Wolf));
//                }
                predatorsMap.put(value.kind, list);
            }
        }
    }

    public boolean hasFreeSpace(Animal animal) {
        AnimalBase animalBase = animal.base;
        ArrayList<? extends Animal> list = animalBase.animalClass == AnimalClass.HERBIVORE ?
                herbivoresMap.get(animalBase.kind) : predatorsMap.get(animalBase.kind);

        return list.size() < animalBase.maxOnLocation || list.contains(animal);
    }

    public DirectionBunch getAvailableDirections() {
        return availableDirections;
    }
    public Stream<Herbivore> herbivoreStream() {
        return  herbivoresMap.values().stream().flatMap(List::stream);
    }
    public Stream<Predator> predatorStream() {
        return  predatorsMap.values().stream().flatMap(List::stream);
    }
    public Stream<Animal> animalsStream() {
        return Stream.concat(
                herbivoreStream(),
                predatorStream()
        );
    }

    public Stream<ArrayList<? extends Animal>> animalListByKindStream() {
        return Stream.concat(
            herbivoresMap.values().stream(),
            predatorsMap.values().stream()
        );
    }

    public void addAnimal(Animal animal) {
        AnimalKind animalKind = animal.base.kind;

        if(animal instanceof Herbivore) {
            if(herbivoresMap.get(animalKind).contains(animal)) {
                throw new AnimalAlreadyExistException();
            }

            herbivoresMap.get(animalKind).add((Herbivore) animal);
        }

        if(animal instanceof Predator) {
            if(predatorsMap.get(animalKind).contains(animal)) {
                throw new AnimalAlreadyExistException();
            }

            predatorsMap.get(animalKind).add((Predator) animal);
        }
    }

    public void startHunting() {
        animalsStream().forEach(animal -> {
            if(animal instanceof Predator) {
                ((Predator) animal).hunt(this);
            }
//            todo herbivore eat plant
        });
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        animalsStream().forEach(animal -> stringBuilder.append(animal).append(" "));
        return stringBuilder.toString();
    }
}
