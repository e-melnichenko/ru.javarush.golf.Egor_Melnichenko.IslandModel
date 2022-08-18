package com.islandModel.src.entities.animal.omnivore;

import com.islandModel.src.entities.settings.Settings;

public class Boar extends Omnivore {
    public Boar() {
        super(Settings.get().getAnimalProps().get("Boar"));
    }
}
