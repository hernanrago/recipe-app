package net.h3lv4ul7.recipeapp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.domain.Recipe;
import net.h3lv4ul7.recipeapp.services.RecipeService;

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;

	RecipeController recipeController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		recipeController = new RecipeController(recipeService);

		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

	}

	@Test
	public final void testDeleteRecipe() throws Exception {
		mockMvc.perform(get("/delete/{id}", 1L))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

	@Test
	public final void testNewRecipe() throws Exception {
		mockMvc.perform(get("/recipe/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("/recipe/recipeform"))
			.andExpect(model().attributeExists("recipe"));		
	}

	@Test
	public final void testSaveOrUpdate() throws Exception{
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
		
		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string")
				.param("direction", "some directions"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/1/show"));
	}

	@Test
	public final void testShowRecipe() throws Exception {
		Recipe recipe = new Recipe();

		recipe.setId(1L);

		when(recipeService.findRecipeById(anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/{id}/show", 1L))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("recipe"))
			.andExpect(view().name("/recipe/show"));
	}

	@Test
	public final void testUpdateRecipe() throws Exception{
		RecipeCommand recipeCommand = new RecipeCommand();
		
		recipeCommand.setDescription("Amuse-Bouche");
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/{id}/update", 1L))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("recipe"))
			.andExpect(view().name("/recipe/recipeform"));
	}
	
	@Test
	public void testPostNewRecipeFormValidationFail() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		
		recipeCommand.setId(1L);
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
		
		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "1")
				.param("description", "some string")
				.param("direction", "some direction"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("recipe"))
		.andExpect(view().name("/recipe/show"));
	}

}
