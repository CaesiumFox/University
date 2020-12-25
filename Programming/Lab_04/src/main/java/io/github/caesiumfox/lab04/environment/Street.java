package io.github.caesiumfox.lab04.environment;

import io.github.caesiumfox.lab04.PhysicalObject;
import io.github.caesiumfox.lab04.exceptions.EmptyNameException;

public class Street extends Way {
    public Street(String name) throws EmptyNameException {
        super(name, 4);
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
            Street street = (Street)obj;
            return (this.getName().equals(street.getName()) &&
                    this.getWidth() == street.getWidth());
        }
        return false;
    }
    @Override
    public String toString() {
        return "Street \"" + this.getName() + "\"";
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getName().hashCode();
        result = 31 * result + this.getWidth();
        return result;
    }
}
