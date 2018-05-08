package net.h3lv4ul7.recipeapp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.h3lv4ul7.recipeapp.exceptions.NotFoundException;
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
	public final void testGetRecipeNotFound() throws Exception {		
		when(recipeService.findRecipeById(anyLong())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(get("/recipe/1/show"))
			.andExpect(status().isNotFound())
			.andExpect(view().name("404error"));
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
		
		mockMvc.perform(post("/recipe"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/recipe/"+recipeService.saveRecipeCommand(recipeCommand).getId()+"/show"))
			.andExpect(content().contentType(MediaType.APPLICATION_FORM_URLENCODED));
			
		
	}

	@Test
	public final void testShowRecipe() throws Exception {
		Recipe recipe = new Recipe();

		recipe.setDescription("Apéritif");

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

}
