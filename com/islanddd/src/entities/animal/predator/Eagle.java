package com.islanddd.src.entities.animal.predator;

import com.islanddd.src.entities.settings.Settings;

public class Eagle extends Predator {
    public Eagle() {
        super(Settings.get().getAnimalProps().get("Eagle"));
    }
}
