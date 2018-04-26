package net.h3lv4ul7.recipeapp.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.h3lv4ul7.recipeapp.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

	List<Ingredient> getIngredientsByDescription(String...description);
	
}
