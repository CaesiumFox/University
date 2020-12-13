package io.github.caesiumfox.lab03.interfaces;

import io.github.caesiumfox.lab03.PhysicalObject;
import io.github.caesiumfox.lab03.environment.Street;

public interface MovingOnGround {
    public void go(PhysicalObject destination);
    public void crossStreet(Street street);
}
