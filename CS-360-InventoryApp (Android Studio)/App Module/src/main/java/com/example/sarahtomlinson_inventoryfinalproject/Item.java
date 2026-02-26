package com.example.sarahtomlinson_inventoryfinalproject;

public class Item {

    int id;
    String description;
    String quantity;

    public Item() {
        super();
    }

    public Item(int i, String description, String quantity) {
        super();
        this.id = i;
        this.description = description;
        this.quantity = quantity;
    }

    // constructor
    public Item(String description, String quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String qty) {
        this.quantity = qty;
    }
}