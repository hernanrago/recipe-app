package net.h3lv4ul7.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import net.h3lv4ul7.recipeapp.commands.IngredientCommand;
import net.h3lv4ul7.recipeapp.commands.RecipeCommand;
import net.h3lv4ul7.recipeapp.commands.UnitOfMeasureCommand;
import net.h3lv4ul7.recipeapp.services.IngredientService;
import net.h3lv4ul7.recipeapp.services.RecipeService;
import net.h3lv4ul7.recipeapp.services.UnitOfMeasureService;

@Slf4j
@Controller
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
	
	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
		log.debug("Calling listIngredients method. Params: "+recipeId+", "+ingredientId);
		
		ingredientService.deleteIngredientById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		
		return "redirect:/recipe/" + recipeId +"/ingredients";

	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Calling IngredientController.listIngredients method.\nParams: "
				  +recipeId+" "+model);
		
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		
		return "/recipe/ingredient/list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String showNewIngredientForm(@PathVariable String recipeId, Model model) {
		log.debug("Calling IngredientController.showNewIngredientForm method.\nParams: "+model);
		
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeCommand.getId());
		
		model.addAttribute("ingredient", ingredientCommand);
		
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
		
		model.addAttribute("unitOfMeasureList", unitOfMeasureService.listAllUnitsOfMeasures());
		
		return "/recipe/ingredient/ingredientform";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showRecipeIngredient(@PathVariable String recipeId,
									   @PathVariable String ingredientId,
									   Model model) {
		log.debug("Calling IngredientController.showRecipeIngredient method.\nParams: "
				  +recipeId+" "+ingredientId+" "+model);

		model.addAttribute("ingredient",
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), 
						Long.valueOf(ingredientId)));
		
		return "/recipe/ingredient/show";
	}
	
    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUnitsOfMeasures());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredient){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredient);

        log.debug("saved recipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId().toString() + "/ingredient/" + savedCommand.getId().toString() + "/show";
    }
}
