package com.islanddd.src.entities.animal.herbivore;

import com.islanddd.src.entities.settings.Settings;

public class Deer extends Herbivore {
    public Deer() {
        super(Settings.get().getAnimalProps().get("Deer"));
    }
}
