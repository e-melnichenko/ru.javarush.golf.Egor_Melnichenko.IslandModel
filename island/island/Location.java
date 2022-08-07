package island.island;

import island.Chance;
import island.animal.*;
import island.animal.herbivore.Herbivore;
import island.animal.omnivore.Mouse;
import island.animal.predator.Predator;
import island.vegetation.Vegetation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Location {
    public final Coords coords;
    public final HashMap<AnimalKind, ArrayList<Herbivore>> herbivoresMap = new HashMap<>();
    public final HashMap<AnimalKind, ArrayList<Predator>> predatorsMap = new HashMap<>();
    public final HashMap<AnimalKind, ArrayList<Animal>> animalsMap = new HashMap<>();
    private final DirectionBunch availableDirections;
    public final Vegetation vegetation;

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

//        for dev
        animalsMap.put(AnimalKind.MOUSE, new ArrayList<>() {{
            add(new Mouse());
        }});
        animalsMap.put(AnimalKind.CATERPILLAR, new ArrayList<>(){{
            add(new Caterpillar());
        }});

//        todo random filling (after all features)
//        todo code smell
//        for (AnimalBase value : AnimalBase.values()) {
////            for dev
////            todo method with generics or single map for animals ???
//            if (value.animalClass == AnimalClass.HERBIVORE) {
//                ArrayList<Herbivore> list = new ArrayList<>();
//                if(animalId < 4) {
//                    if(value == AnimalBase.Horse) {
//                        list.add(new Horse(animalId, AnimalBase.Horse));
//                    }
//                }
//                herbivoresMap.put(value.kind, list);
//            } else {
//                ArrayList<Predator> list = new ArrayList<>();
//                if(animalId == 1) {
//                    if(value == AnimalBase.Boa) {
//                        list.add(new Boa(animalId, value));
//                    }
//                    if(value == AnimalBase.Wolf) {
//                        list.add(new Wolf(animalId, AnimalBase.Wolf));
//                    }
//                }
//                predatorsMap.put(value.kind, list);
//            }
//        }
    }

    public boolean hasFreeSpace(Animal animal) {
//        todo remove .contains(animal)
        AnimalBase base = animal.base;
        ArrayList<Animal> list = animalsMap.get(base.kind);

        return list.size() < base.maxOnLocation || list.contains(animal);
//     AnimalBase animalBase = animal.base;
//        ArrayList<? extends Animal> list = animalBase.animalClass == AnimalClass.HERBIVORE ?
//                herbivoresMap.get(animalBase.kind) : predatorsMap.get(animalBase.kind);
//
//        return list.size() < animalBase.maxOnLocation || list.contains(animal);
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

        if (animalsMap.get(animalKind).contains(animal)) {
            throw new AnimalAlreadyExistException();
        }

        animalsMap.get(animalKind).add(animal);

//        AnimalKind animalKind = animal.base.kind;
//
//        if (animal instanceof Herbivore) {
//            if (herbivoresMap.get(animalKind).contains(animal)) {
//                throw new AnimalAlreadyExistException();
//            }
//
//            herbivoresMap.get(animalKind).add((Herbivore) animal);
//        }
//
//        if (animal instanceof Predator) {
//            if (predatorsMap.get(animalKind).contains(animal)) {
//                throw new AnimalAlreadyExistException();
//            }
//
//            predatorsMap.get(animalKind).add((Predator) animal);
//        }
    }

    public void startHunting() {
//        todo CanHunt for herbivores
        animalsStream().forEach(animal -> {
            if (animal instanceof CanHunt && !animal.isDead && animal.isHungry()) {
//                todo
//                ((CanHunt) animal).hunt(this);
            }
        });
    }

    public void vegetationGrow() {
        vegetation.grow();
    }

//    public void feedHerbivore(CanEatVegetation herbivore) {
//        vegetation.feed(herbivore);
//        herbivoreStream().forEach(herbivore -> vegetation.feed(herbivore));
//    }

    public void clear() {
        animalsMap.values().forEach(list -> {
            list.removeIf(animal -> animal.isDead || animal.satiety < animal.base.wastedSatietyPerStep);
        });
    }

    public void reproduction() {
        animalsMap.values().forEach(list -> {
//            todo if not max count on location
            if (list.size() < 2) return;

            int childrenCount = list.size() / 2;
            Animal animalExample = list.get(0);
            for (int i = 0; i < childrenCount; i++) {
                if (!Chance.isSuccess(animalExample.base.reproductionChance)) continue;

                Animal newAnimal = tryToCreateNewAnimal(animalExample);
                System.out.println("was born: " + newAnimal);
                list.add(newAnimal);
            }
        });
//        todo remove duplicate. Generics problem
//        herbivoresMap.values().forEach(list -> {
//            if (list.size() < 2) return;
//
//            int childrenCount = list.size() / 2;
//            Herbivore animalExample = list.get(0);
//            for (int i = 0; i < childrenCount; i++) {
//                if (Chance.isSuccess(animalExample.base.reproductionChance)) continue;
//                Herbivore newHerbivore = tryToCreateNewAnimal(animalExample);
//                System.out.println("was born: " + newHerbivore);
//                list.add(newHerbivore);
//            }
//        });
//
//        predatorsMap.values().forEach(list -> {
//            if (list.size() < 2) return;
//
//            int childrenCount = list.size() / 2;
//            Predator animalExample = list.get(0);
//            for (int i = 0; i < childrenCount; i++) {
//                if (Chance.isSuccess(animalExample.base.reproductionChance)) continue;
//                Predator newPredator = tryToCreateNewAnimal(animalExample);
//                System.out.println("was born: " + newPredator);
//                list.add(newPredator);
//            }
//        });
    }

    public Animal tryToCreateNewAnimal(Animal animalExample) {
        try {
            Class clazz = animalExample.getClass();
            return (Animal) clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void feed() {
        animalsMap.values().stream().flatMap(ArrayList::stream)
            .forEach(animal -> animal.feed(this));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
            .append("\u001B[31m")
            .append(coords.x)
            .append(":")
            .append(coords.y)
            .append(" \033[0m")
//                vegetation
            .append(vegetation)
            .append(" ");

        animalsMap.values().stream().flatMap(ArrayList::stream)
            .forEach(animal -> stringBuilder.append(animal).append(" "));

        return stringBuilder.toString();
    }
}
