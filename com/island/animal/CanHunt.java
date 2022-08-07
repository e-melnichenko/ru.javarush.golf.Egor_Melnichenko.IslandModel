package com.island.animal;

import com.island.Chance;
import com.island.island.Location;

import java.util.ArrayList;
import java.util.Map;

public interface CanHunt {
default void hunt(Location location, Animal hunter)  {
    outerloop:
    for (Map.Entry<AnimalKind, Integer> entry : hunter.base.menu.entrySet()) {
        AnimalKind victimKind = entry.getKey();
        int successHuntChance = entry.getValue();

////            todo attempts to hunt in one step
        for (Animal victim : location.animalsMap.get(victimKind)) {
            if(victim.isDead || !Chance.isSuccess(successHuntChance)) continue;

            eatAnimal(victim, hunter);
            System.out.println(this + " eat " + victim);

            if (!hunter.isHungry()) {
                System.out.println("loop exit: " + this);
                break outerloop;
            }
        }

//        ArrayList<? extends Animal> animalList = location.getAnimalListByKind(victimKind);
//
//        for (Animal victim : animalList) {
//            if(victim.isDead) continue;
//
//            if (!Chance.isSuccess(successHuntChance)) continue;
//
//            eatAnimal(victim, hunter);
//            System.out.println(this + " eat " + victim);
//
//            if (hunter.satiety == hunter.base.satietyLimit) {
//                System.out.println("loop exit: " + this);
//                break outerloop;
//            }
//        }
    }
}

    private void eatAnimal(Animal victim, Animal hunter) {
//        todo can eat dead animals
        hunter.satiety = Math.min(hunter.satiety + victim.base.weight, hunter.base.satietyLimit);
        victim.isDead = true;
    }
}
