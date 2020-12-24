package io.github.caesiumfox.lab04.interfaces;

import io.github.caesiumfox.lab04.PhysicalObject;
import io.github.caesiumfox.lab04.environment.Street;

/**
 * Implemented by physical objets that
 * can move on ground.
 */
public interface MovingOnGround {
    /**
     * Prints in stdout that the object
     * goes to the destination
     * @param destination
     * The destination
     */
    public void go(PhysicalObject destination);

    /**
     * Prints in stdout that the object
     * crosses the street
     * @param street
     * The street
     */
    public void crossStreet(Street street);
}
