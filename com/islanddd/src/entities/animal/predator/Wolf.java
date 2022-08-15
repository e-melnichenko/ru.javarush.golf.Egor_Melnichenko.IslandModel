package com.islanddd.src.entities.animal.predator;

import com.islanddd.src.entities.settings.Settings;

public class Wolf extends Predator {
    public Wolf() {
        super(Settings.get().getAnimalProps().get("Wolf"));
    }
}
