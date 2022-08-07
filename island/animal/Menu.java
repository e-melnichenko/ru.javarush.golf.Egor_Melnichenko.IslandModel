package island.animal;

import java.util.HashMap;

public class Menu {
    private final HashMap<AnimalKind, Integer> map;

    public Menu(HashMap<AnimalKind, Integer> map) {
        this.map = map;
    }
}
