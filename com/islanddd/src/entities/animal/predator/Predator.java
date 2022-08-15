package com.islanddd.src.entities.animal.predator;

import com.islanddd.src.entities.animal.Animal;
import com.islanddd.src.entities.animal.AnimalBase;
import com.islanddd.src.entities.animal.CanHunt;
import com.islanddd.src.entities.island.Location;

public abstract class Predator extends Animal implements CanHunt {
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
