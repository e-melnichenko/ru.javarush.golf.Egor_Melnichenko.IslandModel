package island.vegetation;

import java.util.concurrent.ThreadLocalRandom;

public class Vegetation {
    public float value;
    private static final float MAX = 200F;
    private static final float GROW_STEP = 100F;

    public Vegetation() {
        this.value = (float) ThreadLocalRandom.current().nextInt((int) MAX+1);
    }

    public void grow() {
        if(value + GROW_STEP >= MAX) {
            value = MAX;
        } else {
            value += GROW_STEP;
        }
    }

    @Override
    public String toString() {
        return "\uD83C\uDF3F" + value;
    }
}
