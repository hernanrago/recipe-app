package net.h3lv4ul7.recipeapp.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import net.h3lv4ul7.recipeapp.domain.Recipe;
import net.h3lv4ul7.recipeapp.services.RecipeService;

public class IndexControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	IndexController indexController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		indexController = new IndexController(recipeService);
	}
	
	@Test
	public void testMockMvc() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	public final void testGetIndexPage() {		
		Recipe soupeALOignon = new Recipe();
		soupeALOignon.setDescription("Soupe à l'oignon");
		
		Recipe coqAVin = new Recipe();
		coqAVin.setDescription("Coq au vin");
		
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(soupeALOignon);
		recipes.add(coqAVin);
		
		when(recipeService.getRecipes()).thenReturn(recipes);
		
		@SuppressWarnings("unchecked")
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		String viewName = indexController.getIndexPage(model);
		
		assertEquals("index", viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
		
	}

}
