package net.h3lv4ul7.recipeapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Note {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Recipe recipe;
	private String recipeNotes;
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public String getRecipeNotes() {
		return recipeNotes;
	}
	public void setRecipeNotes(String recipeNotes) {
		this.recipeNotes = recipeNotes;
	}
	
	

}
