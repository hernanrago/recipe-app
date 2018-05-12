package net.h3lv4ul7.recipeapp.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.services.RecipeService;

@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;
	
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/delete/{id}")
	public String deleteRecipe(@PathVariable String id) {
		log.debug("Calling RecipeController.deleteRecipe method.\nParams: " + id);

		recipeService.deleteById(Long.valueOf(id));

		return "redirect:/";
	}

	@PostMapping("/recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> log.error(objectError.toString()));
			return RECIPE_RECIPEFORM_URL;
		}

		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}

	@GetMapping("/recipe/new")
	public String showNewRecipeForm(Model model) {
		log.debug("Calling newRecipe method. Params: " + model);

		model.addAttribute("recipe", new RecipeCommand());

		return RECIPE_RECIPEFORM_URL;
	}

	@GetMapping("/recipe/{id}/show")
	public String showRecipe(@PathVariable String id, Model model) {
		log.debug("Calling showRecipe method. Params: " + id + " " + model);

		model.addAttribute("recipe", recipeService.findRecipeById(Long.valueOf(id)));

		return "/recipe/show";
	}

	@GetMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		log.debug("Calling RecipeController.updateRecipe method.\nParams: " + id + " " + model);

		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

		return RECIPE_RECIPEFORM_URL;
	}

}
