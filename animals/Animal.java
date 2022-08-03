package animals;

import island.Island;

public abstract class Animal {
    public boolean moved = false;
    abstract public void move(int[] from, Island island);
}
