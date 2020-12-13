package io.github.caesiumfox.lab03.characters;

import io.github.caesiumfox.lab03.enums.Gender;
import io.github.caesiumfox.lab03.interfaces.Flying;

public class Karlsson extends Human implements Flying {
    public Karlsson() {
        super("Karlsson", Gender.Male);
    }

    public void fly() {
        System.out.println(this.toString() + " flies");
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
            Karlsson karlsson = (Karlsson)obj;
            return (this.getName().equals(karlsson.getName())
                    && this.getGender() == karlsson.getGender()
                    && this.getMood() == karlsson.getMood());
        }
        return false;
    }
    @Override
    public String toString() {
        return "Karlsson";
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getName().hashCode();
        result = 31 * result + this.getGender().hashCode();
        result = 31 * result + this.getMood().hashCode();
        return result;
    }
}
