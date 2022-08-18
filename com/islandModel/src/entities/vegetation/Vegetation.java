package com.islandModel.src.entities.vegetation;

import com.islandModel.src.entities.settings.Settings;

import java.util.concurrent.ThreadLocalRandom;

public class Vegetation {
    public float value;
    private static final float MAX = Settings.get().getMaxVegetationOnLocation();
    private static final float GROW_STEP = Settings.get().getVegetationGrowPerStep();

    public Vegetation() {
        this.value = (float) ThreadLocalRandom.current().nextInt((int) MAX+1);
    }

    public void grow() {
        value = Math.min(MAX, value + GROW_STEP);
    }

    @Override
    public String toString() {
        return Settings.get().getVegetationIcon() + value;
    }
}
