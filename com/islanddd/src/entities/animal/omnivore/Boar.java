package com.islanddd.src.entities.animal.omnivore;

import com.islanddd.src.entities.settings.Settings;

public class Boar extends Omnivore {
    public Boar() {
        super(Settings.get().getAnimalProps().get("Boar"));
    }
}
