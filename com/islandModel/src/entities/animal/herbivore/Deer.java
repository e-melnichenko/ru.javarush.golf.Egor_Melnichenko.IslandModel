package com.islandModel.src.entities.animal.herbivore;

import com.islandModel.src.entities.settings.Settings;

public class Deer extends Herbivore {
    public Deer() {
        super(Settings.get().getAnimalProps().get("Deer"));
    }
}
