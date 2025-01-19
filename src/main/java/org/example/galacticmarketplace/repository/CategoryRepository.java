package org.example.galacticmarketplace.repository;

import org.example.galacticmarketplace.repository.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, UUID> {
}