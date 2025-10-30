package com.icecream;

/**
 * Represents a sundae topping
 */
public class Topping {
    private String name;
    private double price;
    
    public Topping(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ")";
    }
}
