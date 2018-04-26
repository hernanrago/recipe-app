package net.h3lv4ul7.recipeapp.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import net.h3lv4ul7.recipeapp.domain.Recipe;
import net.h3lv4ul7.recipeapp.repositories.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().forEach(recipes::add);
		return recipes;
	}
}
