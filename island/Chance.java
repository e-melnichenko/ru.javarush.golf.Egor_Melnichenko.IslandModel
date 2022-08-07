package island;

import java.util.concurrent.ThreadLocalRandom;

public final class Chance {
    private static final int MAX_CHANCE_BOUND = 101;
    private static final int MIN_CHANCE_BOUND = 1;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static boolean isSuccess(int chance) {
        return chance >= RANDOM.nextInt(MIN_CHANCE_BOUND, MAX_CHANCE_BOUND);
    }
}
