package net.h3lv4ul7.recipeapp.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
	
	Category category;
	
	Long id = 1L;
	
	String description = "French";

	
	@Before
	public void setUp() throws Exception {
		category = new Category();
	}

	@Test
	public final void testGetId() throws Exception {		
		category.setId(id);
		
		assertEquals(id, category.getId());
	}

	@Test
	public final void testSetId() {
		
	}

	@Test
	public final void testGetDescription() {
		category.setDescription(description);
		
		assertEquals(description, category.getDescription());
	}

	@Test
	public final void testSetDescription() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetRecipes() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetRecipes() {
		fail("Not yet implemented"); // TODO
	}

}
