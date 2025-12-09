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
        IceCream iceCream = new IceCream(limits, 6);


        //create an array for ice cream ingredients
        String[] ingredients = {"Vanilla", "Chocolate", "Strawberry", "Sprinkles", "Cherries", "Hot Fudge"};
        ArrayList<String> recipe = new ArrayList<>();
        char again; //for the repeating loop



        do {
            // display list of ingredients

             if (!iceCream.hasFlavor()) {
                System.out.println(printFlavorOptions(intToFlavor));
                System.out.print("Enter your choice:");
                int choice = input.nextInt();
                if (intToFlavor.containsKey(choice)) {
                    iceCream.addFlavor(intToFlavor.get(choice), 1);
                    System.out.println("Your Ice Cream is : " + iceCream.flavor);
                    System.out.println("Now choose an ingredient to add to your ice cream:" + printToppingAndSouceOptions(iceCream));
                    choice =  input.nextInt();
                } else {
                    System.out.println("Invalid Choice! Enter a number between 1 and " + intToFlavor.size());
                }
            } else {
                System.out.println("You have already selected a flavor: " + iceCream.flavor);
                 // display list of ingredients
                 System.out.println("Choose an ingredient");
                 for(int i = 0; i < ingredients.length; i++ ){
                     System.out.println((i+1) +"." +ingredients[i]);
                 }
                 //prompt user to make a choice
                 System.out.print("Enter your choice:");
                 int choice = input.nextInt();

                 //validate the user input
                 if(choice >= 1 && choice <= ingredients.length){
                     System.out.println("You chose " +ingredients[choice - 1]);
                     recipe.add(ingredients[choice]);
                 }
                 else{
                     System.out.println("Invalid Choice! Enter a number between 1 and " +ingredients.length);
                 }

                 //prompt user to continue

            }
            System.out.print("Add another ingredient? (Y/N): ");
            again = input.next().charAt(0);
            System.out.println();

        } while(again == 'y' || again == 'Y');

        //print recipe
        System.out.println("Ice Cream Recipe: ");

        //Make Loop to print recipe
        if (recipe.isEmpty()){
            System.out.println("No ingredients selected");
        }
        else{
            for(int i = 0; i < recipe.size(); i++){
                System.out.printf("%d. %s%n", i+1, recipe.get(i));
            }
        }


        input.close();
    }
    private static String printToppingAndSouceOptions(IceCream myIcream) {
        int totalToppings = myIcream.getCurrentToppingsCount();
        int totalScoops = myIcream.getCurrentFlaverCount();
        int maxToppings = myIcream.maxToppings;
        int availableToppings = maxToppings - totalToppings - totalScoops;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < totalToppings; i++){}
        sb.append("1 - SPRINKLES\n");
        sb.append("2 - CHERRIES\n");
        sb.append("3 - CRUSHED_OREOS\n");
        sb.append("1 - SPRINKLES\n");
        sb.append("2 - CHERRIES\n");
        sb.append("3 - CRUSHED_OREOS\n");
        return sb.toString();
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
    static class IceCream {
        private Flavor flavor;
        private Topping[] topping;
        private Map<Enum,Integer> limits;
        private Sauce sauce;
        private int maxToppings = 0;
        private int maxSause = 0;
        private int maxFlavor =0;
        private int currentToppings = 0;
        private int currentSause = 0;
        private int currentFlaverCount = 0;

        public IceCream(Map<Enum,Integer> inLimits, int inMaxToppings) {
            this.limits = inLimits;
            this.maxToppings = inMaxToppings;
            /*this.maxToppings = (int) limits.get(Topping.CHERRIES);
            this.maxSause = (int) limits.get(Sauce.HOT_FUDGE);
            this.maxFlavor = (int) limits.get(Flavor.VANILLA); */
        }
        public Topping[] getToppings() {
            return topping;
        }
        public int getCurrentFlaverCount() {
            return currentFlaverCount;
        }

        public boolean hasFlavor(){
            return this.flavor != null;
        }
        public int getCurrentToppingsCount(){return currentToppings + currentToppings + currentSause; }

        public void addTopping(Topping[] inTopping, int count) {
            if(count + getCurrentToppingsCount() > this.maxToppings) {
                System.out.println("Cannot add more than " + maxToppings + " toppings of " + inTopping);
                return;
            }
            else{
                this.topping = inTopping;
                this.currentToppings += count;
            }
        }
        public void addSause( Sauce inSause, int count) {
            if(count + getCurrentToppingsCount() > this.maxSause) {
                System.out.println("Cannot add more than " + maxSause + " sause of " + inSause);
                return;
            } else {
                this.sauce = inSause;
                this.currentSause = count;
            }
        }
        public void addFlavor(Flavor inFlavor, int count) {
            this.flavor = inFlavor;
            if(count + getCurrentToppingsCount() > this.maxFlavor) {

                System.out.println("Cannot add more than " + maxFlavor + " flavor of " + inFlavor);
                return;
            } else {
                this.flavor = inFlavor;
                this.currentFlaverCount = count;
            }
        }
    }

    public enum Topping { SPRINKLES, CHERRIES, CRUSHED_OREOS }
    public enum Sauce { HOT_FUDGE, CARAMEL_SAUCE }
    public enum ShortCuts {V,C,S,CH,SP,HF,CS,CO }
    public enum Flavor {VANILLA, CHOCOLATE, STRAWBERRY }
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
}