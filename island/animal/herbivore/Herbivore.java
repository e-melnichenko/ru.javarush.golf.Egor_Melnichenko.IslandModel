package island.animal.herbivore;

import island.animal.Animal;
import island.animal.AnimalBase;
import island.animal.CanEatVegetation;
import island.island.Location;

public abstract class Herbivore extends Animal implements CanEatVegetation {
    @Override
    public void feed(Location location) {
        eatVegetation(location, this);
    }

    @Override
    public void eatVegetation(Location location, Animal herbivore) {
        CanEatVegetation.super.eatVegetation(location, herbivore);
    }

    public Herbivore(AnimalBase base) {
        super(base);
    }
}
