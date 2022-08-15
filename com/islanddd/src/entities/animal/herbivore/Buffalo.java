package com.islanddd.src.entities.animal.herbivore;

import com.islanddd.src.entities.settings.Settings;

public class Buffalo extends Herbivore {
    public Buffalo() {
        super(Settings.get().getAnimalProps().get("Buffalo"));
    }
}
