package petfeeder;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import petfeeder.exceptions.MealPlanException;



public class PetFeederTest {

    private PetFeeder petFeeder;

    @BeforeEach
    public void setUp() throws Exception {
        petFeeder = new PetFeeder();
    }

    @Test
    public void testGetMealPlans_Empty() {
        MealPlan[] mealPlans = petFeeder.getMealPlans();
        assertEquals(0, mealPlans.length, "The pet feeder should have no meal plans initially");
    }

    @Test
    public void testGetMealPlans() {
        PetFeeder pf = new PetFeeder();
        MealPlan[] plans = pf.getMealPlans();
        assertEquals(4, plans.length, "The pet feeder should have 4 meal plan slots");
    }
    @Test
    public void testGetMealPlansInital() {
        MealPlan[] mealPlans = petFeeder.getMealPlans();
        for (MealPlan mealPlan : mealPlans) {
            assertNull(mealPlan, "Each meal plan slot should be null initially");
        }
    }

    @Test
    public void testCanDispenseMeal() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        petFeeder.addMealPlan(meal);
        boolean dispensed = petFeeder.dispenseMeal(0);
        assertTrue(dispensed, "Meal should be dispensed");
    }
    @Test
    public void testCannotDispenseMeal_InvalidIndex() {
        MealPlan[] plans = petFeeder.getMealPlans();
        int outOfBoundsIndex = plans.length;
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> petFeeder.dispenseMeal(outOfBoundsIndex),
                "Dispensing with out-of-bounds index should throw ArrayIndexOutOfBoundsException");
    }

    @Test
    public void testDeleteMealPlan_GoodInput() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        Mockito.when(meal.getName()).thenReturn("Test Meal");
        petFeeder.addMealPlan(meal);

        String deletedName = petFeeder.deleteMealPlan(0);
        assertEquals("Test Meal", deletedName, "Deleted meal name should match");
        MealPlan[] after = petFeeder.getMealPlans();
        assertEquals("", after[0].getName(), "Meal name should be empty after deletion");
    }

    @Test
    public void testDeleteMealPlan_InvalidInput() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> petFeeder.deleteMealPlan(-1),
                "Deleting with negative index should throw ArrayIndexOutOfBoundsException");
    }


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

    @Test
    public void testReplenishFood_InvalidInput() {
        Exception exception = assertThrows(Exception.class, () -> petFeeder.replenishFood("bad", "3", "0", "4"),
                "Replenishing with invalid kibble input should throw an exception");
        assertTrue(exception.getMessage().contains("kibble"), "Exception message should mention kibble");
    }

    @Test
    public void something() throws MealPlanException {



        /*
        MealPlan meal = Mockito.mock(MealPlan.class);
        Mockito.when(meal.getAmtKibble()).thenReturn(15);
        Mockito.when(meal.getAmtTreats()).thenReturn(15);
        Mockito.when(meal.getAmtWater()).thenReturn(15);
        Mockito.when(meal.getAmtWetFood()).thenReturn(15);

        */
/*
        petFeeder.addMealPlan(meal);
        boolean dispensed = petFeeder.dispenseMeal(1);
        assertTrue(dispensed, "Meal should be dispensed when stock matches meal requirements exactly");

        String stock = petFeeder.checkFoodStock(); */
       // assertEquals(30, "Kibble should have increased due to bug (should decrease to 0)");
        //assertEquals(0, waterField.getInt(null), "Water should be 0 after dispensing exact meal");
        //assertEquals(0, wetFoodField.getInt(null), "Water should be 0 after dispensing exact meal");
        //assertEquals(0,treatsField.getInt(null), "Treats should be 0 after dispensing exact meal");

    }

    @Test
    public void testCheckFoodStock() {
        String stock = petFeeder.checkFoodStock();
        assertTrue(stock.contains("Kibble"), "Stock string should mention Kibble");
        assertTrue(stock.contains("Water"), "Stock string should mention Water");
        assertTrue(stock.contains("Wet Food"), "Stock string should mention Wet Food");
        assertTrue(stock.contains("Treats"), "Stock string should mention Treats");
    }

    @Test
    public void testCheckEnergyBudget_NoEnergySpent() {
        int energy = petFeeder.getRemainingEnergyBudget();
        assertEquals(500, energy, "Initial energy budget should be 500");
    }

    @Test
    public void testCheckEnergyBudget_AfterDispensing() throws MealPlanException {
        MealPlan meal = Mockito.mock(MealPlan.class);
        Mockito.when(meal.getEnergyCost()).thenReturn(50);
        petFeeder.addMealPlan(meal);
        petFeeder.dispenseMeal(0);
        int energy = petFeeder.getRemainingEnergyBudget();
        assertEquals(450, energy, "Energy budget should decrease by the meal's energy cost after dispensing");
    }

    @Test
    public void testAddMealPlan_ValidInput() {
        MealPlan mealPlan = Mockito.mock(MealPlan.class);
        boolean added = petFeeder.addMealPlan(mealPlan);
        assertTrue(added, "Adding a valid meal plan should return true");
        assertEquals(mealPlan, petFeeder.getMealPlans()[0], "The added meal plan should be retrievable from the pet feeder");
    }

    @Test
    public void testAddMealPlan_NullInput() {
        boolean added = petFeeder.addMealPlan(null);
        assertFalse(added, "Adding a null meal plan should return false");
    }

    @Test
    public void testEditMealPlan() throws MealPlanException {
        PetFeeder pf = new PetFeeder();
        MealPlan meal1 = new MealPlan();
        meal1.setName("Meal 1");
        meal1.setAmtWater("30");
        meal1.setAmtTreats("10");
        meal1.setAmtWetFood("40");
        meal1.setAmtKibble("20");
        pf.addMealPlan(meal1);

        MealPlan updated = new MealPlan();
        updated.setName("Updated Meal");
        updated.setAmtWater("99");
        updated.setAmtTreats("99");
        updated.setAmtWetFood("99");
        updated.setAmtKibble("99");

        String oldName = pf.editMealPlan(0, updated);
        assertEquals("Meal 1", oldName, "Should return the old meal name");
        MealPlan[] plans = pf.getMealPlans();
        assertEquals("", plans[0].getName(), "Meal plan name should be empty after edit");
        assertEquals(99, plans[0].getAmtKibble(), "Kibble amount should be updated");
    }
    @Test
    public void testEnergyLimit() {
        PetFeeder pf = new PetFeeder();
        int energy = pf.getRemainingEnergyBudget();
        assertEquals(500, energy, "Initial energy budget should be 500");
    }

    @AfterEach
    public void tearDown() throws Exception {
        petFeeder = null;
    }

}
