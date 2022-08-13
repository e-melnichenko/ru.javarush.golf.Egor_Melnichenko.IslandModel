package com.island.src.entities.animal.predator;

import com.island.src.entities.animal.Animal;
import com.island.src.entities.animal.AnimalBase;
import com.island.src.entities.animal.CanHunt;
import com.island.src.island.Location;

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
