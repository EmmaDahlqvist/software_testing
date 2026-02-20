package petfeeder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Testing the FeedingScheduler class
 *
 */
public class FeedingSchedulerTest {

    private FeedingScheduler feedingScheduler;
    private PetFeeder petfeeder;
    MealPlan[] mealplans = new MealPlan[3];


    /**
     * Sets up the test environment by creating two meal plans and mocking the PetFeeder instance.
     */
    @BeforeEach
    public void setUp() throws Exception {
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
    }

    /**
     * Test scheduleRecurringFeeding with valid input works as intended
     *
     */
    @Test
    public void testScheduleRecurringFeeding_goodinput() throws InterruptedException {
        feedingScheduler = new FeedingScheduler(petfeeder);
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        feedingScheduler.scheduleRecurringFeeding(1, 1);

        Thread.sleep(1200);

        Mockito.verify(petfeeder, Mockito.atLeastOnce()).dispenseMeal(1);
        Mockito.verify(petfeeder, Mockito.atLeastOnce()).dispenseMeal(1);
    }

    /**
     * Testing schedulerRecurringFeeding with negative seconds as input
     */
    @Test
    public void scheduleRecurringFeeding_negativeSeconds(){
        feedingScheduler = new FeedingScheduler(petfeeder);
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> feedingScheduler.scheduleRecurringFeeding(0,-2));
    }

    /**
     * Testing schedulerRecurringFeeding with index input out of range
     */
    @Test
    public void scheduleRecurringFeeding_indexoutofrange(){
        feedingScheduler = new FeedingScheduler(petfeeder);
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> feedingScheduler.scheduleRecurringFeeding(4,-2));
    }

    /**
     * Testing scheduleRecurringFeeding when a mealplan does not exist (null)
     *
     */
    @Test
    public void scheduleRecurringFeeding_mealplanisnull() throws InterruptedException {
        feedingScheduler = new FeedingScheduler(petfeeder);

        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(false);

        feedingScheduler.scheduleRecurringFeeding(2,1);

        Thread.sleep(1200);

        Mockito.verify(petfeeder, Mockito.atLeastOnce()).dispenseMeal(2);
        Mockito.verify(petfeeder, Mockito.never()).getMealPlans();
    }

    /**
     * Testing stop to see that it works correctly. As a side effect,
     * this test simultaneously checks that hasActviveSchedule returns
     * true and false correctly.
     */
    @Test
    public void testStop(){
        feedingScheduler = new FeedingScheduler((petfeeder));
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        feedingScheduler.scheduleRecurringFeeding(0,100);

        assertTrue(feedingScheduler.hasActiveSchedule(), "Schedule is active before stop is called");

        feedingScheduler.stop();

        assertFalse(feedingScheduler.hasActiveSchedule(), "Schedule is not active after stop is called");
    }

    /**
     * Testing shutdown to see if it actually shuts down process.
     */
    @Test
    public void testShutdown() throws InterruptedException {
        feedingScheduler = new FeedingScheduler((petfeeder));
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        feedingScheduler.scheduleRecurringFeeding(0,100);

        feedingScheduler.shutdown();

        Thread.sleep(300);

        assertFalse(feedingScheduler.hasActiveSchedule(), "Schedule should not be active after shutdown is called");
    }

    /**
     * Testing that scheduleRecurringFeeding can be called twice and that the second call replaces the first schedule.
     * This test is mostly to get coverage on one line of code.
     */
    @Test
    public void scheduleRecurringFeeding_runtwice() throws InterruptedException {
        feedingScheduler = new FeedingScheduler(petfeeder);
        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

        feedingScheduler.scheduleRecurringFeeding(0,100);

        assertTrue(feedingScheduler.hasActiveSchedule(), "Schedule should be active");

        Thread.sleep(1000);

        feedingScheduler.scheduleRecurringFeeding(1,2);

        Thread.sleep(1000);


        assertTrue(feedingScheduler.hasActiveSchedule(), "Schedule should be active after mealplan switch");
    }




}
