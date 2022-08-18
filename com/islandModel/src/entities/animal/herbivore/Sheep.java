package com.islandModel.src.entities.animal.herbivore;

import com.islandModel.src.entities.settings.Settings;

public class Sheep extends Herbivore {
    public Sheep() {
        super(Settings.get().getAnimalProps().get("Sheep"));
    }
}
