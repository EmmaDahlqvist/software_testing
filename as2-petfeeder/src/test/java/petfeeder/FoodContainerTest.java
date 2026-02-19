package petfeeder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import petfeeder.exceptions.FoodStockException;

public class FoodContainerTest {

    private FoodContainer foodContainer;
    @BeforeEach
    public void setUp() throws Exception {
        foodContainer = new FoodContainer();
    }

    @Test
    public void testInitialKibbleAmount() {
        assertEquals(15, foodContainer.getKibble(), "Initial kibble should be 15");
    }

    @Test
    public void testInitialWaterAmount() {
        assertEquals(15, foodContainer.getWater(), "Initial water should be 15");
    }

    @Test
    public void testInitialWetFoodAmount() {
        assertEquals(15, foodContainer.getWetFood(), "Initial wet food should be 15");
    }

    @Test
    public void testInitialTreatsAmount() {
        assertEquals(15, foodContainer.getTreats(), "Initial treats should be 15");
    }

    @Test
    public void testAddWetFood_ValidInput() throws Exception{
        int wetFoodBefore = foodContainer.getWetFood();
        foodContainer.addWetFood("1");
        assertEquals(wetFoodBefore + 1,foodContainer.getWetFood(), "Wet food should increase by 1");
    }

    @Test
    public void testAddWetFood_NegativeInput() {
        assertThrows(FoodStockException.class, () -> foodContainer.addWetFood("-1"), "Adding negative wet food should throw FoodStockException");
    }

    @Test
    public void testAddWetFood_NonNumericInput(){
        assertThrows(FoodStockException.class, () -> foodContainer.addWetFood("one"), "Adding non-numeric wet food should throw FoodStockException");
    }

    @Test
    public void testAddTreats_ValidInput() throws Exception{
        int treatsBefore = foodContainer.getTreats();
        foodContainer.addTreats("1");
        assertEquals(treatsBefore + 1,foodContainer.getTreats(), "Treats should increase by 1");
    }

    @Test
    public void testAddTreats_NegativeInput() {
        assertThrows(FoodStockException.class, () -> foodContainer.addTreats("-1"), "Adding negative treats should throw FoodStockException");
    }

    @Test
    public void testAddTreats_NonNumericInput() {
        assertThrows(FoodStockException.class, () -> foodContainer.addTreats("one"), "Adding non-numeric treats should throw FoodStockException");
    }

    @Test
    public void testAddKibble_ValidInput() throws Exception{
        int kibbleBefore = foodContainer.getKibble();
        foodContainer.addKibble("1");
        assertEquals(kibbleBefore + 1,foodContainer.getKibble(), "Kibble should increase by 1");
    }

    @Test
    public void testAddKibble_NegativeInput() {
        assertThrows(FoodStockException.class, () -> foodContainer.addKibble("-1"), "Adding negative kibble should throw FoodStockException");
    }

    @Test
    public void testAddKibble_NonNumericInput() {
        assertThrows(FoodStockException.class, () -> foodContainer.addKibble("one"), "Adding non-numeric kibble should throw FoodStockException");
    }

    @Test
    public void testAddWater_ValidInput() throws Exception{
        int waterBefore = foodContainer.getWater();
        foodContainer.addWater("1");
        assertEquals(waterBefore + 1,foodContainer.getWater(), "Water should increase by 1");
    }

    @Test
    public void testAddWater_NegativeInput() {
        assertThrows(FoodStockException.class, () -> foodContainer.addWater("-1"), "Adding negative water should throw FoodStockException");
    }

    @Test
    public void testAddWater_NonNumericInput() {
        assertThrows(FoodStockException.class, () -> foodContainer.addWater("one"), "Adding non-numeric water should throw FoodStockException");
    }

    @Test
    public void testUseIngredients_ValidInput() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        org.mockito.Mockito.when(meal.getAmtKibble()).thenReturn(4);
        org.mockito.Mockito.when(meal.getAmtWater()).thenReturn((4));
        org.mockito.Mockito.when(meal.getAmtWetFood()).thenReturn(4);
        org.mockito.Mockito.when(meal.getAmtTreats()).thenReturn(4);

        int kibbleBefore = foodContainer.getKibble();
        int waterBefore = foodContainer.getWater();
        int wetFoodBefore = foodContainer.getWetFood();
        int treatsBefore = foodContainer.getTreats();
        boolean ingredientsUsed = foodContainer.useIngredients(meal);
        assertTrue(ingredientsUsed, "Should successfully use ingredients for the meal");
        assertEquals(waterBefore - 4, foodContainer.getWater(), "Water should decrease");
        assertEquals(wetFoodBefore - 4, foodContainer.getWetFood(), "Wet food should decrease");
        assertEquals(treatsBefore - 4, foodContainer.getTreats(), "Treats should decrease");
        assertEquals(kibbleBefore - 4, foodContainer.getKibble(), "Kibble should decrease");
    }

    @Test
    public void testUseIngredients_InsufficientStock() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        org.mockito.Mockito.when(meal.getAmtKibble()).thenReturn(16);
        org.mockito.Mockito.when(meal.getAmtWater()).thenReturn((16));
        org.mockito.Mockito.when(meal.getAmtWetFood()).thenReturn(16);
        org.mockito.Mockito.when(meal.getAmtTreats()).thenReturn(16);

        int kibbleBefore = foodContainer.getKibble();
        int waterBefore = foodContainer.getWater();
        int wetFoodBefore = foodContainer.getWetFood();
        int treatsBefore = foodContainer.getTreats();

        boolean ingredientsUsed = foodContainer.useIngredients(meal);
        assertFalse(ingredientsUsed, "Should not have enough ingredients to use for the meal");
        assertEquals(kibbleBefore, foodContainer.getKibble(), "Kibble should not decrease");
        assertEquals(waterBefore, foodContainer.getWater(), "Water should not decrease");
        assertEquals(wetFoodBefore, foodContainer.getWetFood(), "Wet food should not decrease");
        assertEquals(treatsBefore, foodContainer.getTreats(), "Treats should not decrease");
    }

    @Test
    public void testUseIngredients_InvalidMealPlan() {
        assertThrows(Exception.class, () -> {
            foodContainer.useIngredients(null);
        });
    }

    @Test
    public void testSetTreats_ValidInput() {
        foodContainer.setTreats(5);
        assertEquals(5, foodContainer.getTreats(), "Treats should be set to 5");
    }

    @Test
    public void testSetTreats_NegativeInput() {
        int treatsBefore = foodContainer.getTreats();
        foodContainer.setTreats(-1);
        assertEquals(treatsBefore, foodContainer.getTreats(), "Treats should not be changed when set to a negative value");
    }

    @Test
    public void testSetKibble_ValidInput() {
        foodContainer.setKibble(5);
        assertEquals(5, foodContainer.getKibble(), "Kibble should be set to 5");
    }

    @Test
    public void testSetKibble_NegativeInput() {
        int kibbleBefore = foodContainer.getKibble();
        foodContainer.setKibble(-1);
        assertEquals(kibbleBefore, foodContainer.getKibble(), "Kibble should not be changed when set to a negative value");
    }

    @Test
    public void testSetWater_ValidInput() {
        foodContainer.setWater(5);
        assertEquals(5, foodContainer.getWater(), "Water should be set to 5");
    }

    @Test
    public void testSetWater_NegativeInput() {
        int waterBefore = foodContainer.getWater();
        foodContainer.setWater(-1);
        assertEquals(waterBefore, foodContainer.getWater(), "Water should not be changed when set to a negative value");
    }

    @Test
    public void testSetWetFood_ValidInput() {
        foodContainer.setWetFood(5);
        assertEquals(5, foodContainer.getWetFood(), "Wet food should be set to 5");
    }

    @Test
    public void testSetWetFood_NegativeInput() {
        int wetFoodBefore = foodContainer.getWetFood();
        foodContainer.setWetFood(-1);
        assertEquals(wetFoodBefore, foodContainer.getWetFood(), "Wet food should not be changed when set to a negative value");
    }

    @Test
    public void testEnoughIngredients_NormalCase_ShouldBeTrue() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        org.mockito.Mockito.when(meal.getAmtKibble()).thenReturn(1);
        org.mockito.Mockito.when(meal.getAmtWater()).thenReturn((1));
        org.mockito.Mockito.when(meal.getAmtWetFood()).thenReturn(1);
        org.mockito.Mockito.when(meal.getAmtTreats()).thenReturn(1);

        assertTrue(foodContainer.enoughIngredients(meal), "Should have enough ingredients for the meal");
    }

    @Test
    public void testEnoughIngredients_EdgeCase_ShouldBeTrue() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        org.mockito.Mockito.when(meal.getAmtKibble()).thenReturn(15);
        org.mockito.Mockito.when(meal.getAmtWater()).thenReturn((15));
        org.mockito.Mockito.when(meal.getAmtWetFood()).thenReturn(15);
        org.mockito.Mockito.when(meal.getAmtTreats()).thenReturn(15);

        assertTrue(foodContainer.enoughIngredients(meal), "Should have enough ingredients for the meal");
    }

    @Test
    public void testEnoughIngredients_NotEnoughKibble_ShouldBeFalse() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        org.mockito.Mockito.when(meal.getAmtKibble()).thenReturn(16);
        org.mockito.Mockito.when(meal.getAmtWater()).thenReturn((1));
        org.mockito.Mockito.when(meal.getAmtWetFood()).thenReturn(1);
        org.mockito.Mockito.when(meal.getAmtTreats()).thenReturn(1);

        assertFalse(foodContainer.enoughIngredients(meal), "Should not have enough kibble for the meal");
    }

    @Test
    public void testEnoughIngredients_NotEnoughWater_ShouldBeFalse() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        org.mockito.Mockito.when(meal.getAmtKibble()).thenReturn(1);
        org.mockito.Mockito.when(meal.getAmtWater()).thenReturn((16));
        org.mockito.Mockito.when(meal.getAmtWetFood()).thenReturn(1);
        org.mockito.Mockito.when(meal.getAmtTreats()).thenReturn(1);

        assertFalse(foodContainer.enoughIngredients(meal), "Should not have enough water for the meal");
    }

    @Test
    public void testEnoughIngredients_NotEnoughWetFood_ShouldBeFalse() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        org.mockito.Mockito.when(meal.getAmtKibble()).thenReturn(1);
        org.mockito.Mockito.when(meal.getAmtWater()).thenReturn((1));
        org.mockito.Mockito.when(meal.getAmtWetFood()).thenReturn(16);
        org.mockito.Mockito.when(meal.getAmtTreats()).thenReturn(1);

        assertFalse(foodContainer.enoughIngredients(meal), "Should not have enough wet food for the meal");
    }

    @Test
    public void testEnoughIngredients_NotEnoughTreats_ShouldBeFalse() {
        MealPlan meal = Mockito.mock(MealPlan.class);
        org.mockito.Mockito.when(meal.getAmtKibble()).thenReturn(1);
        org.mockito.Mockito.when(meal.getAmtWater()).thenReturn((1));
        org.mockito.Mockito.when(meal.getAmtWetFood()).thenReturn(1);
        org.mockito.Mockito.when(meal.getAmtTreats()).thenReturn(16);

        assertFalse(foodContainer.enoughIngredients(meal), "Should not have enough wet food for the meal");
    }

    @Test
    public void testToString() {
        String expected = "Kibble: 15\nWater: 15\nWet Food: 15\nTreats: 15\n";
        assertEquals(expected, foodContainer.toString(), "toString should return the correct string representation of the food container");
    }

    @AfterEach
    public void tearDown() throws Exception {
        foodContainer = null;
    }
}
