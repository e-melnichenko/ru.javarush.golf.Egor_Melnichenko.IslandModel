package com.island.animal.predator;

import com.island.Chance;
import com.island.animal.Animal;
import com.island.animal.AnimalBase;
import com.island.animal.AnimalKind;
import com.island.animal.CanHunt;
import com.island.island.Island;
import com.island.island.Location;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal implements CanHunt {
    public Predator(int id, AnimalBase base) {
        super(id, base);
    }

    @Override
    public void hunt(Location location) {
        outerloop:
        for (Map.Entry<AnimalKind, Integer> entry : base.menu.entrySet()) {
//            herbivores and predators into one map ???
            AnimalKind victimKind = entry.getKey();
            int successHuntChance = entry.getValue();

            ArrayList<? extends Animal> animalList = location.getAnimalListByKind(victimKind);

//            bad: hunt all
            for (Animal victim : animalList) {
                if(victim.isDead) continue;

                if (!Chance.isSuccess(successHuntChance)) continue;

                eatAnimal(victim);
                System.out.println(this + " eat " + victim);

                if (satiety == base.satietyLimit) {
                    System.out.println("loop exit: " + this);
                    break outerloop;
                }
            }
        }
    }

    @Override
    public void eatAnimal(Animal victim) {
//        todo can eat dead animals
        satiety = satiety + victim.base.weight < base.satietyLimit ? satiety + victim.base.weight : base.satietyLimit;
        victim.isDead = true;
    }
}
