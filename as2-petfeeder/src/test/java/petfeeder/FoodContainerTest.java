package petfeeder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FoodContainerTest {
    @Test
    public void testInitialKibbleAmount() {
        FoodContainer container = new FoodContainer();
        assertEquals(15, container.getKibble(), "Initial kibble should be 15");
    }
    @Test
    public void testAddWetFood_Bug() {
        FoodContainer container = new FoodContainer();
        assertEquals(15, container.getWetFood(), "Initial wet food should be 15");
        Exception exception = assertThrows(Exception.class, () -> container.addWetFood("1"),
                "Adding positive wet food should throw due to bug");
        assertTrue(exception.getMessage().contains("wet food"), "Exception should mention wet food");
    }
    @Test
    public void testUseIngredients_KibbleBug() throws Exception{
        FoodContainer container = new FoodContainer();
        MealPlan meal = new MealPlan();
        meal.setAmtKibble("4");
        meal.setAmtWater("4");
        meal.setAmtWetFood("4");
        meal.setAmtTreats("4");
        int kibbleBefore = container.getKibble();
        int waterBefore = container.getWater();
        int wetFoodBefore = container.getWetFood();
        int treatsBefore = container.getTreats();
        container.useIngredients(meal);
        assertEquals(kibbleBefore + 4, container.getKibble(), "Kibble should have increased due to bug (should decrease)");
        assertEquals(waterBefore - 4, container.getWater(), "Water should decrease");
        assertEquals(wetFoodBefore - 4, container.getWetFood(), "Wet food should decrease");
        assertEquals(treatsBefore - 4, container.getTreats(), "Treats should decrease");


    }
}
