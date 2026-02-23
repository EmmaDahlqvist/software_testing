package petfeeder;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import petfeeder.exceptions.MealPlanException;


/**
 * Tests the PetFeeder class
 */
public class PetFeederTest {

    private PetFeeder petFeeder;

    /**
     * Sets up the test environment by creating a new PetFeeder instance before each test.
     */
    @BeforeEach
    public void setUp() throws Exception {
        petFeeder = new PetFeeder();
    }
    

    /**
     * Tests when Meal Plans are empty, the getMealPlans method should return an empty array (length 0).
     */
    @Test
    public void testGetMealPlans_Empty() {
        MealPlan[] mealPlans = petFeeder.getMealPlans();
        assertEquals(0, mealPlans.length, "The pet feeder should have no meal plans initially");
    }

    /**
     * Tests that a meal plan can be dispensed when added to the pet feeder.
     */
    @Test
    public void testCanDispenseMeal() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        petFeeder.addMealPlan(meal);
        boolean dispensed = petFeeder.dispenseMeal(0);
        assertTrue(dispensed, "Meal should be dispensed");
    }

    /**
     * Tests that dispensing a meal with no meal plan in the specified slot returns false.
     */
    @Test
    public void testCannotDispenseMeal_NullMealPlan() {
        boolean dispensed = petFeeder.dispenseMeal(0);
        assertFalse(dispensed, "Dispensing with no meal plan should return false");
    }

    /**
     * Tests that dispensing a meal with not enough energy budget returns false.
     */
    @Test
    public void testDispenseMeal_NotEnoughEnergy() throws MealPlanException {
        MealPlan meal = Mockito.mock(MealPlan.class);
        Mockito.when(meal.getEnergyCost()).thenReturn(600); // Set energy cost higher than initial budget
        petFeeder.addMealPlan(meal);
        boolean dispensed = petFeeder.dispenseMeal(0);
        assertFalse(dispensed, "Dispensing a meal that exceeds energy budget should return false");
    }

    /**
     * Tests that dispensing a meal with not enough Ingredients in stock returns false.
     */
    @Test
    public void testDispenseMeal_NotEnoughIngredients() throws MealPlanException {
        MealPlan meal = Mockito.mock(MealPlan.class);
        Mockito.when(meal.getEnergyCost()).thenReturn(50); // Set energy cost within budget
        Mockito.when(meal.getAmtKibble()).thenReturn(1000); // Set ingredient amount higher than initial stock
        petFeeder.addMealPlan(meal);
        boolean dispensed = petFeeder.dispenseMeal(0);
        assertFalse(dispensed, "Dispensing a meal that exceeds ingredient stock should return false");

    }

    /**
     * Tests that dispensing a meal with an invalid index throws an ArrayIndexOutOfBoundsException.
     */
    @Test
    public void testCannotDispenseMeal_InvalidIndex() {
        MealPlan[] plans = petFeeder.getMealPlans();
        int outOfBoundsIndex = plans.length;
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> petFeeder.dispenseMeal(outOfBoundsIndex),
                "Dispensing with out-of-bounds index should throw ArrayIndexOutOfBoundsException");
    }

    /**
     * Test that deleting a meal plan with a valid index returns the name of the deleted meal plan and that the meal plan slot is set to null after deletion.
     */
    @Test
    public void testDeleteMealPlan_GoodInput() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        Mockito.when(meal.getName()).thenReturn("Test Meal");
        petFeeder.addMealPlan(meal);

        String deletedName = petFeeder.deleteMealPlan(0);
        assertEquals("Test Meal", deletedName, "Deleted meal name should match");
        MealPlan[] after = petFeeder.getMealPlans();
        assertNull(after[0], "Meal name should be null after deletion");
    }

    /**
     * Tests that deleting a meal plan with an invalid index throws an ArrayIndexOutOfBoundsException.
     */
    @Test
    public void testDeleteMealPlan_InvalidInput() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> petFeeder.deleteMealPlan(-1),
                "Deleting with negative index should throw ArrayIndexOutOfBoundsException");
    }

    /**
     * Tests that replenishing food updates the stock and that the stock string mentions all food types (ingredients).
     *
     */
    @Test
    public void testReplenishFood() throws Exception {
        String initialStock = petFeeder.checkFoodStock();
        String[] v1 = initialStock.replaceAll("[^0-9\n]", "").split("\\n");
        int initialKibble = Integer.parseInt(v1[0]);
        int initialWater = Integer.parseInt(v1[1]);
        int initialWetFood = Integer.parseInt(v1[2]);
        int initialTreats = Integer.parseInt(v1[3]);

        petFeeder.replenishFood("5", "3", "0", "4");
        String stock = petFeeder.checkFoodStock();
        String[] v2 = stock.replaceAll("[^0-9\n]", "").split("\\n");
        int kibble = Integer.parseInt(v2[0]);
        int water = Integer.parseInt(v2[1]);
        int wetFood = Integer.parseInt(v2[2]);
        int treats = Integer.parseInt(v2[3]);

        assertEquals(initialKibble + 5, kibble, "Stock should update the kibble amount");
        assertEquals(initialWater + 3, water, "Stock should update the water amount");
        assertEquals(initialWetFood + 0 , wetFood, "Stock should update the wet food amount");
        assertEquals(initialTreats + 4, treats, "Stock should update the treats amount");
    }

    /**
     * Tests that replenishing food with invalid input (non-numeric or negative) throws an exception and that the exception message mentions the type of food that caused the error.
     */
    @Test
    public void testReplenishFood_InvalidInput() {
        Exception exception = assertThrows(Exception.class, () -> petFeeder.replenishFood("bad", "3", "0", "4"),
                "Replenishing with invalid kibble input should throw an exception");
        assertTrue(exception.getMessage().contains("kibble"), "Exception message should mention kibble");
    }

    /**
     * Tests that all the ingredients are contained in the stock string returned by checkFoodStock().
     */
    @Test
    public void testCheckFoodStock() {
        String stock = petFeeder.checkFoodStock();
        assertTrue(stock.contains("Kibble"), "Stock string should mention Kibble");
        assertTrue(stock.contains("Water"), "Stock string should mention Water");
        assertTrue(stock.contains("Wet Food"), "Stock string should mention Wet Food");
        assertTrue(stock.contains("Treats"), "Stock string should mention Treats");
    }

    /**
     * Tests that the remaining energy budget is equal to the initial budget when no meals have been dispensed.
     */
    @Test
    public void testCheckEnergyBudget_NoEnergySpent() {
        int energy = petFeeder.getRemainingEnergyBudget();
        assertEquals(500, energy, "Initial energy budget should be 500");
    }

    /**
     * Tests that after a meal has been dispensed, the remaining energy budget decreases by the energy cost of that meal.
     */
    @Test
    public void testCheckEnergyBudget_AfterDispensing() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        Mockito.when(meal.getEnergyCost()).thenReturn(50);
        petFeeder.addMealPlan(meal);
        petFeeder.dispenseMeal(0);
        int energy = petFeeder.getRemainingEnergyBudget();
        assertEquals(450, energy, "Energy budget should decrease by the meal's energy cost after dispensing");
    }

    /**
     * Tests that adding a valid meal plan correctly returns true and that the meal plan can be retrieved from the pet feeder.
     */
    @Test
    public void testAddMealPlan_ValidInput() {
        MealPlan mealPlan = Mockito.mock(MealPlan.class);
        boolean added = petFeeder.addMealPlan(mealPlan);
        assertTrue(added, "Adding a valid meal plan should return true");
        assertEquals(mealPlan, petFeeder.getMealPlans()[0], "The added meal plan should be retrievable from the pet feeder");
    }

    /**
     * Tests that adding an invalid meal plan (null) returns false and does not add anything to the pet feeder.
     */
    @Test
    public void testAddMealPlan_NullInput() {
        boolean added = petFeeder.addMealPlan(null);
        assertFalse(added, "Adding a null meal plan should return false");
    }

    /**
     * Tests that the petFeeders edit meal plan method correctly updates/replaces the meal plan at the specified index and returns the old meal name.
     */
    @Test
    public void testEditMealPlan_GoodInput() {
        PetFeeder pf = new PetFeeder();
        MealPlan meal1 = new MealPlan();
        meal1.setName("Meal 1");
        pf.addMealPlan(meal1);

        MealPlan updated = new MealPlan();
        updated.setName("Updated Meal");


        String oldName = pf.editMealPlan(0, updated);
        assertEquals("Meal 1", oldName, "Should return the old meal name");
        MealPlan[] plans = pf.getMealPlans();
        assertEquals(updated, plans[0], "Meal plan at index 0 should be updated to the new meal plan");
    }

    /**
     * Tests that the pet feeder's energy budget is correctly initialized to 500 when the pet feeder is created.
     */
    @Test
    public void testInitialEnergyLimit() {
        PetFeeder pf = new PetFeeder();
        int energy = pf.getEnergyLimit();
        assertEquals(500, energy, "Initial energy budget should be 500");
    }

    /**
     * Tests that the energy limit returned by getEnergyLimit() is a positive integer, indicating that the pet feeder has a valid energy budget.
     */
    @Test
    public void testGetEnergyLimit_ShouldBePositive(){
        PetFeeder pf = new PetFeeder();
        int energyLimit = pf.getEnergyLimit();
        assertTrue(energyLimit > 0, "Energy limit should be a positive integer");
    }


    @AfterEach
    public void tearDown() throws Exception {
        petFeeder = null;
    }

}
