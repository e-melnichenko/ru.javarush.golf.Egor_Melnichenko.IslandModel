package com.islandModel.src.entities.animal;

import com.islandModel.src.entities.island.Location;

public interface CanEatVegetation {
    default void eatVegetation(Location location, Animal herbivore) {
        location.getLock().lock();

        try {
            float vegetationValue = location.vegetation.value;
            if(vegetationValue <= 0) return;

            float needVegetation = herbivore.base.satietyLimit - herbivore.satiety;
            float possibleValue = Math.min(needVegetation, vegetationValue);
            herbivore.satiety += possibleValue;
            location.vegetation.value -= possibleValue;
        } finally {
            location.getLock().unlock();
        }
    }
}
