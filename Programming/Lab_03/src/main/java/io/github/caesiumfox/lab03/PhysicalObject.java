package io.github.caesiumfox.lab03;

public abstract class PhysicalObject {
    protected String name;

    public PhysicalObject(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
