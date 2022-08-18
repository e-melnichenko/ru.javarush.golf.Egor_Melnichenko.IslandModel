package com.islandModel.src.entities.animal.predator;

import com.islandModel.src.entities.settings.Settings;

public class Boa extends Predator {
    public Boa() {
        super(Settings.get().getAnimalProps().get("Boa"));
    }
}
