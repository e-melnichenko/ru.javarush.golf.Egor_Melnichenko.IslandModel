package island.animal;

import island.Chance;
import island.island.Location;

import java.util.Map;

public interface CanHunt {
default void hunt(Location location, Animal hunter)  {
    outerloop:
    for (Map.Entry<AnimalKind, Integer> entry : hunter.base.menu.entrySet()) {
        AnimalKind victimKind = entry.getKey();
        int successHuntChance = entry.getValue();

        for (Animal victim : location.animalsMap.get(victimKind)) {
            if(victim.isDead || !Chance.isSuccess(successHuntChance)) continue;

            eatAnimal(victim, hunter);

            if (!hunter.isHungry()) {
                break outerloop;
            }
        }
    }
}

    private void eatAnimal(Animal victim, Animal hunter) {
        hunter.satiety = Math.min(hunter.satiety + victim.base.weight, hunter.base.satietyLimit);
        victim.isDead = true;
    }
}
