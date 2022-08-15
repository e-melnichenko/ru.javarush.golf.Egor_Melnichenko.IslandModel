package com.islanddd.src.util;

public class IdGenerator {
    private static int id = 1;
    public static int get() {
        return id++;
    }
}
