package com.islanddd.src.entities.animal.predator;

import com.islanddd.src.entities.settings.Settings;

public class Boa extends Predator {
    public Boa() {
        super(Settings.get().getAnimalProps().get("Boa"));
    }
}
