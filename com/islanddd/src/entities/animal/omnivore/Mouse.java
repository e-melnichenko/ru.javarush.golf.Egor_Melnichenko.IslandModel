package com.islanddd.src.entities.animal.omnivore;

import com.islanddd.src.entities.settings.Settings;

public class Mouse extends Omnivore {
    public Mouse() {
        super(Settings.get().getAnimalProps().get("Mouse"));
    }
}
