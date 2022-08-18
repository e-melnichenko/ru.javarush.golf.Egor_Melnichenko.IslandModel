package com.islandModel.src.entities.animal.predator;

import com.islandModel.src.entities.settings.Settings;

public class Bear extends Predator {
    public Bear() {
        super(Settings.get().getAnimalProps().get("Bear"));
    }
}
