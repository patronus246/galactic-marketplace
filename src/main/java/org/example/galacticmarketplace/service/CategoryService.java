package org.example.galacticmarketplace.service;
import org.example.galacticmarketplace.domain.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category createCategory(Category category);
    void deleteCategory(UUID categoryId);
    Category getCategoryById(UUID categoryId);
    List<Category> getAllCategories();
}
