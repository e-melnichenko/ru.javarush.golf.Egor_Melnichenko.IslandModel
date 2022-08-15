package com.islanddd.src.entities.animal.herbivore;

import com.islanddd.src.entities.settings.Settings;

public class Sheep extends Herbivore {
    public Sheep() {
        super(Settings.get().getAnimalProps().get("Sheep"));
    }
}
