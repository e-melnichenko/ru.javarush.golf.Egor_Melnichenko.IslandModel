package com.islandModel.src.services;

import com.islandModel.src.entities.animal.Animal;
import com.islandModel.src.entities.island.Location;

public class Task {
    private final Animal animal;
    private final Location location;
    private final Location[][] area;

    public Task(Animal animal, Location location, Location[][] area) {
        this.animal = animal;
        this.location = location;
        this.area = area;
    }

    public void perform() {
        try {
            animal.reproduce(location);
            animal.feed(location);
            animal.move(location, area);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
