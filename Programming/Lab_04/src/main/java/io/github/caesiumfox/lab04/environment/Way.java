package io.github.caesiumfox.lab04.environment;

import io.github.caesiumfox.lab04.PhysicalObject;
import io.github.caesiumfox.lab04.exceptions.EmptyNameException;

public class Way extends PhysicalObject {
    private int width;

    public Way(String name, int width) throws EmptyNameException {
        super(name);
        this.width = width;
    }
    public int getWidth() {
        return width;
    }
}
