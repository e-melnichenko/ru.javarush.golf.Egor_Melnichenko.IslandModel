package com.islanddd.src.entities.animal.predator;

import com.islanddd.src.entities.settings.Settings;

public class Fox extends Predator {
    public Fox() {
        super(Settings.get().getAnimalProps().get("Fox"));
    }
}
