package net.h3lv4ul7.recipeapp.services;

import java.util.HashSet;
import java.util.Optional;
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

	@Override
	public Recipe getRecipeById(Long id) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(id);

		if (!recipeOptional.isPresent())
			throw new RuntimeException("Recipe not found");

		return recipeOptional.get();

	}

	@Override
	public Recipe getRecipeByDescription(String description) {

		Optional<Recipe> recipeOptional = recipeRepository.findByDescription(description);

		if (!recipeOptional.isPresent())
			throw new RuntimeException("Recipe not found");

		return recipeOptional.get();

	}
}
