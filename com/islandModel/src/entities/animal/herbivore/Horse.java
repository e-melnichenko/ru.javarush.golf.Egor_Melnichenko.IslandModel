package com.islandModel.src.entities.animal.herbivore;

import com.islandModel.src.entities.settings.Settings;

public class Horse extends Herbivore {
    public Horse() {
        super(Settings.get().getAnimalProps().get("Horse"));
    }
}
