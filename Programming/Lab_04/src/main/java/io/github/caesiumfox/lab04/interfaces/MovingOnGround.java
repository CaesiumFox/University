package io.github.caesiumfox.lab04.interfaces;

import io.github.caesiumfox.lab04.PhysicalObject;

public interface MovingOnGround {
    public void goTo(PhysicalObject destination);
    public void cross(PhysicalObject longObject);
}
