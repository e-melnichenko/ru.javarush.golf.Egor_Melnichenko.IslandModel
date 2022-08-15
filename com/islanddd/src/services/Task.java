package com.islanddd.src.services;

import com.islanddd.src.entities.animal.Animal;
import com.islanddd.src.entities.island.Location;

public class Task implements Performable {
    private final Animal animal;
    private final Location location;
    private final Location[][] area;

    public Task(Animal animal, Location location, Location[][] area) {
        this.animal = animal;
        this.location = location;
        this.area = area;
    }

    public void perform() {
        animal.reproduce(location);
        animal.feed(location);
        animal.move(location, area);
        animal.clear(location);
    }
}
