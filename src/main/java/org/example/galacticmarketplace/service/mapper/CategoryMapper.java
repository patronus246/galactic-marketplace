package org.example.galacticmarketplace.service.mapper;

import org.example.galacticmarketplace.domain.Category;
import org.example.galacticmarketplace.dto.category.CategoryDto;
import org.example.galacticmarketplace.dto.category.CategoryEntryDto;
import org.example.galacticmarketplace.dto.category.CategoryListDto;
import org.example.galacticmarketplace.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "name", target = "nameCategory")
    @Mapping(source = "products", target = "products")
    CategoryDto toCategoryDto(Category category);

    default CategoryListDto toCategoryListDto(List<Category> categories) {
        return CategoryListDto.builder().categories(toCategoryEntryDto(categories)).build();
    }

    default List<Category> toCategoryList(Iterator<CategoryEntity> produccategoryEntityIterator) {
        List<Category> result = new ArrayList<>();
        produccategoryEntityIterator.forEachRemaining(
                (categoryEntity) -> {
                    result.add(toCategory(categoryEntity));
                });
        return result;
    }

    List<CategoryEntryDto> toCategoryEntryDto(List<Category> categories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "products", target = "products")
    CategoryEntryDto toCategoryEntryDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "nameCategory", target = "name")
    @Mapping(source = "products", target = "products")
    Category toCategory(CategoryDto categoryDto);

    @Mapping(source = "categoryName", target = "name")
    Category toCategory(CategoryEntity categoryEntity);

    @Mapping(source = "name", target = "categoryName")
    CategoryEntity toCategoryEntity(Category categoryDto);
}
