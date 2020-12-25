package io.github.caesiumfox.lab04.environment;

import io.github.caesiumfox.lab04.exceptions.EmptyNameException;

public class RoadIntersection extends Street {
    public RoadIntersection(String name) throws EmptyNameException {
        super(name);
    }
    public String toString() {
        return "Road intersection \"" + this.getName() + "\"";
    }
}
