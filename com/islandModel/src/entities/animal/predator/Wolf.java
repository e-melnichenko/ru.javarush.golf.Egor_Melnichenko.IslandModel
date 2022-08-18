package com.islandModel.src.entities.animal.predator;

import com.islandModel.src.entities.settings.Settings;

public class Wolf extends Predator {
    public Wolf() {
        super(Settings.get().getAnimalProps().get("Wolf"));
    }
}
