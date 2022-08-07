package com.island.animal;

import com.island.island.Caterpillar;

import java.util.HashMap;

// into annotation ???
public enum AnimalBase {
//    todo uppercase
    Horse(
            AnimalKind.HORSE,
            new HashMap<>() {{
                put(AnimalKind.BOA, 100);
            }},
            "\uD83D\uDC0E",
            4,
            400F,
            20,
            60F,
            10
    ),
    Wolf(
            AnimalKind.WOLF,
            new HashMap<>() {{
                put(AnimalKind.HORSE, 10);
            }},
            "\uD83D\uDC3A",
            3,
            50F,
            30,
            8F,
            10
    ),
    Boa(
            AnimalKind.BOA,
            new HashMap<>() {{
                put(AnimalKind.WOLF, 100);
            }},
            "\uD83D\uDC0D",
            3,
            5F,
            30,
            8F,
            100
    ),
    Mouse(
            AnimalKind.MOUSE,
            new HashMap<>() {{
                put(AnimalKind.CATERPILLAR, 90);
            }},
            "\uD83D\uDC01",
            1,
            0.05F,
            500,
            0.01F,
            100
    ),
    Caterpillar(
            AnimalKind.CATERPILLAR,
            new HashMap<>(),
            "\uD83D\uDC1B",
            0,
            0.01F,
            1000,
            0F,
            10
    );

    public final AnimalKind kind;
    public final HashMap<AnimalKind, Integer> menu;
    public final String icon;
    public final int speed;
    public final float weight;
    public final int maxOnLocation;
    public final float satietyLimit;
    public final float wastedSatietyPerStep;
    public final int reproductionChance;

    AnimalBase(
            AnimalKind kind, HashMap<AnimalKind,
            Integer> menu, String icon,
            int speed,
            float weight,
            int maxOnLocation,
            float satietyLimit,
            int reproductionChance
    ) {
        this.kind = kind;
        this.menu = menu;
        this.icon = icon;
        this.speed = speed;
        this.weight = weight;
        this.maxOnLocation = maxOnLocation;
        this.satietyLimit = satietyLimit;
        this.wastedSatietyPerStep = satietyLimit / 6;
        this.reproductionChance = reproductionChance;
    }
}
