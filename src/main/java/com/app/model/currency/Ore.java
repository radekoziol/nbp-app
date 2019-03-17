package com.app.model.currency;

import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;

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
    private double cena ;

    public double getCena() {
        return cena;
    }

}
