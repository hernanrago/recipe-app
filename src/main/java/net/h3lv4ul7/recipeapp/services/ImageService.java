package net.h3lv4ul7.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	void saveImageFile(Long valueOf, MultipartFile file);

}
