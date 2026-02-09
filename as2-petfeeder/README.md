# Smart Pet Feeder System

## System Overview
The Smart Pet Feeder is a control system designed to automate pet feeding. It manages an inventory of food ingredients and allows users to configure "Meal Plans" (recipes) to dispense food based on dietary needs and a simple energy model.

## System Functionality
The system provides the following core features via its API and Console Interface:
- Add/Edit/Delete Meal Plans: Configure feeding profiles (e.g., "Morning Feast") by specifying the required amount of each ingredient; the energy cost is then calculated automatically.
- Replenish Food: Add raw ingredients (Kibble, Water, Wet Food, Treats) to the internal food container.
- Check Stock: Query the current quantity of ingredients in the container.
- Dispense Meal: Select a meal plan. The system checks if there is sufficient stock and remaining energy budget before dispensing.
- Configure Scheduled Feeding: Set up a recurring schedule to automatically dispense a selected meal while the program is running.

## Ingredients and Energy Model

- **Ingredient units**: `kibble`, `water`, `wetFood`, `treats` are all stored and configured as non‑negative integer **units**.
- **Per-unit energy values**: Each ingredient contributes a fixed amount of energy points per unit (for example: kibble = 10, water = 5, wet food = 15, treats = 20).
- **Derived energy cost**: Each `MealPlan` automatically computes its `energyCost` as a weighted sum of its ingredient units and their per‑unit energy values. You can think of this as an approximate total meal energy (e.g., in kcal).
- **Energy limit (per program run)**: The system maintains a simple **total energy limit** (a fixed maximum amount of energy that can be dispensed while the program is running). Each successful meal increases an internal `energyConsumedSoFar` counter by that meal’s energy cost. If dispensing a meal would cause `energyConsumedSoFar` to exceed this limit, the meal is not dispensed, even if there is enough stock.
- **Insufficient stock or energy**:
  - For a single dispense (menu option 6), the system prints a message indicating insufficient ingredients or energy budget if the meal cannot be dispensed.
  - For scheduled feeding (menu option 7), the scheduler logs that the meal could not be dispensed and tries again at the next scheduled time; stock is not changed when a dispense fails.

## Code Structure

- `Main`: Console-based user interface that presents a menu, reads user input, and calls into the core system.
- `PetFeeder`: Main controller that coordinates meal plan management, food stock, and dispensing logic.
- `MealPlan`: Domain model for a single meal configuration, including ingredient quantities and a derived energy cost.
- `MealPlanBook`: Fixed-size collection of `MealPlan` objects; supports listing, adding, editing, and deleting plans.
- `FoodContainer`: Stores the current stock of each ingredient (kibble, water, wet food, treats) and checks/updates inventory when meals are dispensed.
- `FeedingScheduler`: Uses a background scheduler to trigger automatic, periodic calls to `dispenseMeal` for a chosen meal plan.
- `petfeeder.exceptions.*`: Custom exception types used to signal invalid user input or stock/meal configuration errors.

## Typical Usage (Console)

1. **Start the program**: Run the `Main` class; the console menu will appear.
2. **Configure meals**: Use options 1–3 to add, delete, or edit `MealPlan`s with ingredient amounts and energy cost.
3. **Replenish stock**: Use option 4 to add ingredient units into the `FoodContainer`.
4. **Inspect stock**: Use option 5 to print the current quantities of each ingredient.
5. **Dispense a meal**: Use option 6, choose a configured meal; the system will either:
   - Dispense the meal (printing its name), or
   - Reject the request if there is insufficient energy budget or ingredients.
6. **Configure scheduled feeding**: Use option 7 to choose a meal and set the interval (in seconds) between automatic feedings.
7. **Stop scheduled feeding**: Use option 8 to stop the current automatic feeding schedule.