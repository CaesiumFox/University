package io.github.caesiumfox.lab04.interfaces;

import io.github.caesiumfox.lab04.enums.Scent;

/**
 * Implemented by physical objects that
 * can be sniffed by a dog.
 */
public interface Sniffable {
    /**
     * Returns the scent of the object
     * @return
     * The scent
     */
    public Scent makeScent();
}
