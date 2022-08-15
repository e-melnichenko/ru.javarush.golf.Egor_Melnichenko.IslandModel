package com.islanddd.src.entities.settings;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.islanddd.src.entities.animal.AnimalBase;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class Settings {
    private static final Settings SETTINGS = new Settings();
    private static final String INIT_FILE = "com/islanddd/src/init.yaml";

    private int islandWidth;
    private int islandHeight;
    private int corePoolSize;
    private int lifeCycleDuration;

    private Map<String, AnimalBase> animalProps;

    public int getIslandWidth() {
        return islandWidth;
    }

    public int getIslandHeight() {
        return islandHeight;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getLifeCycleDuration() {
        return lifeCycleDuration;
    }
    public Map<String, AnimalBase> getAnimalProps() {
        return animalProps;
    }

    private Settings() {
        URL source = Settings.class.getClassLoader().getResource(INIT_FILE);
        ObjectReader objectReader = new YAMLMapper().readerForUpdating(this);
        if (Objects.nonNull(source)) {
            try {
                objectReader.readValue(source.openStream());
            } catch (IOException e) {
                System.out.printf("Error reading settings file: %s", e);
            }
        }
    }

    public static Settings get() {
        return SETTINGS;
    }
}
