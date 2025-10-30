package com.icecream;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Main application for building custom ice cream sundaes
 */
public class IceCreamBuilder {
    private static final List<IceCream> AVAILABLE_FLAVORS = new ArrayList<>();
    private static final List<Topping> AVAILABLE_TOPPINGS = new ArrayList<>();
    
    static {
        // Initialize available ice cream flavors
        AVAILABLE_FLAVORS.add(new IceCream("Vanilla", 2.50));
        AVAILABLE_FLAVORS.add(new IceCream("Chocolate", 2.50));
        AVAILABLE_FLAVORS.add(new IceCream("Strawberry", 2.50));
        AVAILABLE_FLAVORS.add(new IceCream("Mint Chip", 3.00));
        AVAILABLE_FLAVORS.add(new IceCream("Cookie Dough", 3.50));
        AVAILABLE_FLAVORS.add(new IceCream("Rocky Road", 3.50));
        
        // Initialize available toppings
        AVAILABLE_TOPPINGS.add(new Topping("Hot Fudge", 0.75));
        AVAILABLE_TOPPINGS.add(new Topping("Caramel", 0.75));
        AVAILABLE_TOPPINGS.add(new Topping("Whipped Cream", 0.50));
        AVAILABLE_TOPPINGS.add(new Topping("Sprinkles", 0.25));
        AVAILABLE_TOPPINGS.add(new Topping("Cherry", 0.25));
        AVAILABLE_TOPPINGS.add(new Topping("Nuts", 0.50));
        AVAILABLE_TOPPINGS.add(new Topping("Oreo Crumbles", 1.00));
        AVAILABLE_TOPPINGS.add(new Topping("Gummy Bears", 0.75));
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Sundae sundae = new Sundae();
        
        System.out.println("=================================");
        System.out.println("  Welcome to Ice Cream Builder!");
        System.out.println("=================================");
        System.out.println();
        
        // Select ice cream flavors
        boolean addingIceCream = true;
        while (addingIceCream) {
            displayIceCreamMenu();
            System.out.print("\nSelect ice cream flavor (enter number or 0 to finish): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                if (choice == 0) {
                    addingIceCream = false;
                } else if (choice > 0 && choice <= AVAILABLE_FLAVORS.size()) {
                    IceCream selected = AVAILABLE_FLAVORS.get(choice - 1);
                    sundae.addIceCream(selected);
                    System.out.println("Added: " + selected.getFlavor());
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume invalid input
            }
        }
        
        // Select toppings
        boolean addingToppings = true;
        while (addingToppings) {
            displayToppingsMenu();
            System.out.print("\nSelect topping (enter number or 0 to finish): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                if (choice == 0) {
                    addingToppings = false;
                } else if (choice > 0 && choice <= AVAILABLE_TOPPINGS.size()) {
                    Topping selected = AVAILABLE_TOPPINGS.get(choice - 1);
                    sundae.addTopping(selected);
                    System.out.println("Added: " + selected.getName());
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume invalid input
            }
        }
        
        // Display the final sundae
        System.out.println(sundae.getSummary());
        
        scanner.close();
    }
    
    private static void displayIceCreamMenu() {
        System.out.println("\n--- Available Ice Cream Flavors ---");
        for (int i = 0; i < AVAILABLE_FLAVORS.size(); i++) {
            System.out.println((i + 1) + ". " + AVAILABLE_FLAVORS.get(i).toString());
        }
        System.out.println("0. Done selecting ice cream");
    }
    
    private static void displayToppingsMenu() {
        System.out.println("\n--- Available Toppings ---");
        for (int i = 0; i < AVAILABLE_TOPPINGS.size(); i++) {
            System.out.println((i + 1) + ". " + AVAILABLE_TOPPINGS.get(i).toString());
        }
        System.out.println("0. Done selecting toppings");
    }
}
