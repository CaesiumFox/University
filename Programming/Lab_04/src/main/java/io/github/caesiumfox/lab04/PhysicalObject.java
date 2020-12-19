package io.github.caesiumfox.lab04;

import io.github.caesiumfox.lab04.exceptions.EmptyNameException;

public abstract class PhysicalObject {
    protected String name;

    public PhysicalObject(String name) throws EmptyNameException {
        if(name.equals(""))
            throw new EmptyNameException(this);
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
