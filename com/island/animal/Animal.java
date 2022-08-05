package com.island.animal;

import com.island.animal.herbivore.Herbivore;
import com.island.island.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal implements Movable {
    public final int id;
    public final AnimalBase base;
    public float satiety;
    public boolean moved = false;

    public Animal(int id, AnimalBase base) {
        this.id = id;
        this.base = base;
        this.satiety = base.satietyLimit;
    }

//    public void die(Location location) {
//        if(this instanceof Herbivore) {
//            location.herbivoresMap.get(base.kind).remove(this);
//            System.out.println("died: " + this);
//        }
//    }

    @Override
    public void resetMove() {
        moved = false;
    }

    @Override
    public Location move(Location fromLocation, Island island) {
//        System.out.println(this);
        Location result = fromLocation;

        for (int i = 0; i < base.speed; i++) {
            Direction[] directions = result.getAvailableDirections().directionList;
            int randomIndex = ThreadLocalRandom.current().nextInt(directions.length);
            CoordsDelta delta = directions[randomIndex].getDelta();

            Coords newCoords = result.coords.evaluate(delta);
//            System.out.println(result.coords.x + ":" + result.coords.y + "->" + newCoords.x + ":" + newCoords.y);
            Location newLocation = island.getLocation(newCoords);
            if(newLocation.hasFreeSpace(this)) {
                result = newLocation;
            } else {
//                System.out.println("throttle - max on location");
            }
        }

        float satietyRes = satiety - base.satietyLimit / 6;
        satiety = satietyRes <= 0 ? 0 : satietyRes;
        return result;
    }


    @Override
    public String toString() {
        return base.icon + id + "S:\u001B[32m" + satiety + "\u001B[0m";
    }
}
