package com.example.InventoryManagement.Models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Cody on 7/4/2017.
 */
@Entity
public class Box {
    public Box(){}
    @Id
    @GeneratedValue
    private int id;
    @ManyToMany
    private List<Item> items;
    @OneToMany
    @JoinColumn(name="box_id")
    private List<Quantity> quantities;

    @ManyToOne
    private Location location;

    public int getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void add(Item item){
        items.add(item);
    }
    public void remove(Item item){
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).equals(item)){
                items.remove(i);
            }
        }
    }
}
