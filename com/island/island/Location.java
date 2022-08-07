package com.island.island;

import com.island.Chance;
import com.island.IdGenerator;
import com.island.animal.*;
import com.island.animal.herbivore.Herbivore;
import com.island.animal.predator.Boa;
import com.island.animal.predator.Predator;
import com.island.animal.predator.Wolf;
import com.island.vegetation.Vegetation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Location {
    public final Coords coords;
    public final HashMap<AnimalKind, ArrayList<Herbivore>> herbivoresMap = new HashMap<>();
    public final HashMap<AnimalKind, ArrayList<Predator>> predatorsMap = new HashMap<>();
    private final DirectionBunch availableDirections;
    private final Vegetation vegetation;

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

        vegetation = new Vegetation();

//        todo random filling (after all features)
        for (AnimalBase value : AnimalBase.values()) {
//            for dev
//            todo method with generics or single map for animals ???
            if (value.animalClass == AnimalClass.HERBIVORE) {
                ArrayList<Herbivore> list = new ArrayList<>();
//                if(animalId < 4) {
//                    list.add(new Horse(animalId, AnimalBase.Horse));
//                }
                herbivoresMap.put(value.kind, list);
            } else {
                ArrayList<Predator> list = new ArrayList<>();
//                    System.out.println("animalid: " + animalId);
                if(animalId == 1) {
                    if(value == AnimalBase.Boa) {
                        list.add(new Boa(animalId, value));
                    }
                    if(value == AnimalBase.Wolf) {
                        list.add(new Wolf(animalId, AnimalBase.Wolf));
                    }
                }
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

    public ArrayList<? extends Animal> getAnimalListByKind(AnimalKind kind) {
        ArrayList<? extends Animal> animalList = herbivoresMap.get(kind);
        if (animalList == null) {
            animalList = predatorsMap.get(kind);
        }
        if (animalList == null) {
            throw new IllegalArgumentException("Вид " + kind + " не может существовать в локации");
        }

        return animalList;
    }

    public Stream<Herbivore> herbivoreStream() {
        return herbivoresMap.values().stream().flatMap(List::stream);
    }

    public Stream<Predator> predatorStream() {
        return predatorsMap.values().stream().flatMap(List::stream);
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

        if (animal instanceof Herbivore) {
            if (herbivoresMap.get(animalKind).contains(animal)) {
                throw new AnimalAlreadyExistException();
            }

            herbivoresMap.get(animalKind).add((Herbivore) animal);
        }

        if (animal instanceof Predator) {
            if (predatorsMap.get(animalKind).contains(animal)) {
                throw new AnimalAlreadyExistException();
            }

            predatorsMap.get(animalKind).add((Predator) animal);
        }
    }

    public void startHunting() {
//        todo CanHunt for herbivores
        animalsStream().forEach(animal -> {
            if (animal instanceof CanHunt && !animal.isDead) {
                ((Predator) animal).hunt(this);
            }
        });
    }

    public void vegetationGrow() {
        vegetation.grow();
    }

    public void feedHerbivores() {
        herbivoreStream().forEach(herbivore -> vegetation.feed(herbivore));
    }

    public void clear() {
        Consumer<ArrayList<? extends Animal>> consumer = animals -> animals.removeIf(
                animal -> animal.isDead || animal.satiety < animal.base.wastedSatietyPerStep
        );

        herbivoresMap.values().forEach(consumer);
        predatorsMap.values().forEach(consumer);
    }

    public void reproduction() {
//        todo remove duplicate. Generics problem
        herbivoresMap.values().forEach(list -> {
            if (list.size() < 2) return;

            int childrenCount = list.size() / 2;
            Herbivore animalExample = list.get(0);
            for (int i = 0; i < childrenCount; i++) {
                if(Chance.isSuccess(animalExample.base.reproductionChance)) continue;
                Herbivore newHerbivore = tryToCreateNewAnimal(animalExample);
                System.out.println("was born: " + newHerbivore);
                list.add(newHerbivore);
            }
        });

        predatorsMap.values().forEach(list -> {
            if (list.size() < 2) return;

            int childrenCount = list.size() / 2;
            Predator animalExample = list.get(0);
            for (int i = 0; i < childrenCount; i++) {
                if(Chance.isSuccess(animalExample.base.reproductionChance)) continue;
                Predator newPredator = tryToCreateNewAnimal(animalExample);
                System.out.println("was born: " + newPredator);
                list.add(newPredator);
            }
        });
    }

    public <T> T tryToCreateNewAnimal(T animalExample) {
        try {
            Class clazz = animalExample.getClass();
            AnimalBase base = (AnimalBase) clazz.getField("base").get(animalExample);
            return (T) clazz.getConstructor(int.class, AnimalBase.class).newInstance(IdGenerator.get(), base);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 NoSuchFieldException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(vegetation).append(" ");
        animalsStream().forEach(animal -> stringBuilder.append(animal).append(" "));
        return stringBuilder.toString();
    }
}
