package com.islandModel.src.entities.animal.predator;

import com.islandModel.src.entities.settings.Settings;

public class Fox extends Predator {
    public Fox() {
        super(Settings.get().getAnimalProps().get("Fox"));
    }
}
