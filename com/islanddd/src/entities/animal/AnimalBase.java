package com.islanddd.src.entities.animal;

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
    public float wastedSatietyPerStep;

    public AnimalBase() {}

    public AnimalBase(AnimalKind kind, String icon, int speed, float weight, int maxOnLocation, float satietyLimit, int reproductionChance, Map<AnimalKind, Integer> menu) {
        this.kind = kind;
        this.icon = icon;
        this.speed = speed;
        this.weight = weight;
        this.maxOnLocation = maxOnLocation;
        this.satietyLimit = satietyLimit;
        this.reproductionChance = reproductionChance;
        this.menu = menu;
        this.wastedSatietyPerStep = satietyLimit / 10;
    }
}
