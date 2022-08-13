package com.island.src.entities.animal;

public class AnimalAlreadyExistException extends RuntimeException {
    public AnimalAlreadyExistException() {
        super("Can't add animal: already exist on location");
    }
}
