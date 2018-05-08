package net.h3lv4ul7.recipeapp.services.impl;

import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.h3lv4ul7.recipeapp.domain.Recipe;
import net.h3lv4ul7.recipeapp.repositories.RecipeRepository;
import net.h3lv4ul7.recipeapp.services.ImageService;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	
	RecipeRepository recipeRepository;
	
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	@Transactional
	public void saveImageFile(Long id, MultipartFile file) {
		try {
			Optional<Recipe> recipeOptional = recipeRepository.findById(id);
			
			Byte [] byteObjects = new Byte[file.getBytes().length];
			
			int i = 0;
			
			for(byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			
			if(recipeOptional.isPresent()) {
				Recipe recipe = recipeOptional.get();
				
				recipe.setImage(byteObjects);
				
				recipeRepository.save(recipe);
			}
		} catch(IOException e) {
			log.error("Error ocurred", e);
			
			e.printStackTrace();
		}
	}
}
