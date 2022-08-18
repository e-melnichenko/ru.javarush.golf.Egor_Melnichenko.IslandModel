package com.islandModel.src.entities.island;

import com.islandModel.src.entities.animal.Animal;
import com.islandModel.src.entities.animal.AnimalBase;
import com.islandModel.src.entities.animal.AnimalKind;
import com.islandModel.src.entities.animal.herbivore.*;
import com.islandModel.src.entities.animal.omnivore.Boar;
import com.islandModel.src.entities.animal.omnivore.Duck;
import com.islandModel.src.entities.animal.omnivore.Mouse;
import com.islandModel.src.entities.animal.predator.*;
import com.islandModel.src.entities.settings.Settings;
import com.islandModel.src.entities.vegetation.Vegetation;
import com.islandModel.src.util.Randomizer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class Location {
    public final Coords coords;
    public final HashMap<AnimalKind, ArrayList<Animal>> animalsMap = new HashMap<>();
    private final DirectionBunch availableDirections;
    public final Vegetation vegetation;
    private final Lock lock = new ReentrantLock(true);
    private static final Animal[] ANIMAL_GENERATION_LIST = new Animal[]{
        new Buffalo(), new Caterpillar(), new Deer(), new Goat(), new Horse(), new Rabbit(), new Sheep(),
        new Boar(), new Duck(), new Mouse(),
        new Bear(), new Boa(), new Eagle(), new Fox(), new Wolf()
    };

    public Location(Coords coords) {
        this.coords = coords;

        int x = coords.x;
        int y = coords.y;

        int islandMaxX = Settings.get().getIslandWidth() -1;
        int islandMaxY = Settings.get().getIslandHeight() -1;

        if (x == 0 && y == 0) {
            availableDirections = DirectionBunch.BOTTOM_LEFT_EDGE;
        } else if (x == islandMaxX && y == 0) {
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

        vegetation = new Vegetation();
        for (Animal animal : ANIMAL_GENERATION_LIST) {
            int animalsCount = Randomizer.RANDOM.nextInt(animal.base.maxOnLocation);
            ArrayList<Animal> animalList = new ArrayList<>();

            for (int i = 0; i < animalsCount; i++) {
                animalList.add(tryToCreateNewAnimal(animal));
            }

            animalsMap.put(animal.base.kind, animalList);
        }
    }

    public void forEachAnimal(Consumer<Animal> consumer) {
        animalsMap.values().stream().flatMap(ArrayList::stream).forEach(consumer);
    }

    public Lock getLock() {
        return lock;
    }

    public void vegetationGrow() {
        lock.lock();
        try {
            vegetation.grow();
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        lock.lock();

        try {
            animalsMap.values().forEach(list -> {
                list.forEach(animal -> {
                    animal.isChild = false;
                    animal.moved = false;
                });

                list.removeIf(animal -> {
                    return animal.isDead || animal.satiety < animal.base.getWastedSatietyPerStep();
                });
            });
        } finally {
            lock.unlock();
        }
    }

    public Animal tryToCreateNewAnimal(Animal animalExample) {
        try {
            Class clazz = animalExample.getClass();
            return (Animal) clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasFreeSpace(Animal animal) {
        AnimalBase base = animal.base;
        ArrayList<Animal> list = animalsMap.get(base.kind);

        return list.size() < base.maxOnLocation;
    }

    public DirectionBunch getAvailableDirections() {
        return availableDirections;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
//                coords
                .append("\u001B[31m")
                .append(coords.x)
                .append(":")
                .append(coords.y)
                .append(" \033[0m")
//                vegetation
                .append(vegetation)
                .append(" ");
//                  animals
        animalsMap.values().stream()
                .flatMap(ArrayList::stream)
                .forEach(animal -> stringBuilder.append(animal).append(" "));

        return stringBuilder.toString();
    }
}
