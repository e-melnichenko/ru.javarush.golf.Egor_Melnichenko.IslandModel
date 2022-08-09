package island.animal.predator;

import island.animal.Animal;
import island.animal.AnimalBase;
import island.animal.CanHunt;
import island.island.Location;

public class Predator extends Animal implements CanHunt {
    @Override
    public void feed(Location location) {
        hunt(location, this);
    }

    @Override
    public void hunt(Location location, Animal hunter) {
        CanHunt.super.hunt(location, hunter);
    }

    public Predator(AnimalBase base) {
        super(base);
    }
}
