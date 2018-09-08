package com.lee.jdk8.collectors;

public class Dish {
    String name;
    boolean isFruit;
    int kaluli;

    enum Type {
        FISH,
        MEAT,
        FRUIT,
        OTHER
    }

    Type type;

    public Dish(String name, boolean isFruit, int kaluli, Type type) {
        this.name = name;
        this.isFruit = isFruit;
        this.kaluli = kaluli;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFruit() {
        return isFruit;
    }

    public void setFruit(boolean fruit) {
        isFruit = fruit;
    }

    public int getKaluli() {
        return kaluli;
    }

    public void setKaluli(int kaluli) {
        this.kaluli = kaluli;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", is=" + isFruit +
                ", kaluli=" + kaluli +
                ", type=" + type +
                '}';
    }
}
