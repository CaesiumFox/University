package io.github.caesiumfox.lab04.interfaces;

import io.github.caesiumfox.lab04.PhysicalObject;
import io.github.caesiumfox.lab04.environment.Way;

public interface MovingOnGround {
    public void go(PhysicalObject destination);
    public void follow(PhysicalObject followee);
    public void crossStreet(Way way);
}
