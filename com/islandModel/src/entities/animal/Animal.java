package com.islandModel.src.entities.animal;

import com.islandModel.src.entities.island.Coords;
import com.islandModel.src.entities.island.Direction;
import com.islandModel.src.entities.island.Location;
import com.islandModel.src.util.IdGenerator;
import com.islandModel.src.util.Randomizer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class Animal implements Movable, CanFeed {
    public final int id;
    public AnimalBase base;
    public float satiety;
    public boolean isDead = false;
    public boolean isChild = false;
    public float weight;
    public boolean moved = false;

    public Animal(AnimalBase base) {
        this.id = IdGenerator.get();
        this.base = base;
        this.weight = base.weight;
        this.satiety = base.satietyLimit;
    }

    public boolean isHungry() {
        return satiety < base.satietyLimit;
    }

    private void safeMove(Location fromLocation, Location[][] area) {
        if(moved) return;
        
        fromLocation.getLock().lock();

        try {
            Location result = fromLocation;

            for (int i = 0; i < base.speed; i++) {
                Direction[] directions = result.getAvailableDirections().directionList;
                int randomIndex = Randomizer.RANDOM.nextInt(directions.length);
                Coords delta = directions[randomIndex].getDelta();
                Coords newCoords = result.coords.evaluate(delta);
                Location newLocation = area[newCoords.x][newCoords.y];
                if (newLocation.hasFreeSpace(this)) {
                    result = newLocation;
                }
            }

            if (result != fromLocation) {
//                todo lock destination location. Otherwise throws exception when multithreading or deadlock
                fromLocation.animalsMap.get(this.base.kind).remove(this);
                result.animalsMap.get(this.base.kind).add(this);
            }

            float satietyRes = satiety - base.getWastedSatietyPerStep();
            satiety = satietyRes <= 0 ? 0 : satietyRes;
            moved = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fromLocation.getLock().unlock();
        }
    }

    @Override
    public void move(Location fromLocation, Location[][] area) {
        safeMove(fromLocation, area);
    }

    public void reproduce(Location location) {
        safeReproduce(location);
    }

    private void safeReproduce(Location location) {
        location.getLock().lock();

        try {
            ArrayList<Animal> list = location.animalsMap.get(base.kind);
            if (
                list.size() < 2 ||
                isChild ||
                !location.hasFreeSpace(this) ||
                !Randomizer.isSuccess(base.reproductionChance)
            ) return;

            Animal child = tryToCreateNewAnimal();
            child.isChild = true;
            list.add(child);
        } finally {
            location.getLock().unlock();
        }
    }

    public Animal tryToCreateNewAnimal() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        String res = base.icon + id + "S:\u001B[32m" + satiety;

        if (isDead) {
            res += "\u001B[35mD";
        }
        if (isChild) {
            res += "\u001B[35mCh";
        }

        return res + "\u001B[0m";
    }
}
