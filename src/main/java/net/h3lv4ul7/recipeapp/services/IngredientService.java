package net.h3lv4ul7.recipeapp.services;

import net.h3lv4ul7.recipeapp.commands.IngredientCommand;

public interface IngredientService {
	
	 IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

	IngredientCommand saveIngredientCommand(IngredientCommand command);

	void deleteIngredientById(Long recipeId, Long ingredientId);
}
