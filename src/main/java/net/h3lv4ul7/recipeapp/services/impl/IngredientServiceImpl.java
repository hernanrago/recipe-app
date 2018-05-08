package net.h3lv4ul7.recipeapp.services.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.h3lv4ul7.recipeapp.commands.IngredientCommand;
import net.h3lv4ul7.recipeapp.converters.IngredientCommandToIngredient;
import net.h3lv4ul7.recipeapp.converters.IngredientToIngredientCommand;
import net.h3lv4ul7.recipeapp.domain.Ingredient;
import net.h3lv4ul7.recipeapp.domain.Recipe;
import net.h3lv4ul7.recipeapp.repositories.RecipeRepository;
import net.h3lv4ul7.recipeapp.repositories.UnitOfMeasureRepository;
import net.h3lv4ul7.recipeapp.services.IngredientService;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(
			Long recipeId, Long ingredientId) {
		log.debug("Calling IngredientServiceImpl.findByRecipeIdAndIngredientId method.\nParams: "+recipeId+" "+ingredientId);
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(!recipeOptional.isPresent()) throw new RuntimeException("Recipe "+ recipeId + " not found.");
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommandOptional =
				recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
		
		if(!ingredientCommandOptional.isPresent()) throw new RuntimeException("Ingredient "+ ingredientId+ " not found.");
		
		return ingredientCommandOptional.get();
	}
	
	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		
		if(!recipeOptional.isPresent()) {
			log.error("Recipe with id "+command.getRecipeId()+" not found for ingredient with id "+command.getId());
			return new IngredientCommand();
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<Ingredient> ingredientOptional = recipe
				.getIngredients()
				.stream()
				.filter(ingredient -> ingredient.getId().equals(command.getId()))
				.findFirst();
		
		if(!ingredientOptional.isPresent()) recipe.addIngredient(ingredientCommandToIngredient.convert(command));
		
		Ingredient ingredient = ingredientOptional.get();
		ingredient.setDescription(command.getDescription());
		ingredient.setAmount(command.getAmount());
		ingredient.setUnitOfMeasure(unitOfMeasureRepository
				.findById(command.getUnitOfMeasure().getId())
				.orElseThrow(() -> new RuntimeException("Unit of Measure not found")));
		
		recipe.addIngredient(ingredient);
		
		Recipe savedRecipe = recipeRepository.save(recipe);
		
        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                .findFirst();

        if(!savedIngredientOptional.isPresent()){
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId()
                    		.equals(command.getUnitOfMeasure().getId()))
                    .findFirst();
        }
		
		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
	}

	@Override
	public void deleteIngredientById(Long recipeId, Long ingredientId) {
		log.debug("Calling deleteIngredientById method. Params: "+recipeId + ", " + ingredientId);
				
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
				
		Recipe recipe = recipeOptional.get();
		
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.findFirst();
		
		Ingredient ingredient = ingredientOptional.get();
		ingredient.setRecipe(null);
		
		recipe.getIngredients().remove(ingredient);
		
		recipeRepository.save(recipe);	
	}
}
