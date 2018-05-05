package net.h3lv4ul7.recipeapp.services;

import java.util.Set;

import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.domain.Recipe;

public interface RecipeService{
	
	void deleteById(Long id);
	
	Recipe findRecipeById(Long id);

	RecipeCommand findCommandById(Long id);
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	
	Set<Recipe> getRecipes();
}
