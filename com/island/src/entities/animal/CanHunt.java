package com.island.src.entities.animal;

import com.island.src.Chance;
import com.island.src.island.Location;

import java.util.ArrayList;
import java.util.Map;

public interface CanHunt {
default void hunt(Location location, Animal hunter)  {

    outerloop:
    for (Map.Entry<AnimalKind, Integer> entry : hunter.base.menu.entrySet()) {
        AnimalKind victimKind = entry.getKey();
        int successHuntChance = entry.getValue();
        ArrayList<Animal> list = location.animalsMap.get(victimKind);
        if(list.isEmpty()) continue;
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
            if(!Chance.isSuccess(successHuntChance)) continue;

            eatAnimal(victim, hunter);
        }
    }
}

    private void eatAnimal(Animal victim, Animal hunter) {
        if(victim.weight <= 0) return;

        float need = hunter.base.satietyLimit - hunter.satiety;
        float willBeEaten = Math.max(need, victim.weight);

        hunter.satiety += willBeEaten;
        victim.weight -= willBeEaten;
        victim.isDead = true;
    }
}
