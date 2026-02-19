package petfeeder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MealPlanBookTest {

    private MealPlanBook mealPlanBook;

    @BeforeEach
    public void setUp() throws Exception {
        mealPlanBook = new MealPlanBook();
    }

    @Test
    public void testGetMealPlans_Empty() {
        MealPlan[] mealPlans = mealPlanBook.getMealPlans();
        assertEquals(0, mealPlans.length, "The meal plan book should be empty initially");
    }

    @Test
    public void testGetMealPlans_OneMealPlan() {
        MealPlan mealPlan = Mockito.mock(MealPlan.class);
        mealPlanBook.addMealPlan(mealPlan);
        MealPlan[] mealPlans = mealPlanBook.getMealPlans();
        assertEquals(mealPlans[0], mealPlan, "The meal plan book should contain the added meal plan");
        assertEquals(1, mealPlans.length, "The meal plan book should only contain one meal plan");
    }

    @Test
    public void testAddMealPlan_OneGoodInput() {
        MealPlan mealPlan = Mockito.mock(MealPlan.class);
        boolean added = mealPlanBook.addMealPlan(mealPlan);

        MealPlan[] mealPlans = mealPlanBook.getMealPlans();
        assertTrue(added, "The meal plan should be added successfully");
        assertTrue(Arrays.asList(mealPlans).contains(mealPlan), "The added meal plan should be in the meal plan book");
    }

    @Test
    public void testAddMealPlan_MultipleGoodInput() {
        MealPlan[] addedMealPlans = new MealPlan[10];

        for(int i = 0; i < 10; i++) {
            MealPlan mealPlan = Mockito.mock(MealPlan.class);
            boolean added = mealPlanBook.addMealPlan(mealPlan);
            assertTrue(added, "Each meal plan should be added successfully, and return true");
            addedMealPlans[i] = mealPlan;
        }
        MealPlan[] actualMealPlans = mealPlanBook.getMealPlans();
        for(MealPlan mealPlan : addedMealPlans) {
            assertTrue(Arrays.asList(actualMealPlans).contains(mealPlan), "Each added meal plan should be in the meal plan book");
        }
    }

    @Test
    public void testAddMealPlan_NullInput() {
        boolean added = mealPlanBook.addMealPlan(null);
        assertFalse(added, "Adding a null meal plan should return false");
    }

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

    @Test
    public void testDeleteMealPlan_NonExisting() {
        String mealPlanName = mealPlanBook.deleteMealPlan(0);
        assertNull(mealPlanName, "Deleting a non-existing meal plan should return null");
    }

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

    @Test
    public void testEditMealPlan_GoodInput() {
        MealPlan mealPlan = Mockito.mock(MealPlan.class);
        Mockito.when(mealPlan.getName()).thenReturn("Meal1");

        MealPlan mealPlanToReplace = Mockito.mock(MealPlan.class);
        Mockito.when(mealPlanToReplace.getName()).thenReturn("Meal2");

        mealPlanBook.addMealPlan(mealPlan);
        String editedMealPlan = mealPlanBook.editMealPlan(0, mealPlanToReplace);
        MealPlan[] mealPlans = mealPlanBook.getMealPlans();
        assertEquals("Meal2", mealPlans[0].getName(), "The meal plan at index 0 should be replaced with the new meal plan");
        assertEquals("Meal1", editedMealPlan, "The name of the edited meal");
    }

    @Test
    public void testEditMealPlan_NonExisting() {
        MealPlan mealPlanToReplace = Mockito.mock(MealPlan.class);
        String editedMealPlan = mealPlanBook.editMealPlan(0, mealPlanToReplace);
        assertNull(editedMealPlan, "Editing a non-existing meal plan should return null");
    }

    @Test
    public void testEditMealPlan_NegativeIndex() {
        MealPlan mealPlanToReplace = Mockito.mock(MealPlan.class);
        String editedMealPlan = mealPlanBook.editMealPlan(-1, mealPlanToReplace);
        assertNull(editedMealPlan, "Editing a non-existing meal plan should return null");
    }

    @AfterEach
    public void tearDown() throws Exception {
        mealPlanBook = null;
    }
}
