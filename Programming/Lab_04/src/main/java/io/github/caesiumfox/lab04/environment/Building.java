package io.github.caesiumfox.lab04.environment;

import io.github.caesiumfox.lab04.PhysicalObject;
import io.github.caesiumfox.lab04.exceptions.EmptyNameException;

public class Building extends PhysicalObject {
    public Building(String name) throws EmptyNameException {
        super(name);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(this.getClass() == obj.getClass()) {
            Building building = (Building)obj;
            return (this.getName().equals(building.getName()));
        }
        return false;
    }
    @Override
    public String toString() {
        return "House \"" + this.getName() + "\"";
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getName().hashCode();
        return result;
    }
}
