package com.islanddd.src.services;

import com.islanddd.src.entities.Game;
import com.islanddd.src.entities.settings.Settings;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameWorker extends Thread {
    private final Game game;
    private final int CORE_POOL_SIZE = Settings.get().getCorePoolSize();
    private final int LIFE_CYCLE_DURATION = Settings.get().getLifeCycleDuration();

    public GameWorker(Game game) {
        this.game = game;
    }
    @Override
    public void run() {
        game.printStats();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        scheduledExecutorService.scheduleWithFixedDelay(this::runAndWaitLocationWorkers, LIFE_CYCLE_DURATION, LIFE_CYCLE_DURATION, TimeUnit.MILLISECONDS);
    }

    public void runAndWaitLocationWorkers() {
        ArrayList<LocationWorker> locationWorkers = new ArrayList<>();

        game.getIsland().locationsStream().forEach(location -> {
            locationWorkers.add(new LocationWorker(location, game.getIsland().getArea()));
        });

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(CORE_POOL_SIZE);
        for (LocationWorker locationWorker : locationWorkers) {
            fixedThreadPool.submit(locationWorker);
        }

        fixedThreadPool.shutdown();

        try {
            if (fixedThreadPool.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS)) {
                game.printStats();
            }
        } catch (InterruptedException e) {
            //
            System.out.println("The game is finished");
        }
    }
}
