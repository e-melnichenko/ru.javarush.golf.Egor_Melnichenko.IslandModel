package com.island.animal.herbivore;

import com.island.animal.Animal;
import com.island.animal.AnimalBase;

public abstract class Herbivore extends Animal {
    public void eat(float vegetationPoints) {
        satiety += vegetationPoints;
    }

    public Herbivore(int id, AnimalBase base) {
        super(id, base);
    }
}
