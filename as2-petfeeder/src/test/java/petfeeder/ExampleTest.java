package petfeeder;

import petfeeder.exceptions.FoodStockException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * Example Unit tests for PetFeeder class.
 * Do not submit as your own!
 */
public class ExampleTest {
    
    private PetFeeder pf;
    private MealPlan p1;
    private MealPlan p2;

    @BeforeEach
    public void setUp() throws Exception {
        pf = new PetFeeder();
        
        //Set up for p1 (Basic Meal)
        p1 = new MealPlan();
        p1.setName("Morning Feast");
        p1.setAmtTreats("0");
        p1.setAmtKibble("3");
        p1.setAmtWater("1");
        p1.setAmtWetFood("1");
        
        //Set up for p2 (Snack Time)
        p2 = new MealPlan();
        p2.setName("Snack Time");
        p2.setAmtTreats("4");
        p2.setAmtKibble("0");
        p2.setAmtWater("1");
        p2.setAmtWetFood("1");
    }
    
    @Test
    public void testReplenishFood_Normal() {
        try {
            pf.replenishFood("4","7","0","9"); //Kibble, Water, WetFood, Treats
        } catch (FoodStockException e) {
            fail("FoodStockException should not be thrown");
        }
        String stock = pf.checkFoodStock();
        // Original logic: starts with 15 each.
        // Kibble: 15 + 4 = 19
        // Water: 15 + 7 = 22
        // Wet Food: 15 + 0 = 15
        // Treats: 15 + 9 = 24
        String expected = "Kibble: 19\nWater: 22\nWet Food: 15\nTreats: 24\n";
        assertEquals(expected, stock);
    }
    
    @Test
    public void testReplenishFoodException() {
        Throwable exception = assertThrows(
                FoodStockException.class, () -> {
                    pf.replenishFood("4", "-1", "asdf", "3"); // Should throw a FoodStockException
                }
                );
    }

    @Test
    public void testMealPlanEnergyCostCalculation() throws Exception {
        MealPlan meal = new MealPlan();
        meal.setAmtKibble("2"); // 2 * 10 = 20
        meal.setAmtWater("1");  // 1 * 5  = 5
        meal.setAmtWetFood("1"); // 1 * 15 = 15
        meal.setAmtTreats("0"); // 0 * 20 = 0

        // Expected energy cost: 20 + 5 + 15 + 0 = 40
        assertEquals(40, meal.getEnergyCost());
    }

    @Test
    public void testScheduledFeeding_ChangesStock() throws Exception {
        // Arrange: add a meal plan that can be dispensed
        pf.addMealPlan(p1);

        // Initial stock should be the default 15 units for each ingredient
        String initialStock = pf.checkFoodStock();

        // Act: schedule automatic feeding every 1 second and wait a bit
        FeedingScheduler scheduler = new FeedingScheduler(pf);
        scheduler.scheduleRecurringFeeding(0, 1L);

        // Wait long enough for at least one scheduled run
        Thread.sleep(1500L);
        scheduler.stop();

        String stockAfter = pf.checkFoodStock();

        // Assert: food stock has changed compared to the initial value
        assertNotEquals(initialStock, stockAfter);
    }

}