package petfeeder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petfeeder.exceptions.MealPlanException;

/**
 * Testing the MealPlan class
 */
public class MealPlanTest {

    private MealPlan mealPlan;

    /**
     * Sets up the test environment by creating a new MealPlan instance before each test.
     */
    @BeforeEach
    public void setUp() {
        this.mealPlan = new MealPlan();
    }


    /**
     * Tests that setAmtTreats correctly updates the amtTreats when given valid input.
     */
    @Test
    public void testSetAmtTreats_goodInput() throws MealPlanException {
        mealPlan.setAmtTreats("5");
        assertEquals(5, mealPlan.getAmtTreats(), "setAmtTreats should update the amtTreats to 5 when input is '5'");
    }

    /**
     * Tests that setAmtTreats throws a MealPlanException when given non-numeric input.
     */
    @Test
    public void testSetAmtTreats_nonNumericInput(){
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtTreats("five"));
        assertEquals("Units of treats must be a positive integer", mealPlanException.getMessage(), "setAmtTreats should throw a MealPlanException when input is invalid");
    }

    /**
     * Tests that setAmtTreats throws a MealPlanException when given negative input.
     */
    @Test
    public void testSetAmtTreats_negativeInput() {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtTreats("-1"));
        assertEquals("Units of treats must be a positive integer", mealPlanException.getMessage(), "setAmtTreats should throw a MealPlanException when input is negative");
    }

    /**
     * Tests that setAmtKibble correctly updates the amtKibble when given valid input.
     */
    @Test
    public void setAmtKibble_goodInput() throws MealPlanException {
        mealPlan.setAmtKibble("3");
        assertEquals(3, mealPlan.getAmtKibble(), "setAmtKibble should update the amtKibble to 3 when input is '3'");
    }

    /**
     * Tests that setAmtKibble throws a MealPlanException when given non-numeric input.
     */
    @Test
    public void setAmtKibble_nonNumericInput()  {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtKibble("three"));
        assertEquals("Units of kibble must be a positive integer", mealPlanException.getMessage(), "setAmtKibble should throw a MealPlanException when input is invalid");
    }

    /**
     * Tests that setAmtKibble throws a MealPlanException when given negative input.
     */
    @Test
    public void setAmtKibble_negativeInput()  {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtKibble("-1"));
        assertEquals("Units of kibble must be a positive integer", mealPlanException.getMessage(), "setAmtKibble should throw a MealPlanException when input is negative");
    }

    /**
     * Tests that setAmtWater correctly updates the amtWater when given valid input.
     */
    @Test
    public void setAmtWater_goodInput() throws MealPlanException {
        mealPlan.setAmtWater("3");
        assertEquals(3, mealPlan.getAmtWater(), "setAmtWater should update the amtWater to 3 when input is '3'");
    }

    /**
     * Tests that setAmtWater throws a MealPlanException when given non-numeric input.
     */
    @Test
    public void setAmtWater_nonNumericInput()  {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtWater("three"));
        assertEquals("Units of water must be a positive integer", mealPlanException.getMessage(), "setAmtWater should throw a MealPlanException when input is invalid");
    }

    /**
     * Tests that setAmtWater throws a MealPlanException when given negative input.
     */
    @Test
    public void setAmtWater_negativeInput()  {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtWater("-1"));
        assertEquals("Units of water must be a positive integer", mealPlanException.getMessage(), "setAmtWater should throw a MealPlanException when input is negative");
    }

    /**
     * Tests that setAmtWetFood correctly updates the amtWetFood when given valid input.
     */
    @Test
    public void setAmtWetFood_goodInput() throws MealPlanException {
        mealPlan.setAmtWetFood("3");
        assertEquals(3, mealPlan.getAmtWetFood(), "setAmtWetFood should update the amtWetFood to 3 when input is '3'");
    }

    /**
     * Tests that setAmtWetFood throws a MealPlanException when given non-numeric input.
     */
    @Test
    public void setAmtWetFood_nonNumericInput() {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtWetFood("three"));
        assertEquals("Units of wet food must be a positive integer", mealPlanException.getMessage(), "setAmtWetFood should throw a MealPlanException when input is invalid");
    }

    /**
     * Tests that setAmtWetFood throws a MealPlanException when given negative input.
     */
    @Test
    public void setAmtWetFood_negativeInput()  {
        MealPlanException mealPlanException = assertThrows(MealPlanException.class, () -> mealPlan.setAmtWetFood("-1"));
        assertEquals("Units of wet food must be a positive integer", mealPlanException.getMessage(), "setAmtWetFood should throw a MealPlanException when input is negative");
    }

    /**
     * Tests that the hash code of the same string in two different MealPlan instances are the same, ensuring consistency of hash codes.
     */
    @Test
    public void testHashCode_same_goodInput() {
        MealPlan mealPlan2 = new MealPlan();
        mealPlan.setName(("Nice meal"));
        mealPlan2.setName("Nice meal");
        assertEquals(mealPlan2.hashCode(),mealPlan.hashCode(), "The hashcode from MealPlan should be consistent");
    }

    /**
     * Tests that the energy cost is updated correctly after using the setAmt functions with valid input.
     */
    @Test
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

    /**
     * Tests that the energy cost does not change when setAmtKibble is given non-numeric input,
     * ensuring that invalid input does not affect the state of the MealPlan.
     */
    @Test
    public void testUpdateEnergyCost_nonNumeric_shouldNotChange()  {
        int before = mealPlan.getEnergyCost();

        assertThrows(MealPlanException.class, () -> mealPlan.setAmtKibble("twenty"), "Setting non-numeric kibble should throw MealPlanException");
        assertEquals(before, mealPlan.getEnergyCost(), "Energy cost should not change after invalid kibble input");
    }

    /**
     * Tests that the toString and getName methods return the same string when a valid name is set, ensuring consistency between these two methods.
     */
    @Test
    public void testToString_getName_same_goodInput(){
        mealPlan.setName("MyNiceMeal");
        assertEquals("MyNiceMeal", mealPlan.getName(), "getName should return 'MyNiceMeal'");
        assertEquals(mealPlan.getName(), mealPlan.toString(), "toString and getName should return 'MyNiceMeal'");
    }

    /**
     * Tests that setName does not change the name when given null input, ensuring that the MealPlan's name remains unchanged in this case.
     */
    @Test
    public void testSetName_nullInput() {
        String nameBefore = mealPlan.getName();
        mealPlan.setName(null);
        assertEquals(nameBefore, mealPlan.getName(), "getName should return an empty string when name is set to null");
        assertEquals(nameBefore, mealPlan.toString(), "toString should return an empty string when name is set to null");
    }

    /**
     * Tests that the equals method returns true when comparing the same MealPlan object, ensuring that an object is equal to itself.
     */
    @Test
    public void testEquals_sameObject() {
        assertTrue(mealPlan.equals(mealPlan), "Should be equal to itself");
    }

    /**
     * Tests that the equals method returns true when comparing two different MealPlan objects with the same name,
     * ensuring that equality is based on the name of the meal plan rather than object identity.
     */
    @Test
    public void testEquals_sameName_differentObject() {
        MealPlan mealPlan2 = new MealPlan();
        mealPlan.setName("Same Name");
        mealPlan2.setName("Same Name");
        assertTrue(mealPlan.equals(mealPlan2), "Different objects with the same name should be equal");
    }

    /**
     * Tests that the equals method returns false when comparing two different MealPlan objects with different names,
     * ensuring that objects with different names are not considered equal.
     */
    @Test
    public void testEquals_differentName() {
        MealPlan mealPlan2 = new MealPlan();
        mealPlan.setName("Meal One");
        mealPlan2.setName("Meal Two");
        assertFalse(mealPlan.equals(mealPlan2), "Different objects with different names should not be equal");
    }

    @Test
    public void testEquals_nullObject() {
        mealPlan.setName("A Name");
        assertFalse(mealPlan.equals(null), "Null object should not be equal to a mealplan");
    }

    @Test
    public void testEquals_differentClass() {
        mealPlan.setName("A Name");
        int number = 31; // int class
        assertFalse(mealPlan.equals(number), "Object of different class should not be equal to a mealplan");
    }

    
    @Test
    public void testEquals_nameIsNull() {
        MealPlan mealplan2 = new MealPlan();
        mealplan2.setName("Not null");
        mealPlan.setName(null);

        assertFalse(mealPlan.equals(mealplan2), "One with null in name and one without should not be equal");
    }

    /**
     * Tears down the test environment by setting the MealPlan instance to null after each test, ensuring that each test starts with a fresh state.
     */
    @AfterEach
    public void tearDown() throws Exception {
        this.mealPlan = null;
    }





}
