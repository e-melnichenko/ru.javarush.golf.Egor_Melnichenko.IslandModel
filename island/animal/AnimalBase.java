package island.animal;

import java.util.HashMap;

public enum AnimalBase {
    WOLF(
            AnimalKind.WOLF,
            new HashMap<>() {{
                put(AnimalKind.HORSE, 10);
                put(AnimalKind.DEER, 15);
                put(AnimalKind.RABBIT, 60);
                put(AnimalKind.MOUSE, 80);
                put(AnimalKind.GOAT, 60);
                put(AnimalKind.SHEEP, 70);
                put(AnimalKind.BOAR, 15);
                put(AnimalKind.BUFFALO, 10);
                put(AnimalKind.DUCK, 40);
            }},
            "\uD83D\uDC3A",
            3,
            50F,
            30,
            8F,
            20
    ),
    BOA(
            AnimalKind.BOA,
            new HashMap<>() {{
                put(AnimalKind.FOX, 15);
                put(AnimalKind.RABBIT, 20);
                put(AnimalKind.MOUSE, 40);
                put(AnimalKind.DUCK, 10);
            }},
            "\uD83D\uDC0D",
            1,
            15F,
            30,
            3F,
            30
    ),
    FOX(
            AnimalKind.FOX,
            new HashMap<>() {{
                put(AnimalKind.RABBIT, 70);
                put(AnimalKind.MOUSE, 90);
                put(AnimalKind.DUCK, 60);
                put(AnimalKind.CATERPILLAR, 40);
            }},
            "\uD83E\uDD8A",
            2,
            8F,
            30,
            2F,
            30
    ),
    BEAR(
            AnimalKind.BEAR,
            new HashMap<>() {{
                put(AnimalKind.BOA, 80);
                put(AnimalKind.HORSE, 40);
                put(AnimalKind.DEER, 80);
                put(AnimalKind.RABBIT, 80);
                put(AnimalKind.MOUSE, 90);
                put(AnimalKind.GOAT, 70);
                put(AnimalKind.SHEEP, 70);
                put(AnimalKind.BOAR, 50);
                put(AnimalKind.BUFFALO, 20);
                put(AnimalKind.DUCK, 10);
            }},
            "\uD83D\uDC3B",
            2,
            500F,
            5,
            80F,
            30
    ),
    EAGLE(
            AnimalKind.EAGLE,
            new HashMap<>() {{
                put(AnimalKind.FOX, 10);
                put(AnimalKind.RABBIT, 90);
                put(AnimalKind.MOUSE, 90);
                put(AnimalKind.DUCK, 80);
            }},
            "\uD83E\uDD85",
            3,
            6F,
            20,
            1F,
            30
    ),
    HORSE(
            AnimalKind.HORSE,
            new HashMap<>(),
            "\uD83D\uDC0E",
            4,
            400F,
            20,
            60F,
            50
    ),
    DEER(
            AnimalKind.DEER,
            new HashMap<>(),
            "\uD83E\uDD8C",
            4,
            300F,
            20,
            50F,
            50
    ),
    RABBIT(
            AnimalKind.RABBIT,
            new HashMap<>(),
            "\uD83D\uDC07",
            2,
            2F,
            150,
            0.45F,
            50
    ),
    MOUSE(
            AnimalKind.MOUSE,
            new HashMap<>() {{
                put(AnimalKind.CATERPILLAR, 90);
            }},
            "\uD83D\uDC01",
            1,
            0.05F,
            500,
            0.01F,
            50
    ),
    GOAT(
            AnimalKind.GOAT,
            new HashMap<>(),
            "\uD83D\uDC10",
            3,
            60F,
            140,
            10F,
            50
    ),
    SHEEP(
            AnimalKind.SHEEP,
            new HashMap<>(),
            "\uD83D\uDC11",
            3,
            70F,
            140,
            15F,
            50
    ),
    BOAR(
            AnimalKind.BOAR,
            new HashMap<>() {{
                put(AnimalKind.MOUSE, 50);
                put(AnimalKind.CATERPILLAR, 90);
            }},
            "\uD83D\uDC17",
            2,
            400F,
            50,
            50F,
            50
    ),
    BUFFALO(
            AnimalKind.BUFFALO,
            new HashMap<>() ,
            "\uD83D\uDC03",
            3,
            700F,
            10,
            100F,
            50
    ),
    DUCK(
            AnimalKind.DUCK,
            new HashMap<>() {{
                put(AnimalKind.CATERPILLAR, 90);
            }},
            "\uD83E\uDD86",
            4,
            1F,
            200,
            0.15F,
            50
    ),
    CATERPILLAR(
            AnimalKind.CATERPILLAR,
            new HashMap<>(),
            "\uD83D\uDC1B",
            0,
            0.01F,
            1000,
            0F,
            50
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
        this.reproductionChance = reproductionChance;
        this.wastedSatietyPerStep = satietyLimit / 10; // hardcode
    }
}
