package com.example.InventoryManagement.Models;

import com.example.InventoryManagement.Models.Data.StoreLocations;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Cody on 7/11/2017.
 */
@Entity
public class Location {
    @Id
    @GeneratedValue
    private int id;

    private StoreLocations side;

    private int row;
    private int section;

    @OneToMany
    @JoinColumn(name="box_id")
    private List<Box> boxes;

    public int getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public StoreLocations getSide() {
        return side;
    }

    public void setSide (String side) {
        this.side = StoreLocations.valueOf(side);
    }
    @Override
    public String toString(){
        return side.toString() + row + "-" + section;
    }
    @Override
    public boolean equals(Object object){
        if(!(object instanceof Location)){
            return false;
        }
        Location l = (Location)object;
        return Integer.compare(this.row, l.getRow()) == 0 && Integer.compare(this.section, l.getSection())== 0 &&
        this.side.equals(l.getSide());
    }
}
