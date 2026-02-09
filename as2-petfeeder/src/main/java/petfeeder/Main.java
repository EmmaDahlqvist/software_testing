package petfeeder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import petfeeder.exceptions.FoodStockException;
import petfeeder.exceptions.MealPlanException;

/**
 * Starts the console UI for the PetFeeder.
 */
public class Main {
    private static PetFeeder petFeeder;
    private static FeedingScheduler feedingScheduler;

    /**
     * Prints the main menu and handles user input for 
     * main menu commands.
     */
    public static void mainMenu() {
        System.out.println("1. Add a meal plan");
        System.out.println("2. Delete a meal plan");
        System.out.println("3. Edit a meal plan");
        System.out.println("4. Replenish food");
        System.out.println("5. Check food stock");
        System.out.println("6. Dispense meal");
        System.out.println("7. Configure scheduled feeding");
        System.out.println("8. Stop scheduled feeding");
        System.out.println("0. Exit\n");
        
        //Get user input
        try {
            int userInput = Integer.parseInt(inputOutput("Please press the number that corresponds to what you would like the pet feeder to do."));
            
            if (userInput >= 0 && userInput <=8) {
                if (userInput == 1) addMealPlan();
                if (userInput == 2) deleteMealPlan();
                if (userInput == 3) editMealPlan();
                if (userInput == 4) replenishFood();
                if (userInput == 5) checkFoodStock();
                if (userInput == 6) dispenseMeal();
                if (userInput == 7) configureScheduledFeeding();
                if (userInput == 8) stopScheduledFeeding();
                if (userInput == 0) {
                    if (feedingScheduler != null) {
                        feedingScheduler.shutdown();
                    }
                    System.exit(0);
                }
            } else {
                System.out.println("Please enter a number from 0 - 6");
                mainMenu();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number from 0 - 6");
            mainMenu();
        }
    }
    
    /**
     * The add meal plan user interface that processes user input.
     */
    public static void addMealPlan() {
        
        //Read in meal plan name
        String name = inputOutput("\nPlease enter the meal plan name: ");
        
        //Read in amt kibble
        String kibbleString = inputOutput("\nPlease enter the units of kibble in the meal: ");
        
        //Read in amt water
        String waterString = inputOutput("\nPlease enter the units of water in the meal: ");
        
        //Read in amt wet food
        String wetFoodString = inputOutput("\nPlease enter the units of wet food in the meal: ");
        
        //Read in amt treats
        String treatsString = inputOutput("\nPlease enter the units of treats in the meal: ");
                
        MealPlan m = new MealPlan();
        try {
            m.setName(name);
            m.setAmtKibble(kibbleString);
            m.setAmtWater(waterString);
            m.setAmtWetFood(wetFoodString);
            m.setAmtTreats(treatsString);
            
            boolean added = petFeeder.addMealPlan(m);
            
            if(added) {
                System.out.println(name + " successfully added.\n");
            } else {
                System.out.println(name + " could not be added.\n");
            }
        } catch (MealPlanException e) {
            System.out.println(e.getMessage());
        } finally {
            mainMenu();
        }
    }
    
    /**
     * Delete meal plan user interface that processes input.
     */
    public static void deleteMealPlan() {
        MealPlan [] plans = petFeeder.getMealPlans();
        for(int i = 0; i < plans.length; i++) {
            if (plans[i] != null) {
                System.out.println((i+1) + ". " + plans[i].getName()
                        + " (energy cost: " + plans[i].getEnergyCost() + ")");
            }
        }
        int planToDelete = planListSelection("Please select the number of the meal plan to delete.");
        
        if(planToDelete < 0) {
            mainMenu();
        }
        
        String deleted = petFeeder.deleteMealPlan(planToDelete);
        
        if (deleted != null) {
            System.out.println(deleted + " successfully deleted.\n");
        } else {
            System.out.println("Selected meal plan doesn't exist and could not be deleted.\n");
        }
        mainMenu();
    }
    
    /**
     * Edit meal plan user interface that processes user input.
     */
    public static void editMealPlan() {
        MealPlan [] plans = petFeeder.getMealPlans();
        for(int i = 0; i < plans.length; i++) {
            if (plans[i] != null) {
                System.out.println((i+1) + ". " + plans[i].getName()
                        + " (energy cost: " + plans[i].getEnergyCost() + ")");
            }
        }
        int planToEdit = planListSelection("Please select the number of the meal plan to edit.");
        
        if(planToEdit < 0) {
            mainMenu();
        }
        
        //Read in amt kibble
        String kibbleString = inputOutput("\nPlease enter the units of kibble in the meal: ");
        
        //Read in amt water
        String waterString = inputOutput("\nPlease enter the units of water in the meal: ");
        
        //Read in amt wet food
        String wetFoodString = inputOutput("\nPlease enter the units of wet food in the meal: ");
        
        //Read in amt treats
        String treatsString = inputOutput("\nPlease enter the units of treats in the meal: ");
        
        MealPlan newPlan = new MealPlan();
        try {
            newPlan.setAmtKibble(kibbleString);
            newPlan.setAmtWater(waterString);
            newPlan.setAmtWetFood(wetFoodString);
            newPlan.setAmtTreats(treatsString);
            
            String edited = petFeeder.editMealPlan(planToEdit, newPlan);
            
            if (edited != null) {
                System.out.println(edited + " successfully edited.\n");
            }
            else {
                System.out.println(edited + "could not be edited.\n");
            }
        } catch (MealPlanException e) {
            System.out.println(e.getMessage());
        } finally {
            mainMenu();
        }
    }
    
    /**
     * Replenish food user interface that processes input.
     */
    public static void replenishFood() {
        //Read in amt kibble
        String kibbleString = inputOutput("\nPlease enter the units of kibble to add: ");
        
        //Read in amt water
        String waterString = inputOutput("\nPlease enter the units of water to add: ");
        
        //Read in amt wet food
        String wetFoodString = inputOutput("\nPlease enter the units of wet food to add: ");
        
        //Read in amt treats
        String treatsString = inputOutput("\nPlease enter the units of treats to add: ");
                
        try {
            petFeeder.replenishFood(kibbleString, waterString, wetFoodString, treatsString);
            System.out.println("Food stock successfully replenished");
        } catch (FoodStockException e) {
            System.out.println("Food stock was not replenished");
        } finally {
            mainMenu();
        }
    }
    
    /**
     * Check food stock user interface that processes input.
     */
    public static void checkFoodStock() {
        System.out.println(petFeeder.checkFoodStock());
        mainMenu();
    }
    
    /**
     * Dispense meal user interface that processes input.
     */
    public static void dispenseMeal() {
        MealPlan [] plans = petFeeder.getMealPlans();
        for(int i = 0; i < plans.length; i++) {
            if (plans[i] != null) {
                System.out.println((i+1) + ". " + plans[i].getName()
                        + " (energy cost: " + plans[i].getEnergyCost() + ")");
            }
        }
        
        int planToPurchase = planListSelection("Please select the number of the meal to dispense.");

        boolean dispensed = petFeeder.dispenseMeal(planToPurchase);

        if (dispensed) {
            System.out.println("Dispensing " + petFeeder.getMealPlans()[planToPurchase].getName());
            System.out.println("Remaining total energy budget: " + petFeeder.getRemainingEnergyBudget() + " energy points.\n");
        } else {
            System.out.println("Insufficient ingredients or energy budget to dispense.\n");
        }
        mainMenu();
    }

    /**
     * Configure a recurring scheduled feeding.
     */
    public static void configureScheduledFeeding() {
        MealPlan [] plans = petFeeder.getMealPlans();
        for(int i = 0; i < plans.length; i++) {
            if (plans[i] != null) {
                System.out.println((i+1) + ". " + plans[i].getName());
            }
        }

        int planToSchedule = planListSelection("Please select the number of the meal to schedule for automatic feeding.");

        if(planToSchedule < 0) {
            mainMenu();
        }

        String periodString = inputOutput("Please enter the interval in seconds between feedings");
        long periodSeconds = 0;
        try {
            periodSeconds = Long.parseLong(periodString);
            if (periodSeconds <= 0) {
                System.out.println("Please enter a positive integer for the interval");
                mainMenu();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a positive integer for the interval");
            mainMenu();
        }

        if (feedingScheduler == null) {
            feedingScheduler = new FeedingScheduler(petFeeder);
        }

        feedingScheduler.scheduleRecurringFeeding(planToSchedule, periodSeconds);
        System.out.println("Scheduled recurring feeding for " + plans[planToSchedule].getName() +
                           " every " + periodSeconds + " seconds.\n");
        mainMenu();
    }

    /**
     * Stops the current scheduled feeding, if any.
     */
    public static void stopScheduledFeeding() {
        if (feedingScheduler != null && feedingScheduler.hasActiveSchedule()) {
            feedingScheduler.stop();
            System.out.println("Scheduled feeding stopped.\n");
        } else {
            System.out.println("No active scheduled feeding.\n");
        }
        mainMenu();
    }
    
    /**
     * Passes a prompt to the user and returns the user specified 
     * string.
     * @param message The message prompt to display.
     * @return String The user input.
     */
    private static String inputOutput(String message) {
        System.out.println(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String returnString = "";
        try {
            returnString = br.readLine();
        }
        catch (IOException e){
            System.out.println("Error reading in value");
            mainMenu();
        }
        return returnString;
    }
    
    /**
     * Passes a prompt to the user that deals with the meal plan list
     * and returns the user selected number.
     * @param message The message prompt to display.
     * @return int The selected meal plan index.
     */
    private static int planListSelection(String message) {
        String userSelection = inputOutput(message);
        int plan = 0;
        try {
            plan = Integer.parseInt(userSelection) - 1;
            if (plan >= 0 && plan <=2) {
                //do nothing here.
            } else {
                plan = -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please select a number from 1-3.");
            plan = -1;
        }
        return plan;
    }
    
    /**
     * Starts the pet feeder program.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        petFeeder = new PetFeeder();
        feedingScheduler = new FeedingScheduler(petFeeder);
        System.out.println("Welcome to the PetFeeder!\n");
        System.out.println("Maximum total energy budget for this run: " + petFeeder.getEnergyLimit() + " energy points.\n");
        mainMenu();
    }
}
