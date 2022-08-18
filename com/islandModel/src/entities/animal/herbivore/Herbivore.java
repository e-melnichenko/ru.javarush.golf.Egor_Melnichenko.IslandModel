package com.islandModel.src.entities.animal.herbivore;

import com.islandModel.src.entities.animal.AnimalBase;
import com.islandModel.src.entities.animal.Animal;
import com.islandModel.src.entities.animal.CanEatVegetation;
import com.islandModel.src.entities.island.Location;

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
