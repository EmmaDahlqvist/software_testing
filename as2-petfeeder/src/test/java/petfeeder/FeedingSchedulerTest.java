package petfeeder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petfeeder.exceptions.MealPlanException;
import org.mockito.Mockito;

public class FeedingSchedulerTest {

    @BeforeEach
    public void setUp() throws Exception {
        PetFeeder petfeeder = Mockito.mock(PetFeeder.class);
        MealPlanBook mealPlanBook = Mockito.mock(PetFeeder.class);

        Mockito.when(petfeeder.dispenseMeal(Mockito.anyInt())).thenReturn(true);

    }

}
