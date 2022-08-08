package island.island;

import island.Chance;
import island.animal.*;
import island.animal.herbivore.Caterpillar;
import island.animal.omnivore.Mouse;
import island.vegetation.Vegetation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class Location {
    public final Coords coords;
    public final HashMap<AnimalKind, ArrayList<Animal>> animalsMap = new HashMap<>();
    private final DirectionBunch availableDirections;
    public final Vegetation vegetation;
//    todo to config
    private static final Class[] ANIMAL_GENERATION_LIST = new Class[]{
            Caterpillar.class, Mouse.class
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
// todo generation
        for (Class animal : ANIMAL_GENERATION_LIST) {
            AnimalBase base = tryToCreateNewAnimal(animal).base;
            int animalsCount = Chance.RANDOM.nextInt(base.maxOnLocation);
//            System.out.println("animals count: " + animalsCount);
            ArrayList<Animal> animalList = new ArrayList<>();

            for (int i = 0; i < animalsCount; i++) {
//                System.out.println("loop");
                animalList.add(tryToCreateNewAnimal(animal));
            }

//            System.out.println("animal list" + animalList);

            animalsMap.put(base.kind, animalList);
        }
    }

    public void moveAnimals() {
        animalsMap.entrySet().forEach(entry -> {
            ArrayList<Animal> newAnimalList = new ArrayList<>();

            entry.getValue().forEach(animal -> {
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
                if(!hasFreeSpace(animalExample)) {
//                    System.out.println("no reproduction");
                    break;
                };
                if (!Chance.isSuccess(animalExample.base.reproductionChance)) continue;

                Animal newAnimal = tryToCreateNewAnimal(animalExample);
//                System.out.println("was born: " + newAnimal);
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

    public Animal tryToCreateNewAnimal(Class animalClass) {
        try {
            return (Animal) animalClass.getConstructor().newInstance();
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
