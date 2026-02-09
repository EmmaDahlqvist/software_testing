# Smart Pet Feeder System 🐱🐶

## System Overview
The Smart Pet Feeder is a control system designed to automate pet feeding. It manages an inventory of food ingredients and allows users to configure "Meal Plans" (recipes) to dispense food based on dietary needs and an energy quota system.

## System Functionality
The system provides the following core features via its API and Console Interface:
- Add/Edit/Delete Meal Plans: Configure feeding profiles (e.g., "Morning Feast") by specifying the required amount of ingredients and the energy cost.
- Replenish Food: Add raw ingredients (Kibble, Water, Wet Food, Treats) to the internal food container.
- Check Stock: Query the current quantity of ingredients in the container.
- Dispense Meal: Select a meal plan and provide an energy quota. The system checks if there is sufficient stock and energy quota before dispensing.
