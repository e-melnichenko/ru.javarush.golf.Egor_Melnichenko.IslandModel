package com.islandModel.src.entities.animal.herbivore;

import com.islandModel.src.entities.settings.Settings;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(Settings.get().getAnimalProps().get("Rabbit"));
    }
}
