package net.h3lv4ul7.recipeapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.services.RecipeService;

@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/delete/{id}")
	public String deleteRecipe(@PathVariable String id) {
		log.debug("Calling RecipeController.deleteRecipe method.\nParams: " + id);

		recipeService.deleteById(Long.valueOf(id));

		return "redirect:/";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public String handleNotFound() {
		log.error("Handling not found exception");

		return "404error";
	}

	@PostMapping("/recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		log.debug("Calling RecipeController.saveOrUpdate method.\nParams: " + command);

		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}

	@GetMapping("/recipe/new")
	public String showNewRecipeForm(Model model) {
		log.debug("Calling RecipeController.newRecipe method.\nParams: " + model);

		model.addAttribute("recipe", new RecipeCommand());

		return "/recipe/recipeform";
	}

	@GetMapping("/recipe/{id}/show")
	public String showRecipe(@PathVariable String id, Model model) {
		log.debug("Calling RecipeController.showRecipe method.\nParams: " + id + " " + model);

		model.addAttribute("recipe", recipeService.findRecipeById(Long.valueOf(id)));

		return "/recipe/show";
	}

	@GetMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		log.debug("Calling RecipeController.updateRecipe method.\nParams: " + id + " " + model);

		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

		return "/recipe/recipeform";
	}

}
