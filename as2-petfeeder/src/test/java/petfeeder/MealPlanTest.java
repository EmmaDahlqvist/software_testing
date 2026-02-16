package petfeeder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import petfeeder.exceptions.MealPlanException;

public class MealPlanTest {


    @Test // Test that it works with a valid input.
    public void testSetAmtTreats_goodInput() throws MealPlanException {
        MealPlan mealPlan = new MealPlan();
        mealPlan.setAmtTreats("5");
        assertEquals(5, mealPlan.getAmtTreats(), "setAmtTreats should return 5 when input is '5'");
    }

    @Test // Test that an invalid input (non-numerical) does not generate an error.
    public void testSetAmtTreats_invalidInput() throws MealPlanException{
        MealPlan mealPlan = new MealPlan();
        MealPlanException mealPlanExceptiontion = assertThrows(MealPlanException.class, () -> mealPlan.setAmtTreats("five"));

        assertEquals("Units of treats must be a positive integer", mealPlanExceptiontion.getMessage(), "setAmtTreats should throw a MealPlanException when input is invalid");
    }

    @Test // Test that hash code of the same string in two different instances are the same
    public void testHashCode_same_goodInput() {
        MealPlan mealPlan = new MealPlan();
        MealPlan mealPlan2 = new MealPlan();
        mealPlan.setName(("Nice meal"));
        mealPlan2.setName("Nice meal");
        assertEquals(mealPlan2.getName().hashCode(),mealPlan.getName().hashCode(), "The hashcode from MealPlan should be consistent");
    }

    @Test // Test the energy is both calculated and updated correctly after using setAmt functions.
    public void testUpdateEnergyCost_goodInput() throws MealPlanException{
        MealPlan mealPlan = new MealPlan();
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

    @Test // Test that toString and getName return the same
    public void test_toString_getName_same_goodInput(){
        MealPlan mealPlan = new MealPlan();
        mealPlan.setName("MyNiceMeal");
        assertEquals(mealPlan.getName(), mealPlan.toString(), "toString and getName should return 'MyNiceMeal'");
    }





}
