package com.islandModel.src.entities.animal.predator;

import com.islandModel.src.entities.settings.Settings;

public class Eagle extends Predator {
    public Eagle() {
        super(Settings.get().getAnimalProps().get("Eagle"));
    }
}
