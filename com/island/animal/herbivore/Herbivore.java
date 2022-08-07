package com.island.animal.herbivore;

import com.island.animal.Animal;
import com.island.animal.AnimalBase;
import com.island.animal.CanEatVegetation;
import com.island.island.Location;

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
