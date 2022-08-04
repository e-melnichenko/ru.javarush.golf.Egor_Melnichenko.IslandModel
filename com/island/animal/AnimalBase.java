package com.island.animal;

public enum AnimalBase {
    Horse(AnimalClass.HERBIVORE, "Horse", "\uD83D\uDC0E", 4, 400F, 20, 60F),
    Wolf(AnimalClass.PREDATOR, "Wolf", "\uD83D\uDC3A", 3, 50F, 30, 8F);

    public final AnimalClass animalClass;
    public final String kind;
    public final String icon;
    public final int speed;
    public final float weight;
    public final int maxOnLocation;
    public final float satietyLimit;

    AnimalBase(AnimalClass animalClass, String kind, String icon, int speed, float weight, int maxOnLocation, float satietyLimit) {
        this.animalClass = animalClass;
        this.kind = kind;
        this.icon = icon;
        this.speed = speed;
        this.weight = weight;
        this.maxOnLocation = maxOnLocation;
        this.satietyLimit = satietyLimit;
    }
}
