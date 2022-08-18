package com.islandModel.src.entities.animal;

import com.islandModel.src.entities.island.Location;
import com.islandModel.src.util.Randomizer;

import java.util.ArrayList;
import java.util.Map;

public interface CanHunt {
default void hunt(Location location, Animal hunter)  {
    location.getLock().lock();

    try {
        outerloop:
        for (Map.Entry<AnimalKind, Integer> entry : hunter.base.menu.entrySet()) {
            AnimalKind victimKind = entry.getKey();
            int successHuntChance = entry.getValue();
            ArrayList<Animal> list = location.animalsMap.get(victimKind);
            if(list == null || list.isEmpty()) continue;
            int attemptsCount = Math.max(1, list.get(0).base.maxOnLocation/3);

            for (int i = 0; i < list.size(); i++) {
                if (!hunter.isHungry()) break outerloop;
                if(attemptsCount <= 0) break;

                Animal victim = list.get(i);

                if(victim.isDead) {
                    eatAnimal(victim, hunter);
                    continue;
                }

                attemptsCount--;
                if(!Randomizer.isSuccess(successHuntChance)) continue;

                eatAnimal(victim, hunter);
            }
        }
    } finally {
        location.getLock().unlock();
    }
}

    private void eatAnimal(Animal victim, Animal hunter) {
        if(victim.weight <= 0) return;

        float need = hunter.base.satietyLimit - hunter.satiety;
        float willBeEaten = victim.weight - need > 0 ? need : victim.weight;

        hunter.satiety += willBeEaten;
        victim.weight -= willBeEaten;
        victim.isDead = true;
    }
}
