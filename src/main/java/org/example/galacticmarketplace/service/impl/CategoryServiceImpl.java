package org.example.galacticmarketplace.service.impl;

import jakarta.persistence.PersistenceException;
import org.example.galacticmarketplace.domain.Category;
import org.example.galacticmarketplace.repository.CategoryRepository;
import org.example.galacticmarketplace.service.CategoryService;
import org.example.galacticmarketplace.service.exception.CategoryNotFoundException;
import org.example.galacticmarketplace.service.mapper.CategoryMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        try {
            return categoryMapper.toCategory(categoryRepository.save(categoryMapper.toCategoryEntity(category)));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional
    public void deleteCategory(UUID categoryId) {
        try {
            categoryRepository.findById(categoryId).orElseThrow(() -> {
                log.info("Category with id {} not found", categoryId);
                return new CategoryNotFoundException(categoryId);
            });
            categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(UUID categoryId) {
        return categoryMapper.toCategory(categoryRepository.findById(categoryId).orElseThrow(()-> new CategoryNotFoundException(categoryId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        try {
            return categoryMapper.toCategoryList(categoryRepository.findAll().iterator());
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}