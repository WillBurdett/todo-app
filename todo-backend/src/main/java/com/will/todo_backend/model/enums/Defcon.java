package com.will.todo_backend.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Defcon {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5");

    private final String level;

    Defcon(String level) {
        this.level = level;
    }

    @JsonValue
    public String getLevel() {
        return level;
    }

    @JsonCreator
    public static Defcon fromValue(String value) {
        for (Defcon d : values()) {
            if (d.level.equals(value)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Invalid DEFCON level: " + value);
    }
}
