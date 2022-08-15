package com.islanddd.src.services;

import com.islanddd.src.entities.island.Location;

public class LocationTask implements Performable {
    private final Location location;

    public LocationTask(Location location) {
        this.location = location;
    }

    public void perform() {
        location.vegetationGrow();
    }
}
