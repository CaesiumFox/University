package io.github.caesiumfox.lab03.environment;

import io.github.caesiumfox.lab03.PhysicalObject;

public class Street extends PhysicalObject {
    public Street(String name) {
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
            Street street = (Street)obj;
            return (this.getName().equals(street.getName()));
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
        return result;
    }
}
