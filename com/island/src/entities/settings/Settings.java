package com.island.src.entities.settings;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Settings {
    private static final Settings SETTINGS = new Settings();
    private static final String INIT_FILE = "com/island/src/init.yaml";

    public int getIslandWidth() {
        return islandWidth;
    }

    public int getIslandHeight() {
        return islandHeight;
    }

    private int islandWidth;
    private int islandHeight;
    private Settings() {
        URL source = Settings.class.getClassLoader().getResource(INIT_FILE);
        ObjectReader objectReader = new YAMLMapper().readerForUpdating(this);
        if(Objects.nonNull(source)) {
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
