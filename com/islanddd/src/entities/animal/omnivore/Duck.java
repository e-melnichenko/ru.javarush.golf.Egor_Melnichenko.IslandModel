package com.islanddd.src.entities.animal.omnivore;

import com.islanddd.src.entities.settings.Settings;

public class Duck extends Omnivore {
    public Duck() {
        super(Settings.get().getAnimalProps().get("Duck"));
    }
}
