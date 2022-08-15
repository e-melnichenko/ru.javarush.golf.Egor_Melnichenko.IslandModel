package com.islanddd.src.entities.animal.herbivore;

import com.islanddd.src.entities.settings.Settings;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(Settings.get().getAnimalProps().get("Rabbit"));
    }
}
