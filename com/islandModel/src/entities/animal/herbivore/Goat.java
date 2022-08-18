package com.islandModel.src.entities.animal.herbivore;

import com.islandModel.src.entities.settings.Settings;

public class Goat extends Herbivore {
    public Goat() {
        super(Settings.get().getAnimalProps().get("Goat"));
    }
}
