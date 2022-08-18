package com.islandModel.src.services;

import com.islandModel.src.entities.island.Location;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LocationWorker implements Runnable {
    private final Location location;
    private final Queue<Task> tasks = new ConcurrentLinkedQueue<>();
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
        } finally {
            location.getLock().unlock();
        }

        tasks.forEach(Task::perform);
        tasks.clear();
    }
}
