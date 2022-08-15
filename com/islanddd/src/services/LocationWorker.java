package com.islanddd.src.services;

import com.islanddd.src.entities.island.Location;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LocationWorker implements Runnable {
    private final Location location;
    private final Queue<Performable> tasks = new ConcurrentLinkedQueue<>();
    private final Location[][] area;

    public LocationWorker(Location location, Location[][] area) {
        this.location = location;
        this.area = area;
    }

    @Override
    public void run() {
        location.getLock().lock();

        try {
            location.forEachAnimal(animal -> tasks.add(new Task(animal, location, area)));
            tasks.add(new LocationTask(location));
        } finally {
            location.getLock().unlock();
        }

        tasks.forEach(Performable::perform);
        tasks.clear();
    }
}
