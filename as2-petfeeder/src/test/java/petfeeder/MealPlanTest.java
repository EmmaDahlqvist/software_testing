package petfeeder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petfeeder.exceptions.MealPlanException;

public class MealPlanTest {

    private MealPlan mealPlan;

    @BeforeEach
    public void setUp() throws Exception {
        this.mealPlan = new MealPlan();
    }


    @Test // Test that it works with a valid input.
    public void testSetAmtTreats_goodInput() throws MealPlanException {
        mealPlan.setAmtTreats("5");
        assertEquals(5, mealPlan.getAmtTreats(), "setAmtTreats should update the amtTreats to 5 when input is '5'");
    }

    @Test // Test that an invalid input (non-numerical) does not generate an error.
    public void testSetAmtTreats_nonNumericInput() throws MealPlanException{
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtTreats("five"));
        assertEquals("Units of treats must be a positive integer", mealPlanException.getMessage(), "setAmtTreats should throw a MealPlanException when input is invalid");
    }

    @Test
    public void testSetAmtTreats_negativeInput() throws MealPlanException{
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtTreats("-1"));
        assertEquals("Units of treats must be a positive integer", mealPlanException.getMessage(), "setAmtTreats should throw a MealPlanException when input is negative");
    }


    @Test
    public void setAmtKibble_goodInput() throws MealPlanException {
        mealPlan.setAmtKibble("3");
        assertEquals(3, mealPlan.getAmtKibble(), "setAmtKibble should update the amtKibble to 3 when input is '3'");
    }

    @Test
    public void setAmtKibble_nonNumericInput() throws MealPlanException {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtKibble("three"));
        assertEquals("Units of kibble must be a positive integer", mealPlanException.getMessage(), "setAmtKibble should throw a MealPlanException when input is invalid");
    }

    @Test
    public void setAmtKibble_negativeInput() throws MealPlanException {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtKibble("-1"));
        assertEquals("Units of kibble must be a positive integer", mealPlanException.getMessage(), "setAmtKibble should throw a MealPlanException when input is negative");
    }

    @Test
    public void setAmtWater_goodInput() throws MealPlanException {
        mealPlan.setAmtWater("3");
        assertEquals(3, mealPlan.getAmtWater(), "setAmtWater should update the amtWater to 3 when input is '3'");
    }

    @Test
    public void setAmtWater_nonNumericInput() throws MealPlanException {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtWater("three"));
        assertEquals("Units of water must be a positive integer", mealPlanException.getMessage(), "setAmtWater should throw a MealPlanException when input is invalid");
    }

    @Test
    public void setAmtWater_negativeInput() throws MealPlanException {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtWater("-1"));
        assertEquals("Units of water must be a positive integer", mealPlanException.getMessage(), "setAmtWater should throw a MealPlanException when input is negative");
    }

    @Test
    public void setAmtWetFood_goodInput() throws MealPlanException {
        mealPlan.setAmtWetFood("3");
        assertEquals(3, mealPlan.getAmtWetFood(), "setAmtWetFood should update the amtWetFood to 3 when input is '3'");
    }

    @Test
    public void setAmtWetFood_nonNumericInput() throws MealPlanException {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtWetFood("three"));
        assertEquals("Units of wet food must be a positive integer", mealPlanException.getMessage(), "setAmtWetFood should throw a MealPlanException when input is invalid");
    }

    @Test
    public void setAmtWetFood_negativeInput() throws MealPlanException {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtWetFood("-1"));
        assertEquals("Units of wet food must be a positive integer", mealPlanException.getMessage(), "setAmtWetFood should throw a MealPlanException when input is negative");
    }


    @Test // Test that hash code of the same string in two different instances are the same
    public void testHashCode_same_goodInput() {
        MealPlan mealPlan2 = new MealPlan();
        mealPlan.setName(("Nice meal"));
        mealPlan2.setName("Nice meal");
        assertEquals(mealPlan2.getName().hashCode(),mealPlan.getName().hashCode(), "The hashcode from MealPlan should be consistent");
    }

    @Test // Test the energy is both calculated and updated correctly after using setAmt functions.
    public void testUpdateEnergyCost_goodInput() throws MealPlanException{
        mealPlan.setAmtKibble("4");
        mealPlan.setAmtWater("10");
        mealPlan.setAmtWetFood("0");
        mealPlan.setAmtTreats("1");
        // KIBBLE_ENERGY = 10;
        // WATER_ENERGY = 5;
        // WETFOOD_ENERGY = 15;
        // TREATS_ENERGY = 20;
        int expectedEnergyCost = 4 * 10 + 10 * 5 + 0 * 15 + 1 * 20;
        assertEquals(expectedEnergyCost, mealPlan.getEnergyCost(), "Energy cost should be updated and be the expected value");

        mealPlan.setAmtWater("0");
        expectedEnergyCost = 4 * 10 + 0 * 5 + 0 * 15 + 1 * 20;
        assertEquals(expectedEnergyCost,mealPlan.getEnergyCost(), "Energy cost is up to date after water amount change");
    }

    @Test
    public void testUpdateEnergyCost_nonNumeric_shouldNotChange() throws MealPlanException {
        int before = mealPlan.getEnergyCost();

        assertThrows(MealPlanException.class, () -> mealPlan.setAmtKibble("twenty"), "Setting non-numeric kibble should throw MealPlanException");
        assertEquals(before, mealPlan.getEnergyCost(), "Energy cost should not change after invalid kibble input");
    }


    @Test // Test that toString and getName return the same
    public void testToString_getName_same_goodInput(){
        mealPlan.setName("MyNiceMeal");
        assertEquals("MyNiceMeal", mealPlan.getName(), "getName should return 'MyNiceMeal'");
        assertEquals(mealPlan.getName(), mealPlan.toString(), "toString and getName should return 'MyNiceMeal'");
    }

    @Test
    public void testSetName_nullInput() {
        String nameBefore = mealPlan.getName();
        mealPlan.setName(null);
        assertEquals(nameBefore, mealPlan.getName(), "getName should return an empty string when name is set to null");
        assertEquals(nameBefore, mealPlan.toString(), "toString should return an empty string when name is set to null");
    }

    @Test
    public void testEquals_sameObject() {
        assertTrue(mealPlan.equals(mealPlan), "Should be equal to itself");
    }

    @Test
    public void testEquals_sameName_differentObject() {
        MealPlan mealPlan2 = new MealPlan();
        mealPlan.setName("Same Name");
        mealPlan2.setName("Same Name");
        assertTrue(mealPlan.equals(mealPlan2), "Different objects with the same name should be equal");
    }

    @Test
    public void testEquals_differentName() {
        MealPlan mealPlan2 = new MealPlan();
        mealPlan.setName("Meal One");
        mealPlan2.setName("Meal Two");
        assertFalse(mealPlan.equals(mealPlan2), "Different objects with different names should not be equal");
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.mealPlan = null;
    }





}
