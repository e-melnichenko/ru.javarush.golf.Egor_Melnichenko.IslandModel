package com.islanddd.src.entities.animal.predator;

import com.islanddd.src.entities.settings.Settings;

public class Bear extends Predator {
    public Bear() {
        super(Settings.get().getAnimalProps().get("Bear"));
    }
}
