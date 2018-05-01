package net.h3lv4ul7.recipeapp.services;

import java.util.Set;

import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.domain.Recipe;

public interface RecipeService{
	
	Recipe getRecipeById(Long id);
	
	Recipe getRecipeByDescription(String description);
	
	Set<Recipe> getRecipes();
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);

	RecipeCommand findCommandById(Long id);
	
	RecipeCommand findCommandByDescription(String description);

	void deleteRecipeByDescription(String description);

	
}
