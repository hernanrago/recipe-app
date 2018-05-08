package net.h3lv4ul7.recipeapp.converters;

import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.domain.Category;
import net.h3lv4ul7.recipeapp.domain.Ingredient;
import net.h3lv4ul7.recipeapp.domain.Recipe;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
	
	private final CategoryToCategoryCommand categoryConverter;
	private final IngredientToIngredientCommand ingredientConverter;
	private final NoteToNoteCommand noteConverter;

	public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
			IngredientToIngredientCommand ingredientConverter, NoteToNoteCommand noteConverter) {
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
		this.noteConverter = noteConverter;
	}

	@Nullable
	@Override
	@Synchronized
	public RecipeCommand convert(Recipe source) {
		log.debug("Calling RecipeToRecipeCommand.convert method.\nParams: " + source);

		if (source == null) return null;

		RecipeCommand command = new RecipeCommand();
		
		command.setId(source.getId());
		command.setDescription(source.getDescription());
		command.setPrepTime(source.getPrepTime());
		command.setCookTime(source.getCookTime());
		command.setServings(source.getServings());
		command.setUrl(source.getUrl());
		command.setSource(source.getSource());
		command.setDirection(source.getDirection());
		command.setDifficulty(source.getDifficulty());
		command.setNote(noteConverter.convert(source.getNote()));

		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories()
					.forEach((Category category) -> command.getCategories().add(categoryConverter.convert(category)));
		}

		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients()
					.forEach((Ingredient ingredient) -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		command.setImage(source.getImage());

		return command;
	}
}
