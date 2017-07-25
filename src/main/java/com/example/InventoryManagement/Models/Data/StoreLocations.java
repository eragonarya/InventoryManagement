package com.example.InventoryManagement.Models.Data;

/**
 * Created by Cody on 7/12/2017.
 */
public enum StoreLocations {
    CEN("CEN"),
    FRT("FRT"),
    FST("FST"),
    LFT("LFT"),
    RCV("RCV"),
    RGT("RGT");

    private final String side;

    private StoreLocations(String side){
        this.side = side;
    }
    }

