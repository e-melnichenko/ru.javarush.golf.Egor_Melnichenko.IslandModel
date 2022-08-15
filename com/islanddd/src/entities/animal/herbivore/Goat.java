package com.islanddd.src.entities.animal.herbivore;

import com.islanddd.src.entities.settings.Settings;

public class Goat extends Herbivore {
    public Goat() {
        super(Settings.get().getAnimalProps().get("Goat"));
    }
}
