package com.islanddd.src.entities.animal.herbivore;

import com.islanddd.src.entities.settings.Settings;

public class Horse extends Herbivore {
    public Horse() {
        super(Settings.get().getAnimalProps().get("Horse"));
    }
}
