package petfeeder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests the FeedingScheduler class
 */
public class FeedingSchedulerTest {

    private FeedingScheduler feedingScheduler;
    private PetFeeder petfeeder;

    /**
     * Sets up the test environment by creating two meal plans and mocking the PetFeeder instance, and
     * creating a new FeedingScheduler instance with the mocked PetFeeder before each test.
     */
    @BeforeEach
    public void setUp() throws Exception {
        MealPlan[] mealplans = new MealPlan[3];

        // Create two meal plans with different ingredient amounts
        MealPlan meal1 = new MealPlan();
        meal1.setName("Meal 1");
        meal1.setAmtWater("30");
        meal1.setAmtTreats("10");
        meal1.setAmtWetFood("40");
        meal1.setAmtKibble("20");

        MealPlan meal2 = new MealPlan();
        meal2.setName("Meal 2");
        meal2.setAmtWater("20");
        meal2.setAmtTreats("5");
        meal2.setAmtWetFood("30");
        meal2.setAmtKibble("10");

        mealplans[0] = meal1;
        mealplans[1] = meal2;

        // MOCKING //
        petfeeder = Mockito.mock(PetFeeder.class);

        Mockito.when(petfeeder.getMealPlans()).thenReturn(mealplans);

        feedingScheduler = new FeedingScheduler(petfeeder);
    }

    /**
     * Test scheduleRecurringFeeding with valid input works as intended
     */
    @Test
    public void testScheduleRecurringFeeding_goodInput() throws InterruptedException {
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        feedingScheduler.scheduleRecurringFeeding(1, 1);

        Thread.sleep(1200);

        Mockito.verify(petfeeder, Mockito.atLeastOnce()).dispenseMeal(1);
        Mockito.verify(petfeeder, Mockito.atLeastOnce()).dispenseMeal(1);
    }

    /**
     * Tests schedulerRecurringFeeding with negative seconds as input
     */
    @Test
    public void scheduleRecurringFeeding_negativeSeconds(){
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> feedingScheduler.scheduleRecurringFeeding(0,-2));
    }

    /**
     * Tests schedulerRecurringFeeding with index input out of range
     */
    @Test
    public void scheduleRecurringFeeding_indexOutOfRange(){
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);


        assertThrows(IllegalArgumentException.class, () -> feedingScheduler.scheduleRecurringFeeding(4,-2));
    }

    /**
     * Tests scheduleRecurringFeeding when a mealplan does not exist (null)
     */
    @Test
    public void scheduleRecurringFeeding_mealPlanIsNull() throws InterruptedException {;
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(false);

        feedingScheduler.scheduleRecurringFeeding(2,1);

        Thread.sleep(1200);

        Mockito.verify(petfeeder, Mockito.atLeastOnce()).dispenseMeal(2);
        Mockito.verify(petfeeder, Mockito.never()).getMealPlans();
    }

    /**
     * Tests stop to see that it works correctly. As a side effect,
     * this test simultaneously checks that hasActviveSchedule returns
     * true and false correctly.
     */
    @Test
    public void testStop(){
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        feedingScheduler.scheduleRecurringFeeding(0,100);

        assertTrue(feedingScheduler.hasActiveSchedule(), "Schedule is active before stop is called");

        feedingScheduler.stop();

        assertFalse(feedingScheduler.hasActiveSchedule(), "Schedule is not active after stop is called");
    }

    /**
     * Tests shutdown to see if it actually shuts down process.
     */
    @Test
    public void testShutdown() throws InterruptedException {
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        feedingScheduler.scheduleRecurringFeeding(0,100);

        feedingScheduler.shutdown();

        Thread.sleep(300);

        assertFalse(feedingScheduler.hasActiveSchedule(), "Schedule should not be active after shutdown is called");
    }

    /**
     * Tests that scheduleRecurringFeeding can be called twice and that the second call replaces the first schedule.
     * This test is mostly to get coverage on one line of code.
     */
    @Test
    public void scheduleRecurringFeeding_runTwice() throws InterruptedException {
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        feedingScheduler.scheduleRecurringFeeding(0,100);

        assertTrue(feedingScheduler.hasActiveSchedule(), "Schedule should be active");

        Thread.sleep(1000);

        feedingScheduler.scheduleRecurringFeeding(1,2);

        Thread.sleep(1000);

        assertTrue(feedingScheduler.hasActiveSchedule(), "Schedule should be active after mealplan switch");
    }

    /**
     * Tests that scheduleRecurringFeeding hits the catch block when dispenseMeal throws an exception,
     * and that the error message is printed to the console.
     */
    @Test
    public void testScheduleRecurringFeeding_shouldHitCatch() {
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenThrow(new RuntimeException());

        // Redirect System.out to capture the output for verification
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Schedule a recurring feeding and wait a bit to allow the scheduled task to run and hit the catch block
        try {
            feedingScheduler.scheduleRecurringFeeding(0, 1);
            Thread.sleep(1200);

        } catch (InterruptedException e) {
            fail("Unexpected InterruptedException: " + e.getMessage());
        }

        // Get the captured output and verify that it contains the expected error message from the catch block
        String output = out.toString();
        assertTrue(output.contains("[Scheduler] Error during scheduled feeding"), "Output should contain error message from catch block");
    }

    /**
     * Tears down the test environment by setting the FeedingScheduler instance to null after each test.
     */
    @AfterEach
    public void tearDown() {
        feedingScheduler = null;
    }




}
