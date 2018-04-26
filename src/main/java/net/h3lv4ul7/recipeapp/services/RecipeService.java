package net.h3lv4ul7.recipeapp.services;

import java.util.Set;

import net.h3lv4ul7.recipeapp.domain.Recipe;

public interface RecipeService{
	
	Recipe getRecipeById(Long id);
	
	Recipe getRecipeByDescription(String description);
	
	Set<Recipe> getRecipes();

}
