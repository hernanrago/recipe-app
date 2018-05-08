package net.h3lv4ul7.recipeapp.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.h3lv4ul7.recipeapp.commands.IngredientCommand;
import net.h3lv4ul7.recipeapp.converters.IngredientCommandToIngredient;
import net.h3lv4ul7.recipeapp.converters.IngredientToIngredientCommand;
import net.h3lv4ul7.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import net.h3lv4ul7.recipeapp.domain.Ingredient;
import net.h3lv4ul7.recipeapp.domain.Recipe;
import net.h3lv4ul7.recipeapp.repositories.RecipeRepository;
import net.h3lv4ul7.recipeapp.repositories.UnitOfMeasureRepository;
import net.h3lv4ul7.recipeapp.services.impl.IngredientServiceImpl;

public class IngredientServiceImplTest {
	
	IngredientService ingredientService;

	IngredientToIngredientCommand ingredientToIngredientCommand;
	
	IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
				new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
				recipeRepository, unitOfMeasureRepository);
	}

	@Test
	public final void testFindByRecipeIdAndImgredientId() {
		Recipe potageDeLegumes = new Recipe();
		potageDeLegumes.setId(1L);
		potageDeLegumes.setDescription("Potage de Légumes");

		Ingredient bigCarrot = new Ingredient();
		bigCarrot.setId(1L);
		bigCarrot.setDescription("big carrot");

		Ingredient potatoe = new Ingredient();
		potatoe.setId(2L);
		potatoe.setDescription("potatoe");

		Ingredient leek = new Ingredient();
		leek.setId(3L);
		leek.setDescription("leek");
				
		potageDeLegumes.addIngredient(bigCarrot);	
		potageDeLegumes.addIngredient(potatoe);		
		potageDeLegumes.addIngredient(leek);
		
		Optional<Recipe> optional = Optional.of(potageDeLegumes);
		
		when(recipeRepository.findById(anyLong())).thenReturn(optional);
		
		IngredientCommand ingredientCommand = 
				ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
				
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
	}
	
	@Test
	public void testSaveRecipeCommand() throws Exception {
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		assertEquals(Long.valueOf(3L), savedCommand.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
		
	}
}
