package com.islandModel.src.entities.animal;

import java.util.Map;

public class AnimalBase {
    public AnimalKind kind;
    public String icon;
    public int speed;
    public float weight;
    public int maxOnLocation;
    public float satietyLimit;
    public int reproductionChance;
    public Map<AnimalKind, Integer> menu;
    public int stepsCountWithoutFood;

    public AnimalBase() {}

    public float getWastedSatietyPerStep() {
        return satietyLimit / stepsCountWithoutFood;
    }
}
