package com.icecream;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a custom ice cream sundae
 */
public class Sundae {
    private List<IceCream> iceCreams;
    private List<Topping> toppings;
    
    public Sundae() {
        this.iceCreams = new ArrayList<>();
        this.toppings = new ArrayList<>();
    }
    
    public void addIceCream(IceCream iceCream) {
        iceCreams.add(iceCream);
    }
    
    public void addTopping(Topping topping) {
        toppings.add(topping);
    }
    
    public List<IceCream> getIceCreams() {
        return new ArrayList<>(iceCreams);
    }
    
    public List<Topping> getToppings() {
        return new ArrayList<>(toppings);
    }
    
    public double getTotalPrice() {
        double total = 0.0;
        for (IceCream ice : iceCreams) {
            total += ice.getPrice();
        }
        for (Topping topping : toppings) {
            total += topping.getPrice();
        }
        return total;
    }
    
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Your Custom Sundae ===\n");
        
        if (iceCreams.isEmpty()) {
            sb.append("No ice cream selected\n");
        } else {
            sb.append("\nIce Cream Flavors:\n");
            for (IceCream ice : iceCreams) {
                sb.append("  - ").append(ice.toString()).append("\n");
            }
        }
        
        if (toppings.isEmpty()) {
            sb.append("\nNo toppings selected\n");
        } else {
            sb.append("\nToppings:\n");
            for (Topping topping : toppings) {
                sb.append("  - ").append(topping.toString()).append("\n");
            }
        }
        
        sb.append("\nTotal Price: $").append(String.format("%.2f", getTotalPrice())).append("\n");
        sb.append("=========================\n");
        
        return sb.toString();
    }
}
