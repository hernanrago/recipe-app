package net.h3lv4ul7.recipeapp.commands;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.h3lv4ul7.recipeapp.domain.Difficulty;

@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {
	
	private Long id;
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String url;
	private String source;
	private String direction;
	private Difficulty difficulty;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private NoteCommand note;
	private Set<CategoryCommand> categories = new HashSet<>();
	private Byte[] image;
}
