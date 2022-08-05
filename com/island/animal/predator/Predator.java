package com.island.animal.predator;

import com.island.animal.Animal;
import com.island.animal.AnimalBase;
import com.island.animal.AnimalKind;
import com.island.animal.CanHunt;
import com.island.island.Island;
import com.island.island.Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal implements CanHunt {
    public Predator(int id, AnimalBase base) {
        super(id, base);
    }

    @Override
    public void hunt(Location location) {
        this.base.menu.entrySet().forEach(entry -> {
//            herbivores and predators into one map ???
//            Set is shuffle!!!
            AnimalKind victimKind = entry.getKey();
            int successHuntChance = entry.getValue();

            ArrayList<? extends Animal> animalList  = location.herbivoresMap.get(victimKind);
            if(animalList == null) {
                animalList = location.predatorsMap.get(victimKind);
            }
            if(animalList == null) {
                throw new IllegalArgumentException("Вид " + victimKind + " не может существовать в локации");
            }

//            todo replace iterator
            Iterator<? extends Animal> iterator = animalList.iterator();
            while(iterator.hasNext()) {
                Animal victim = iterator.next();
                boolean successHunt = successHuntChance >= ThreadLocalRandom.current().nextInt(Island.MAX_CHANCE_BOUND);
                if(successHunt) {
                    iterator.remove();
                    System.out.println(this + " eat " + victim);
                } else {
//                    System.out.println("hunt failed: " + this);
                }
            }
        });

    }
}
