package com.island.src.island;

import com.island.src.entities.animal.Animal;
import com.island.src.entities.animal.AnimalAlreadyExistException;
import com.island.src.entities.animal.AnimalBase;
import com.island.src.entities.animal.AnimalKind;
import com.island.src.entities.animal.herbivore.*;
import com.island.src.entities.animal.predator.*;
import com.island.src.entities.vegetation.Vegetation;
import com.island.src.Chance;
import com.island.src.entities.animal.omnivore.Boar;
import com.island.src.entities.animal.omnivore.Duck;
import com.island.src.entities.animal.omnivore.Mouse;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class Location {
    public final Coords coords;
    public final HashMap<AnimalKind, ArrayList<Animal>> animalsMap = new HashMap<>();
    private final DirectionBunch availableDirections;
    public final Vegetation vegetation;
    private static final Animal[] ANIMAL_GENERATION_LIST = new Animal[]{
        new Buffalo(), new Caterpillar(), new Deer(), new Goat(), new Horse(), new Rabbit(), new Sheep(),
        new Boar(), new Duck(), new Mouse(),
        new Bear(), new Boa(), new Eagle(), new Fox(), new Wolf()
    };

    public Location(Coords coords) {
        this.coords = coords;

        int x = coords.x;
        int y = coords.y;

        int islandMaxX = Island.WIDTH - 1;
        int islandMaxY = Island.HEIGHT - 1;

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
            int animalsCount = Chance.RANDOM.nextInt(animal.base.maxOnLocation);
            ArrayList<Animal> animalList = new ArrayList<>();

//            if(animal.base == AnimalBase.BEAR) {
                for (int i = 0; i < animalsCount; i++) {
                    animalList.add(tryToCreateNewAnimal(animal));
                }
//            }

            animalsMap.put(animal.base.kind, animalList);
        }
    }

    public void moveAnimals() {
        animalsMap.entrySet().forEach(entry -> {
            ArrayList<Animal> newAnimalList = new ArrayList<>();

            entry.getValue().forEach(animal -> {
                if(animal.moved) {
                    newAnimalList.add(animal);
                    return;
                }

                Location newLocation = animal.move(this);
//                    compare by link is okay
                if (newLocation == this) {
                    newAnimalList.add(animal);
                } else {
                    newLocation.addAnimal(animal);
                }
                animal.moved = true;
            });

            entry.setValue(newAnimalList);
        });
    }

    public void vegetationGrow() {
        vegetation.grow();
    }

    public void clear() {
        animalsMap.values().forEach(list -> {
            list.removeIf(animal -> animal.isDead || animal.satiety < animal.base.wastedSatietyPerStep);

            list.forEach(Animal::resetMove);
        });
    }

    public void reproduction() {
        animalsMap.values().forEach(list -> {
            if (list.size() < 2) return;

            int childrenCount = list.size() / 2;
            Animal animalExample = list.get(0);
            for (int i = 0; i < childrenCount; i++) {
                if(!hasFreeSpace(animalExample)) break;
                if (!Chance.isSuccess(animalExample.base.reproductionChance)) continue;

                Animal newAnimal = tryToCreateNewAnimal(animalExample);
                list.add(newAnimal);
            }
        });
    }

    public void feed() {
        animalsMap.values().stream()
                .flatMap(ArrayList::stream)
                .forEach(animal -> animal.feed(this));
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

    public void addAnimal(Animal animal) {
        AnimalKind animalKind = animal.base.kind;

        if (animalsMap.get(animalKind).contains(animal)) {
            throw new AnimalAlreadyExistException();
        }

        animalsMap.get(animalKind).add(animal);
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
