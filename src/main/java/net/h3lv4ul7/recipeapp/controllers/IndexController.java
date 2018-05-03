package net.h3lv4ul7.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.h3lv4ul7.recipeapp.services.RecipeService;

@Slf4j
@Controller
public class IndexController {
	
	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){
		log.debug("Calling IndexController.getIndexPage method.");
		
		model.addAttribute("recipes", recipeService.getRecipes());
		
        return "index";
    }
}
