package com.islanddd.src.entities.animal.herbivore;

import com.islanddd.src.entities.animal.Animal;
import com.islanddd.src.entities.animal.AnimalBase;
import com.islanddd.src.entities.animal.CanEatVegetation;
import com.islanddd.src.entities.island.Location;

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
