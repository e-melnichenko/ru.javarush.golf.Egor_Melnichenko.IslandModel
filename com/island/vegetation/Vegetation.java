package com.island.vegetation;

import com.island.animal.herbivore.Herbivore;

import java.util.concurrent.ThreadLocalRandom;

public class Vegetation {
    public float value;
    private static final float MAX = 1F;
    private static final float GROW_STEP = 0.1F;

    public Vegetation() {
        this.value = (float) ThreadLocalRandom.current().nextInt((int) MAX+1);
    }

//    public void feed(Herbivore herbivore) {
//        if(value <= 0) return;
//
//        float needVegetation = herbivore.base.satietyLimit - herbivore.satiety;
//        float possibleValue = needVegetation < value ? needVegetation : value;
//        herbivore.eat(possibleValue);
//        value -= possibleValue;
//    }
    public void grow() {
        if(value + GROW_STEP >= MAX) {
            value = MAX;
        } else {
            value += GROW_STEP;
        }
    }

    @Override
    public String toString() {
        return "\uD83C\uDF3F" + value;
    }
}
