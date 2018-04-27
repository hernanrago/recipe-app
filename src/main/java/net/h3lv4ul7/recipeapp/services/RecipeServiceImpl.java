package net.h3lv4ul7.recipeapp.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.converters.RecipeCommandToRecipe;
import net.h3lv4ul7.recipeapp.converters.RecipeToRecipeCommand;
import net.h3lv4ul7.recipeapp.domain.Recipe;
import net.h3lv4ul7.recipeapp.repositories.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	private final RecipeCommandToRecipe recipeCommandToRecipe;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand,
			RecipeCommandToRecipe recipeCommandToRecipe) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
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

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe recipe = recipeCommandToRecipe.convert(command);
		Recipe savedRecipe = recipeRepository.save(recipe);
		return recipeToRecipeCommand.convert(savedRecipe);
	}

	@Override
	@Transactional
	public RecipeCommand findCommandById(Long id) {
		
		return recipeToRecipeCommand.convert(getRecipeById(id));
	}

	@Override
	public RecipeCommand findCommandByDescription(String description) {

		return recipeToRecipeCommand.convert(getRecipeByDescription(description));
	}
}
