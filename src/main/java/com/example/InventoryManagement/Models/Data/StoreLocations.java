package com.example.InventoryManagement.Models.Data;

/**
 * Created by Cody on 7/12/2017.
 */
public enum StoreLocations {
    RGT("RGT"),
    LFT("LFT"),
    RCV("RCV"),
    CEN("CEN"),
    FST("FST"),
    FRT("FRT");

    private final String side;

    private StoreLocations(String side){
        this.side = side;
    }
    }

