package com.islandModel.src.entities.animal.herbivore;

import com.islandModel.src.entities.settings.Settings;

public class Buffalo extends Herbivore {
    public Buffalo() {
        super(Settings.get().getAnimalProps().get("Buffalo"));
    }
}
