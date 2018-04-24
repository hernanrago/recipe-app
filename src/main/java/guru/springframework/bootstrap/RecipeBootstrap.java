package guru.springframework.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent>{
	
	private final CategoryRepository categoryRepository;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}
	
	private List<Recipe> getRecipes(){
		
		List<Recipe> recipes = new ArrayList<>();
		
		Optional<UnitOfMeasure> unitUom = unitOfMeasureRepository.findByDescription("Unit");
		Optional<UnitOfMeasure> teaSpoonUom = unitOfMeasureRepository.findByDescription("Teaspoon");
		Optional<UnitOfMeasure> tableSpoonUom = unitOfMeasureRepository.findByDescription("Tablespoon");
		Optional<UnitOfMeasure> CupUom = unitOfMeasureRepository.findByDescription("Cup");
		Optional<UnitOfMeasure> dashUom = unitOfMeasureRepository.findByDescription("Dash");

		Optional<Category> americanCategory = categoryRepository.findByDescription("Mexican");
		
		Recipe perfectGuacamole = new Recipe();
		perfectGuacamole.setDescription("Perfect Guacamole");
		perfectGuacamole.setPrepTime(10);
		perfectGuacamole.setCookTime(0);
		perfectGuacamole.setServings(4);
		perfectGuacamole.setDirection(
				"1. Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado "
				+ "with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place"
				+ " in a bowl.\n"
				
				+ "2. Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should"
				+ " be a little chunky.)\n"
				
				+"3. Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the "
				+ "lime juice will provide some balance to the richness of the avocado and will help delay the avocados"
				+ " from turning brown.\n"
				+"Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their "
				+ "hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree "
				+ "of hotness.\n" 
				+"Remember that much of this is done to taste because of the variability in the fresh ingredients. "
				+ "Start with this recipe and adjust to your taste.\n"
				
				+"4. Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it"
				+ " and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the "
				+ "guacamole brown.) Refrigerate until ready to serve.\n"
				+"Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it "
				+ "just before serving.");
		perfectGuacamole.setDifficulty(Difficulty.EASY);
		perfectGuacamole.getIngredients().add(new Ingredient("ripe avocado", new BigDecimal(2), unitUom.get()));
		perfectGuacamole.getIngredients().add(new Ingredient("salt", new BigDecimal(0.5), teaSpoonUom.get()));
		perfectGuacamole.getIngredients().add(new Ingredient("fresh lime juice", new BigDecimal(1),
				tableSpoonUom.get()));
		perfectGuacamole.getIngredients().add(new Ingredient("minced onion", new BigDecimal(2), tableSpoonUom.get()));
		perfectGuacamole.getIngredients().add(new Ingredient("serrano chile", new BigDecimal(1), unitUom.get()));
		perfectGuacamole.getIngredients().add(new Ingredient("cilantro", new BigDecimal(2), tableSpoonUom.get()));
		perfectGuacamole.getIngredients().add(new Ingredient("grated black pepper", new BigDecimal(1), dashUom.get()));
		perfectGuacamole.getIngredients().add(new Ingredient("ripe tomato", new BigDecimal(0.5), unitUom.get()));

		perfectGuacamole.getCategories().add(americanCategory.get());
		
		recipes.add(perfectGuacamole);
		
		return recipes;

	}
	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		initData();
		
	}

	private void initData() {
		
		recipeRepository.saveAll(getRecipes());
	}
	
	

}
