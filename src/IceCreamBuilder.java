package src;

import java.util.*;

/**
 * author: James Crowder
 * date: 9/30/25
 * description: program that builds an ice cream sundae by allowing the user to select flavors, toppings,
 * and sauces while enforcing limits on each category.
 * filename: IceCreadBuilder.java
 */

public class  IceCreamBuilder {


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] errorMessages = new String[100];
        int currentErrorIndex = 0;
        Map<Integer, Flavor> intToFlavor = configureIntToFlavor();
        Map<Integer, Topping> intToTopping = configureIntToTopping();
        Map<Integer, Sauce> intToSauce = configureIntToSauce();
        Map<Enum, Object> shortCuts = configureShortCuts();
        Map<Enum, Integer> limits = configureLimits();
        Map<Enum, UnitType> unitTypes = configureUnitTypes();
        IceCream iceCream = new IceCream(limits, 6);
        Map<Integer, Commands> commandStack;


        //create an array for ice cream ingredients
        //String[] ingredients = {"Vanilla", "Chocolate", "Strawberry", "Sprinkles", "Cherries", "Hot Fudge"};
        //ArrayList<String> recipe = new ArrayList<>();
        char again; //for the repeating loop



        do {
             if (!iceCream.hasFlavor()) {
                System.out.println(printFlavorOptions(intToFlavor));
                System.out.print("Enter your choice:");
                int choice = getValidatedIntInput(input); //input.nextInt();
                if (intToFlavor.containsKey(choice)) {
                    iceCream.addFlavor(intToFlavor.get(choice), 1);
                    System.out.println(descibeIceCream(iceCream));
                    int maxToppings = iceCream.maxToppings();
                    int currentToppings = iceCream.getCurrentToppingsCount();
                    int availableToppings = maxToppings - currentToppings;
                    System.out.println("Now choose up to " + availableToppings + " items to add to your ice cream:");
                    Commands[] currentCommands= printToppingAndSauceOptions(iceCream);
                    displayCommandDescriptions(currentCommands);

                    choice =  getValidatedIntInput(input); // input.nextInt();
                    if(choice >= 1 && choice <= currentCommands.length){
                        Commands selectedCommand = currentCommands[choice - 1];
                        System.out.println("You chose " +selectedCommand.type + " x" + selectedCommand.count);
                        //add to ice cream
                        if(selectedCommand.type instanceof Topping){
                            iceCream.addTopping((Topping) selectedCommand.type, selectedCommand.count);
                            System.out.println(descibeIceCream(iceCream));
                        }
                        else if(selectedCommand.type instanceof Sauce){
                            iceCream.addSauce((Sauce) selectedCommand.type, selectedCommand.count);
                            System .out.println(selectedCommand.type + " added." + selectedCommand.count);
                            System.out.println(descibeIceCream(iceCream));
                        } else if(selectedCommand.type instanceof Flavor){
                            iceCream.setScoops(selectedCommand.count);
                            System.out.println(descibeIceCream(iceCream));
                        }
                    }
                    else{
                        System.out.println("Invalid Choice! Enter a number between 1 and " +currentCommands.length);
                    }
                } else {
                    System.out.println("Invalid Choice! Enter a number between 1 and " + intToFlavor.size());
                }
            } else {

                 System.out.println("Choose an ingredient");

                 System.out.print("Enter your choice:");

                 int maxToppings = iceCream.maxToppings();
                 int currentToppings = iceCream.getCurrentToppingsCount();
                 int availableToppings = maxToppings - currentToppings;
                 System.out.println("Now choose up to " + availableToppings + " items to add to your ice cream:");
                 Commands[] currentCommands= printToppingAndSauceOptions(iceCream);
                 displayCommandDescriptions(currentCommands);
                 int choice = getValidatedIntInput(input);

                 if(choice >= 1 && choice <= currentCommands.length){
                     Commands selectedCommand = currentCommands[choice - 1];
                     System.out.println("You chose " +selectedCommand.type + " x" + selectedCommand.count);
                     //add to ice cream
                     if(selectedCommand.type instanceof Topping){
                         iceCream.addTopping((Topping) selectedCommand.type, selectedCommand.count);
                         System.out.println(descibeIceCream(iceCream));
                     }
                     else if(selectedCommand.type instanceof Sauce){
                         iceCream.addSauce((Sauce) selectedCommand.type, selectedCommand.count);
                         System .out.println(selectedCommand.type + " added." + selectedCommand.count);
                         System.out.println(descibeIceCream(iceCream));
                     } else if(selectedCommand.type instanceof Flavor){
                         iceCream.setScoops(selectedCommand.count);
                         System.out.println(descibeIceCream(iceCream));
                     }
                 }
                 else{
                     System.out.println("Invalid Choice! Enter a number between 1 and " +currentCommands.length);
                 }

            }
             if (iceCream.getCurrentToppingsCount() >= iceCream.maxToppings) {
                 System.out.println("You have reached the maximum number of toppings/sauces for your ice cream.");
                    again = 'N';
             } else {
                 System.out.print("Add another ingredient? (Y/N): ");
                 again = input.next().charAt(0);
                 System.out.println();
             }
        } while(again == 'y' || again == 'Y');

        //print recipe
        System.out.println("Ice Cream Recipe: \n\n ");
        System.out.println(descibeIceCream(iceCream));



        input.close();
    }

    private static Commands[] printToppingAndSauceOptions(IceCream myIcecream) {
        int totalToppings = myIcecream.getCurrentToppingsCount();
        int totalScoops = myIcecream.getCurrentFlavorCount();
        int maxToppings = myIcecream.maxToppings;
        int availableToppings = maxToppings - totalToppings;
        ArrayList<Commands> commandList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int scoopLimit = maxToppings - myIcecream.getLimit(myIcecream.getFlavor());
        int availableScoopCount = Math.min(availableToppings, scoopLimit);
        int currentOptionToChoose = 1;
        int scoopCount = myIcecream.getCurrentFlavorCount();
        int scoupIncrement = scoopCount +1;

        //scoop options
        for(int i = scoopCount; i < availableScoopCount; i++){
            String currentDescription =  makeDicription(currentOptionToChoose,myIcecream.getFlavor() ,scoupIncrement);
            commandList.add(new Commands(i, myIcecream.getFlavor(), scoupIncrement, currentDescription));
            scoupIncrement++;
            currentOptionToChoose++;
        }
        //sauce options

        Commands[] souceCommands = availableSaucePrintOptions(myIcecream, currentOptionToChoose);
        currentOptionToChoose = currentOptionToChoose + souceCommands.length;
        commandList.addAll(Arrays.asList(souceCommands));

        //topping options
        Commands[] toppingCommands = availableTopicPrintOptions(myIcecream, currentOptionToChoose);
        currentOptionToChoose = currentOptionToChoose + toppingCommands.length;
        commandList.addAll(Arrays.asList(toppingCommands));

        return commandList.toArray(new Commands[0]);
    }

    private static String printFlavorOptions(Map<Integer,Flavor> intToFlavor) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Flavor> entry : intToFlavor.entrySet()) {
            sb.append(entry.getKey())
                    .append(" - ")
                    .append(entry.getValue().name())
                    .append("\n");
        }
        return sb.toString();
    }

   public static class IceCream {
        private Flavor flavor;
       // private Topping[] topping;
        private Map<Enum,Integer> limits;
        private Map<Enum,Integer> toppingCounts;
        private Sauce sauce;
        private int maxToppings = 0;
        private int maxSause = 0;
        private int maxFlavor =0;
        private int currentToppingsCount = 0;
        private int currentSauceCount = 0;
        private int currentFlavorCount = 0;

        public IceCream(Map<Enum,Integer> inLimits, int inMaxToppings) {
            this.limits = inLimits;
            this.maxToppings = inMaxToppings;
            this.toppingCounts = new HashMap<>();
        }

        public int getToppingCount(Enum key) {
            if (toppingCounts.containsKey(key)) {
                return toppingCounts.get(key);
            }
            return 0;
        }
        public int maxToppings() { return maxToppings;}
        public Flavor getFlavor() { return flavor;}
        public Sauce getSauce() { return sauce; }
        public int currentSauceCount() { return currentSauceCount;}

        public int getLimit(Enum key) {
            if (limits.containsKey(key)) {
                return (int) limits.get(key);
            } else { return -99; }
        }
        public Map<Enum,Integer>  getToppings() { return toppingCounts;}
        public int getCurrentFlavorCount() { return currentFlavorCount; }
        public boolean hasFlavor(){ return this.flavor != null; }
        public int getCurrentToppingsCount(){return currentToppingsCount + currentFlavorCount + currentSauceCount; } // TODO refactor name
       public int getOnlyToppingsCount(){ return currentToppingsCount;  }

        public void addTopping(Topping inTopping, int count) {
                this.toppingCounts.put(inTopping, count);
                this.currentToppingsCount = this.currentToppingsCount + count;

        }

        public void addSauce( Sauce inSauce, int count) {
                this.sauce = inSauce;
                this.currentSauceCount = count;
        }
        public void setScoops( int inCount) {
            this.currentFlavorCount = inCount;
        }
        public void addFlavor(Flavor inFlavor, int count) {
            this.flavor = inFlavor;
            this.currentFlavorCount = count;
            if(count + getCurrentFlavorCount() > this.limits.get(flavor)) {

                System.out.println("Cannot add more than " + maxFlavor + " flavor of " + inFlavor);
                this.currentFlavorCount -= count;
                this.flavor = null;
                return;
            }
        }
    }
    public static int getValidatedIntInput(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            scanner.next(); // consume invalid input
            return -99;
        }
    }

    public static String makeDicription(int inOptionNumber, Enum inType, int inCount) {
        if (inType instanceof Flavor) {
            return inOptionNumber + ") make it " + inCount + " scoop(s) of " + inType + " for your dish.";
        } else if( inType instanceof Sauce) {
            return inOptionNumber + ") add " + inCount + " tablespoon of " + inType + " to your dish.";
        } else if ( inType == Topping.CHERRIES) {
            return inOptionNumber + ") add " + inCount + " " + inType + " to your dish.";
        }
        return inOptionNumber + ") add " + inCount + " spoonful of " + inType + " to your order.";
    }
    public static Commands[] availableSaucePrintOptions(IceCream myIcecream, int inCurrentOptionToChoose) {
        ArrayList<Commands> availableSauceOptions = new ArrayList<>();
        int localOptionToChoose = inCurrentOptionToChoose;
        int currentSauceCount = myIcecream.currentSauceCount();
        int sauceIncrement =1;
        if( myIcecream.getSauce() != null) {

            int sauceLimit = myIcecream.getLimit(myIcecream.getSauce());
            int availableSauceCount = Math.min(myIcecream.maxToppings - myIcecream.getCurrentToppingsCount(), sauceLimit-currentSauceCount);

            for(int i = 0; i < availableSauceCount; i++) {
                String currentDescription =  makeDicription(localOptionToChoose,myIcecream.getSauce(),(currentSauceCount + sauceIncrement));
                localOptionToChoose++;
                availableSauceOptions.add( new Commands(localOptionToChoose, myIcecream.getSauce(), currentSauceCount + sauceIncrement,currentDescription));
                sauceIncrement++;
            }
        }
        else {
            for(Sauce sauceOption : Sauce.values()) {
                int sauceLimit = myIcecream.getLimit(sauceOption);
                currentSauceCount = myIcecream.currentSauceCount();
                int availableSauceCount = Math.min(myIcecream.maxToppings - myIcecream.getCurrentToppingsCount(), sauceLimit - currentSauceCount);

                for(int i = 0; i < availableSauceCount; i++) {
                    String currentDescription =  makeDicription(localOptionToChoose,sauceOption,(currentSauceCount + sauceIncrement));
                    localOptionToChoose++;
                    availableSauceOptions.add( new Commands(localOptionToChoose, sauceOption, currentSauceCount + sauceIncrement,currentDescription));
                    sauceIncrement++;
                }
            }
        }
        return availableSauceOptions.toArray(new Commands[0]);
    }
    public static Commands[] availableTopicPrintOptions(IceCream myIcecream, int inCurrentOptionToChoose) {
        ArrayList<Commands> availableToppingOptions = new ArrayList<>();
        int localOptionToChoose = inCurrentOptionToChoose;

        for(Topping toppingOption : Topping.values()) {
            int toppingLimit = myIcecream.getLimit(toppingOption);
            int availableToppingCount = Math.min(myIcecream.maxToppings - myIcecream.getCurrentToppingsCount(), toppingLimit - myIcecream.getOnlyToppingsCount());

            int currentToppingCount = myIcecream.getToppingCount(toppingOption);

            int toppingIncrement =1;
            for(int i = 0; i < availableToppingCount; i++) {

                String currentDescription =  makeDicription(localOptionToChoose,toppingOption,(currentToppingCount + toppingIncrement));
                localOptionToChoose++;
                availableToppingOptions.add( new Commands(localOptionToChoose, toppingOption, currentToppingCount + toppingIncrement,currentDescription));
                toppingIncrement++;
            }
        }
        return availableToppingOptions.toArray(new Commands[0]);
    }
    public static String descibeIceCream(IceCream iceCream) {
        StringBuilder description = new StringBuilder();
        description.append("Ice Cream Flavor: ").append(iceCream.getFlavor()).append(" with ");
        description.append(iceCream.getCurrentFlavorCount()).append(" Scoop(s): ").append(" ");
        if(iceCream.getSauce() != null ) {
            description.append("Sauce: ").append(iceCream.getSauce()).append(" (").append(iceCream.currentSauceCount()).append(") ");
        } else {
            description.append("Sauce: None ");
        }
        description.append("Toppings: ");
        if (iceCream.getToppings() != null) {

            for (Map.Entry<Enum, Integer> entry : iceCream.getToppings().entrySet()) {
                Topping key = (Topping) entry.getKey();
                int value = entry.getValue();
                description.append(key).append(" (").append(value).append(") ");
            }
        } else {
            description.append("None");
        }
        return description.toString();
    }
    public static void displayCommandDescriptions(Commands[] commands) {
        for (Commands command : commands) {
            System.out.println(command.description);
        }
    }
    public enum Topping { SPRINKLES, CRUSHED_OREOS, CHERRIES }
    public enum Sauce { HOT_FUDGE, CARAMEL_SAUCE }
    public enum ShortCuts {V,C,S,CH,SP,HF,CS,CO }
    public enum Flavor {VANILLA, CHOCOLATE, STRAWBERRY }
    public enum UnitType { SCOOP, TOPPING, SAUCE }
    public static Map configureUnitTypes() {
        Map unitTypeMap = new HashMap<>();
        unitTypeMap.put(Flavor.CHOCOLATE, UnitType.SCOOP);
        unitTypeMap.put(Flavor.VANILLA, UnitType.SCOOP);
        unitTypeMap.put(Flavor.STRAWBERRY, UnitType.SCOOP);
        unitTypeMap.put(Topping.CHERRIES, UnitType.TOPPING);
        unitTypeMap.put(Topping.SPRINKLES, UnitType.TOPPING);
        unitTypeMap.put(Topping.CRUSHED_OREOS, UnitType.TOPPING);
        unitTypeMap.put(Sauce.HOT_FUDGE, UnitType.SAUCE);
        unitTypeMap.put(Sauce.CARAMEL_SAUCE, UnitType.SAUCE);
        return unitTypeMap;
    }
    public static Map configureLimits() {
        Map limitsMap = new HashMap<>();
        limitsMap.put(Topping.CHERRIES, 5);
        limitsMap.put(Topping.SPRINKLES, 3);
        limitsMap.put(Topping.CRUSHED_OREOS, 3);
        limitsMap.put(Sauce.HOT_FUDGE, 2);
        limitsMap.put(Sauce.CARAMEL_SAUCE, 2);
        limitsMap.put(Flavor.CHOCOLATE, 3);
        limitsMap.put(Flavor.VANILLA, 3);
        limitsMap.put(Flavor.STRAWBERRY, 3);
        return limitsMap;
    }
    public static Map configureShortCuts() {
        // TODO not implemented
        Map shortCutsMap = new HashMap<>();
        shortCutsMap.put(ShortCuts.C, Flavor.CHOCOLATE);
        shortCutsMap.put(ShortCuts.V, Flavor.VANILLA);
        shortCutsMap.put(ShortCuts.S, Flavor.STRAWBERRY);
        shortCutsMap.put(ShortCuts.SP, Topping.SPRINKLES);
        shortCutsMap.put(ShortCuts.CH, Topping.CHERRIES);
        shortCutsMap.put(ShortCuts.HF, Sauce.HOT_FUDGE);
        shortCutsMap.put(ShortCuts.CS, Sauce.CARAMEL_SAUCE);
        shortCutsMap.put(ShortCuts.CO, Topping.CRUSHED_OREOS);
        return shortCutsMap;
    }
    public static Map configureIntToFlavor() {
        Map intToFlavorMap = new HashMap<>();
        intToFlavorMap.put(1, Flavor.VANILLA);
        intToFlavorMap.put(2, Flavor.CHOCOLATE);
        intToFlavorMap.put(3, Flavor.STRAWBERRY);
        return intToFlavorMap;
    }
    public static Map configureIntToTopping() {
        Map intToToppingMap = new HashMap<>();
        intToToppingMap.put(1, Topping.SPRINKLES);
        intToToppingMap.put(2, Topping.CHERRIES);
        intToToppingMap.put(3, Topping.CRUSHED_OREOS);
        return intToToppingMap;
    }
    public static Map configureIntToSauce() {
        Map intToSauseMap = new HashMap<>();
        intToSauseMap.put(1, Sauce.HOT_FUDGE);
        intToSauseMap.put(2, Sauce.CARAMEL_SAUCE);
        return intToSauseMap;
    }
     static class ComamandDecorator {
        Commands command;
        String description;
        ComamandDecorator(Commands inCommand, String inDescription) {
            this.command = inCommand;
            this.description = inDescription;
        }

         public Commands getCommand() {
             return command;
         }
            public String getDescription() {
                return description;
            }
     }
    public static class Commands {
        int id =0;
        Enum type;
        int count = 0;
        String description;
        Commands(int inID, Enum inType, int inCount, String inDescription) {
            this.id = inID;
            this.type = inType;
            this.count = inCount;
            this.description = inDescription;
        }
    }
}