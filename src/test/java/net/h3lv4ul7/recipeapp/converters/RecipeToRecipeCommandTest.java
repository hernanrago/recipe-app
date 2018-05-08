package net.h3lv4ul7.recipeapp.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.h3lv4ul7.recipeapp.domain.Difficulty;
import net.h3lv4ul7.recipeapp.domain.Recipe;

public class RecipeToRecipeCommandTest {
	
	public static final Long ID=1L;
	public static final String DESCRIPTION="My recipe";
	public static final Integer PREP_TIME=7;
	public static final Integer COOK_TIME=5;
	public static final Integer SERVINGS=4;
	public static final String URL="www.myrecipe.com";
	public static final String SOURCE="any source";
	public static final String DIRECTION="some directions";
	public static final Difficulty DIFFICULTY=Difficulty.EASY;
	public static final Long CATEGORY_ID=3L;
	public static final Long INGREDIENT_ID=4L;
	RecipeToRecipeCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new RecipeToRecipeCommand(
				new CategoryToCategoryCommand(),
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new NoteToNoteCommand());
	}

	@Test
	public final void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public final void testEmptyObject() {
		assertNotNull(converter.convert(new Recipe()));
	}

}
