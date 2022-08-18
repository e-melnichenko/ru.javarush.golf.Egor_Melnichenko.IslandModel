package com.islandModel.src.entities.settings;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.islandModel.src.entities.animal.AnimalBase;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class Settings {
    private static final Settings SETTINGS = new Settings();
    private static final String INIT_FILE = "com/islandModel/src/init.yaml";
    private int islandWidth;
    private int islandHeight;
    private int corePoolSize;
    private int lifeCycleDuration;
    private float maxVegetationOnLocation;
    private float vegetationGrowPerStep;
    private boolean printStatsForDev;
    private String vegetationIcon;
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

    public float getMaxVegetationOnLocation() {
        return maxVegetationOnLocation;
    }

    public float getVegetationGrowPerStep() {
        return vegetationGrowPerStep;
    }

    public String getVegetationIcon() {
        return vegetationIcon;
    }

    public boolean isPrintStatsForDev() {
        return printStatsForDev;
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
