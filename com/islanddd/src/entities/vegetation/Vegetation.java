package com.islanddd.src.entities.vegetation;

import java.util.concurrent.ThreadLocalRandom;

public class Vegetation {
    public float value;
    private static final float MAX = 200F;
    private static final float GROW_STEP = 50F;

    public Vegetation() {
        this.value = (float) ThreadLocalRandom.current().nextInt((int) MAX+1);
    }

    public void grow() {
        value = Math.min(MAX, value + GROW_STEP);
    }

    @Override
    public String toString() {
        return "\uD83C\uDF3F" + value;
    }
}
