package com.islanddd.src.util;

import java.util.concurrent.ThreadLocalRandom;

public final class Randomizer {
    private static final int MAX_CHANCE_BOUND = 101;
    private static final int MIN_CHANCE_BOUND = 1;
    public static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static boolean isSuccess(int chance) {
        return chance >= RANDOM.nextInt(MIN_CHANCE_BOUND, MAX_CHANCE_BOUND);
    }
}
