package net.h3lv4ul7.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.h3lv4ul7.recipeapp.services.RecipeService;

/**
 * Created by jt on 6/1/17.
 */
@Controller
public class IndexController {

	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}



	@RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){
		model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
