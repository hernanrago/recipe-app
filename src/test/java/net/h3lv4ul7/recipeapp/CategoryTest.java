package net.h3lv4ul7.recipeapp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.h3lv4ul7.recipeapp.domain.Category;

public class CategoryTest {
	
	Category category;
	
	@Before
	public void setUp() {
		category = new Category();
	}

	@Test
	public void testGetId() {
		Long idValue = 4L;
		category.setId(idValue);
		assertEquals(idValue, category.getId());
	}

	@Test
	public void testGetDescription() {
		
	}

	@Test
	public void testGetRecipes() {

	}

}
