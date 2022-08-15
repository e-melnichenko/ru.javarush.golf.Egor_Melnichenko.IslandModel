package com.islanddd.src.entities.animal.herbivore;

import com.islanddd.src.entities.settings.Settings;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(Settings.get().getAnimalProps().get("Caterpillar"));
    }
}
