package net.h3lv4ul7.recipeapp.converters;

import net.h3lv4ul7.recipeapp.commands.IngredientCommand;
import net.h3lv4ul7.recipeapp.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;


@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
    
	private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
		this.uomConverter = uomConverter;
	}
	
	@Nullable
	@Override
	@Synchronized
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) return null;

        IngredientCommand ingredientCommand = new IngredientCommand();
        
        ingredientCommand.setId(ingredient.getId());
        if((ingredient.getRecipe() != null)) ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setUnitOfMeasure(uomConverter.convert(ingredient.getUnitOfMeasure()));
        
        return ingredientCommand;
    }
}
