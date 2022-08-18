package com.islandModel.src.entities.animal.omnivore;

import com.islandModel.src.entities.settings.Settings;

public class Mouse extends Omnivore {
    public Mouse() {
        super(Settings.get().getAnimalProps().get("Mouse"));
    }
}
