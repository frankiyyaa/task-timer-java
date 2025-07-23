package com.project.tasktimer.model;

public class Category extends AbstractEntity {

    private String name;
    private String color; // hex code or simple label

    public Category() {}

    public Category(String name, String color) {
        super();
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "[Category] " + name + " (" + color + ")";
    }
}
