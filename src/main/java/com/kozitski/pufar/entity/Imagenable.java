package com.kozitski.pufar.entity;

import java.awt.image.BufferedImage;

/**
 * The Interface Imagenable.
 * This interface must implement all entity classes
 * which have BufferedImage
 */
public interface Imagenable {

    /**
     * Gets the image.
     *
     * @return the image
     */
    BufferedImage getImage();

}
