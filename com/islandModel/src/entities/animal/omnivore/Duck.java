package com.islandModel.src.entities.animal.omnivore;

import com.islandModel.src.entities.settings.Settings;

public class Duck extends Omnivore {
    public Duck() {
        super(Settings.get().getAnimalProps().get("Duck"));
    }
}
