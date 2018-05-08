package net.h3lv4ul7.recipeapp.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.h3lv4ul7.recipeapp.commands.IngredientCommand;
import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.services.IngredientService;
import net.h3lv4ul7.recipeapp.services.RecipeService;
import net.h3lv4ul7.recipeapp.services.UnitOfMeasureService;

public class IngredientControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	IngredientService ingredientService;
	
	@Mock
	UnitOfMeasureService unitOfMeasureService;
	
	IngredientController ingredientController;
	
	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}
	
	@Test
	public final void testDeleteIngredient() throws Exception {
		mockMvc.perform(get("/recipe/{recipeId}/ingredient/{ingredientId}", 1L, 1L))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/{recipeId}/ingredients"));
		
		verify(ingredientService, times(1)).deleteIngredientById(anyLong(), anyLong());;
	}
	
	@Test
	public final void testListIngredients() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/{recipeId}/ingredients", 1L))
			.andExpect(status().isOk())
			.andExpect(view().name("/recipe/ingredient/list"))
			.andExpect(model().attributeExists("recipe"));
		
		verify(recipeService, times(1)).findCommandById(anyLong());
		
	}
	
	public void testNewIngredientForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(command);
		when(unitOfMeasureService.listAllUnitsOfMeasures()).thenReturn(new HashSet<>());
		
		mockMvc.perform(get("/recipe/{recipeId}/ingredient/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("/recipe/ingredient/ingredientform"))
			.andExpect(model().attributeExists("ingredient"));
		
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public final void testShowRecipeIngredient() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		when(ingredientService.findByRecipeIdAndIngredientId(
				anyLong(), anyLong())).thenReturn(ingredientCommand);
		
		mockMvc.perform(get("/recipe/{recipeId}/ingredient/{ingredientId}/show"
				,1L, 1L))
			.andExpect(status().isOk())
			.andExpect(view().name("/recipe/ingredient/show"))
			.andExpect(model().attributeExists("ingredient"));	
	}
}
