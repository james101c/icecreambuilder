import java.util.*;

public class IceCreamBuilder {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //create an array for ice cream ingredients
        String[] ingredients = {"Vanilla", "Chocolate", "Strawberry", "Sprinkles", "Cherries", "Hot Fudge"};
        ArrayList<String> recipe = new ArrayList<>();
        char again; //for the repeating loop
        
        do { 
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
}
