package island.animal;

import island.Chance;
import island.island.Location;

import java.util.ArrayList;
import java.util.Map;

public interface CanEatAnimal {
    default void hunt(Location location, Animal hunter)  {
        outerloop:
        for (Map.Entry<AnimalKind, Integer> entry : hunter.base.menu.entrySet()) {
//            herbivores and predators into one map ???
            AnimalKind victimKind = entry.getKey();
            int successHuntChance = entry.getValue();

            ArrayList<? extends Animal> animalList = location.getAnimalListByKind(victimKind);

//            todo attempts to hunt in one step
            for (Animal victim : animalList) {
                if(victim.isDead) continue;

                if (!Chance.isSuccess(successHuntChance)) continue;

                eatAnimal(victim, hunter);
                System.out.println(this + " eat " + victim);

                if (hunter.satiety == hunter.base.satietyLimit) {
                    System.out.println("loop exit: " + this);
                    break outerloop;
                }
            }
        }
    }

    private void eatAnimal(Animal victim, Animal hunter) {
//        todo can eat dead animals
        hunter.satiety = hunter.satiety + victim.base.weight < hunter.base.satietyLimit ? hunter.satiety + victim.base.weight : hunter.base.satietyLimit;
        victim.isDead = true;
    }
}
