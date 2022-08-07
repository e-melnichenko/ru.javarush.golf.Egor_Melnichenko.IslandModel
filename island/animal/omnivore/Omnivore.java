package island.animal.omnivore;

import island.animal.Animal;
import island.animal.AnimalBase;
import island.animal.CanEatVegetation;
import island.animal.CanHunt;
import island.island.Location;

public abstract class Omnivore extends Animal implements CanHunt, CanEatVegetation {
    public Omnivore(AnimalBase base) {
        super(base);
    }

    @Override
    public void feed(Location location) {
        eatVegetation(location, this);

        if (!this.isHungry()) return;

        hunt(location, this);
    }

    @Override
    public void hunt(Location location, Animal hunter) {
        CanHunt.super.hunt(location, hunter);
    }

    @Override
    public void eatVegetation(Location location, Animal herbivore) {
        CanEatVegetation.super.eatVegetation(location, herbivore);
    }
}