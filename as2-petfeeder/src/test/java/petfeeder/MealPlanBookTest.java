package petfeeder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the MealPlanBook class
 */
public class MealPlanBookTest {

    private MealPlanBook mealPlanBook;

    /**
     * Sets up the test environment by creating a new MealPlanBook instance before each test.
     */
    @BeforeEach
    public void setUp() {
        mealPlanBook = new MealPlanBook();
    }

    /**
     * Test that getMealPlans returns an empty array when the meal plan book is initially created.
     */
    @Test
    public void testGetMealPlans_Empty() {
        MealPlan[] mealPlans = mealPlanBook.getMealPlans();
        assertEquals(0, mealPlans.length, "The meal plan book should be empty initially");
    }

    /**
     * Test that getMealPlans returns an array containing the added meal plan when one meal plan is added to the meal plan book.
     */
    @Test
    public void testGetMealPlans_OneMealPlan() {
        MealPlan mealPlan = Mockito.mock(MealPlan.class); // Mock a MealPlan object

        mealPlanBook.addMealPlan(mealPlan);
        MealPlan[] mealPlans = mealPlanBook.getMealPlans();
        assertEquals(mealPlans[0], mealPlan, "The meal plan book should contain the added meal plan");
        assertEquals(1, mealPlans.length, "The meal plan book should only contain one meal plan");
    }

    /**
     * Test that getMealPlans returns an array containing the added meal plan when one meal plan is added to the meal plan book.
     */
    @Test
    public void testAddMealPlan_OneGoodInput() {
        MealPlan mealPlan = Mockito.mock(MealPlan.class);
        boolean added = mealPlanBook.addMealPlan(mealPlan);

        MealPlan[] mealPlans = mealPlanBook.getMealPlans();
        assertTrue(added, "The meal plan should be added successfully");
        assertTrue(Arrays.asList(mealPlans).contains(mealPlan), "The added meal plan should be in the meal plan book");
    }

    /**
     * Test that addMealPlan returns true when multiple valid
     * meal plans are added to the meal plan book, and that all added meal plans are
     * contained in the meal plan book.
     */
    @Test
    public void testAddMealPlan_MultipleGoodInput() {
        MealPlan[] addedMealPlans = new MealPlan[10];

        // Add 10 meal plans to the meal plan book
        for(int i = 0; i < 10; i++) {
            MealPlan mealPlan = Mockito.mock(MealPlan.class);
            boolean added = mealPlanBook.addMealPlan(mealPlan);

            // Verify that each meal plan is added successfully
            assertTrue(added, "Each meal plan should be added successfully, and return true");
            addedMealPlans[i] = mealPlan;
        }
        MealPlan[] actualMealPlans = mealPlanBook.getMealPlans();

        // Verify that all added meal plans are contained in the meal plan book
        for(MealPlan mealPlan : addedMealPlans) {
            assertTrue(Arrays.asList(actualMealPlans).contains(mealPlan), "Each added meal plan should be in the meal plan book");
        }
    }

    /**
     * Test that addMealPlan returns false when a null meal plan is added to the meal plan book.
     */
    @Test
    public void testAddMealPlan_NullInput() {
        boolean added = mealPlanBook.addMealPlan(null);
        assertFalse(added, "Adding a null meal plan should return false");
    }

    /**
     * Test that addMealPlan returns false when a meal plan that already exists in the meal plan book is added again.
     */
    @Test
    public void testAddMealPlan_AlreadyExists() {
        MealPlan mealPlan1 = Mockito.mock(MealPlan.class);
        boolean added = mealPlanBook.addMealPlan(mealPlan1);
        boolean addedAgain = mealPlanBook.addMealPlan(mealPlan1);
        assertTrue(added, "The first addition of the meal plan should return true");
        assertFalse(addedAgain, "Adding the same meal plan again should return false");
    }

    /**
     * Test that deleteMealPlan returns the name of the deleted meal plan and that the meal plan is removed
     * from the meal plan book when a valid index is provided.
     * The test also verifies that the meal plan book no longer contains the deleted meal plan after deletion.
     */
    @Test
    public void testDeleteMealPlan_GoodInput() {
        MealPlan mealPlan = Mockito.mock(MealPlan.class);
        Mockito.when(mealPlan.getName()).thenReturn("Meal1");
        mealPlanBook.addMealPlan(mealPlan);

        // Delete the first meal plan, index 0
        String mealPlanName = mealPlanBook.deleteMealPlan(0);
        MealPlan[] mealPlans = mealPlanBook.getMealPlans();
        assertEquals("Meal1", mealPlanName, "The deleted meal plan name should match the expected name");
        assertFalse(Arrays.asList(mealPlans).contains(mealPlan), "The meal plan book should no longer contain the deleted meal plan");
    }

    /**
     * Test that deleteMealPlan returns null when an invalid index is provided
     */
    @Test
    public void testDeleteMealPlan_NonExisting() {
        String mealPlanName = mealPlanBook.deleteMealPlan(0);
        assertNull(mealPlanName, "Deleting a non-existing meal plan should return null");
    }

    /**
     * Test that deleteMealPlan returns null a meal plan that has already been deleted is attempted to be deleted again
     */
    @Test
    public void testDeleteMealPlan_AlreadyDeleted() {
        MealPlan mealPlan = Mockito.mock(MealPlan.class);
        Mockito.when(mealPlan.getName()).thenReturn("Meal1");
        mealPlanBook.addMealPlan(mealPlan);

        // Delete the first meal plan, index 0
        mealPlanBook.deleteMealPlan(0);

        // Attempt to delete the same meal plan again
        String mealPlanName = mealPlanBook.deleteMealPlan(0);
        assertNull(mealPlanName, "Deleting an already deleted meal plan should return null");
    }

    /**
     * Test that editMealPlan returns the name of the edited meal plan and that the meal plan is replaced
     * in the meal plan book when a valid index is provided.
     */
    @Test
    public void testEditMealPlan_GoodInput() {
        // Meal plan to replace
        MealPlan mealPlan = new MealPlan();
        mealPlan.setName("Meal1");

        // New meal plan to replace the old one
        MealPlan mealPlanToReplace = new MealPlan();
        mealPlanToReplace.setName("Meal2");

        // Adding the first mealplan (index 0)
        mealPlanBook.addMealPlan(mealPlan);
        // Attempt to edit the first meal plan, index 0
        String editedMealPlan = mealPlanBook.editMealPlan(0, mealPlanToReplace);
        MealPlan[] mealPlans = mealPlanBook.getMealPlans();

        // Verify that it is replaced in the meal plan book and that the name of the edited meal plan is returned
        assertEquals("Meal2", mealPlans[0].getName(), "The meal plan at index 0 should be replaced with the new meal plan");
        assertEquals("Meal1", editedMealPlan, "The name of the edited meal");
    }

    /**
     * Test that editMealPlan returns null when an invalid index is provided
     */
    @Test
    public void testEditMealPlan_NonExisting() {
        MealPlan mealPlanToReplace = Mockito.mock(MealPlan.class);
        String editedMealPlan = mealPlanBook.editMealPlan(0, mealPlanToReplace);
        assertNull(editedMealPlan, "Editing a non-existing meal plan should return null");
    }

    /**
     * Test that editMealPlan returns null when a negative index is provided
     */
    @Test
    public void testEditMealPlan_NegativeIndex() {
        MealPlan mealPlanToReplace = Mockito.mock(MealPlan.class);
        String editedMealPlan = mealPlanBook.editMealPlan(-1, mealPlanToReplace);
        assertNull(editedMealPlan, "Editing a non-existing meal plan should return null");
    }

    /**
     * Tears down the test environment by setting the MealPlanBook instance to null after each test.
     */
    @AfterEach
    public void tearDown() {
        mealPlanBook = null;
    }
}
