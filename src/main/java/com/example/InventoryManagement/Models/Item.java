package com.example.InventoryManagement.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Cody on 7/4/2017.
 */
@Entity
public class Item {
    public Item(){}
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    private String name;
    private int qty;
    @NotNull
    private double price;
    @ManyToMany(mappedBy = "items")
    private List<Box> boxes;
    @OneToMany
    @JoinColumn(name="item_id")
    private List<Quantity> quantities;

    public int getId() {
        return id;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void add(Box box){
        boxes.add(box);
    }

    @Override
    public String toString(){
        String out = "Item name: " + getName() + "\nItem ID: " + getId();
        return out;
    }
    @Override
    public boolean equals(Object object){
        if(!(object instanceof Item)){
            return false;
        }
        Item i = (Item)object;
        return Integer.compare(getId(), i.getId()) == 0;
    }
}
