package petfeeder;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class PetFeederTest {

    private PetFeeder petFeeder;
    private MealPlan meal;

    @BeforeEach
    public void setUp() throws Exception {
        petFeeder = new PetFeeder();
        meal = new MealPlan();
        meal.setName("Test Meal");
        meal.setAmtKibble("1");
        meal.setAmtWater("1");
        meal.setAmtTreats("1");
        meal.setAmtWetFood("1");
        petFeeder.addMealPlan(meal);

    }

    @Test
    public void testCanDispenseMeal() {

        MealPlan[] before = petFeeder.getMealPlans();
        assertEquals("Test Meal", before[0].getName(), "Meal name should match");
        boolean dispensed = petFeeder.dispenseMeal(0);
        assertTrue(dispensed, "Meal should be dispensed");
    }

    @Test
    public void testDeleteMealPlan_GoodInput() {
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
    public void testRelenishFood() throws Exception {
        petFeeder.replenishFood("5", "3", "0", "4");
        String stock = petFeeder.checkFoodStock();
        assertTrue(stock.contains("Kibble"), "Stock string should mention Kibble");
        assertTrue(stock.contains("Water"), "Stock string should mention Water");
        assertTrue(stock.contains("Wet Food"), "Stock string should mention Wet Food");
        assertTrue(stock.contains("Treats"), "Stock string should mention Treats");
    }
}
