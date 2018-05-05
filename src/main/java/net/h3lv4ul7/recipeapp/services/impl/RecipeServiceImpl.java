package net.h3lv4ul7.recipeapp.services.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.converters.RecipeCommandToRecipe;
import net.h3lv4ul7.recipeapp.converters.RecipeToRecipeCommand;
import net.h3lv4ul7.recipeapp.domain.Recipe;
import net.h3lv4ul7.recipeapp.repositories.RecipeRepository;
import net.h3lv4ul7.recipeapp.services.RecipeService;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	private final RecipeCommandToRecipe recipeCommandToRecipe;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand,
			RecipeCommandToRecipe recipeCommandToRecipe) {
		this.recipeRepository = recipeRepository;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
	}

	@Override
	public void deleteById(Long id) {
		log.debug("Calling RecipeServiceImpl.deleteById method\nParams: "+id);
		
		recipeRepository.deleteById(id);
	}

	@Override
	public Recipe findRecipeById(Long id) {
		log.debug("Calling RecipeServiceImpl.findRecipeById method\nParams: "+id);
		
		Optional<Recipe> optional = recipeRepository.findById(id);
		
		if(!optional.isPresent()) throw new RuntimeException("Recipe with id "+ id + "not found.");
		
		return optional.get();
	}

	@Override
	@Transactional
	public RecipeCommand findCommandById(Long id) {
		log.debug("Calling RecipeServiceImpl.findCommandById method\nParams: "+id);
		
		return recipeToRecipeCommand.convert(findRecipeById(id));
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		log.debug("Calling RecipeServiceImpl.saveRecipeCommand method\nParams: "+command);

		return recipeToRecipeCommand
				.convert(recipeRepository.save(recipeCommandToRecipe.convert(command)));
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("Calling RecipeServiceImpl.saveRecipeCommand method.");

		Set<Recipe> recipes = new HashSet<>();
		
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		
		return recipes;
	}
	
	
}