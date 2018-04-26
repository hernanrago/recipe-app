package net.h3lv4ul7.recipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.h3lv4ul7.recipeapp.domain.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long>{
	
	Optional<Recipe> findByDescription(String description);


}
