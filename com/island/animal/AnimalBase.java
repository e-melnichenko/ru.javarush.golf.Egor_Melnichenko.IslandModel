package com.island.animal;

import java.util.HashMap;

// into annotation ???
public enum AnimalBase {
    Horse(
            AnimalClass.HERBIVORE,
            AnimalKind.HORSE,
            new HashMap<>(),
            "\uD83D\uDC0E",
            4,
            400F,
            20,
            60F
    ),
    Wolf(
            AnimalClass.PREDATOR,
            AnimalKind.WOLF,
            new HashMap<>(){{
               put(AnimalKind.HORSE, 10);
            }},
            "\uD83D\uDC3A",
            3,
            50F,
            30,
            8F
    );

    public final AnimalClass animalClass;
    public final AnimalKind kind;
    public final HashMap<AnimalKind, Integer> menu;
    public final String icon;
    public final int speed;
    public final float weight;
    public final int maxOnLocation;
    public final float satietyLimit;
    public final float wastedSatietyPerStep;

    AnimalBase(AnimalClass animalClass, AnimalKind kind, HashMap<AnimalKind, Integer> menu, String icon, int speed, float weight, int maxOnLocation, float satietyLimit) {
        this.animalClass = animalClass;
        this.kind = kind;
        this.menu = menu;
        this.icon = icon;
        this.speed = speed;
        this.weight = weight;
        this.maxOnLocation = maxOnLocation;
        this.satietyLimit = satietyLimit;
        this.wastedSatietyPerStep = satietyLimit / 6;
    }
}
