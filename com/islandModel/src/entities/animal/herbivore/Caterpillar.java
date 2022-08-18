package com.islandModel.src.entities.animal.herbivore;

import com.islandModel.src.entities.settings.Settings;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(Settings.get().getAnimalProps().get("Caterpillar"));
    }
}
