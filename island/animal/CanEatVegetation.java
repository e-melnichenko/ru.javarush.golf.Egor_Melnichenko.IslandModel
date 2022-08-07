package island.animal;

import island.island.Location;

public interface CanEatVegetation {
    default void eatVegetation(Location location, Animal herbivore) {
        float vegetationValue = location.vegetation.value;
        if(vegetationValue <= 0) return;

        float needVegetation = herbivore.base.satietyLimit - herbivore.satiety;
        float possibleValue = Math.min(needVegetation, vegetationValue);
        herbivore.satiety += possibleValue;
        location.vegetation.value -= possibleValue;
    }
}
