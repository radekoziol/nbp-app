package com.app.model.currency;

import java.io.Serializable;

/**
 * This class describes actually gold
 */
public class Ore implements Serializable {


    /**
     * date
     */
    private String data;
    /**
     * price
     */
    private double cena;

    public double getCena() {
        return cena;
    }

}
