package com.example.InventoryManagement.Models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Cody on 7/4/2017.
 */
@Entity
public class Box {
    @Id
    @GeneratedValue
    private int id;
    @ManyToMany
    private List<Item> items;


    public int getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void add(Item item){
        items.add(item);
    }
}
