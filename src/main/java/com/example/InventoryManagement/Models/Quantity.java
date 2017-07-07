package com.example.InventoryManagement.Models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Cody on 7/7/2017.
 */
@Entity
public class Quantity {
    public Quantity(){}
    public Quantity(int qty, Box box, Item item){
        this.setQuantity(qty);
    }
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Box box;
    @ManyToOne
    private Item item;

    private int quantity;

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
