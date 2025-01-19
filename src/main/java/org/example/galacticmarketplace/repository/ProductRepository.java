package org.example.galacticmarketplace.repository;

import org.example.galacticmarketplace.repository.entity.ProductEntity;
import org.example.galacticmarketplace.repository.projection.ProductReportProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {
    ProductEntity findByProductName(String name);

    @Query("SELECT p.productName AS productName, SUM(oi.quantity) AS totalQuantity " +
            "FROM OrderItemEntity oi " +
            "JOIN ProductEntity p ON oi.productId = p.id " +
            "GROUP BY p.productName " +
            "ORDER BY totalQuantity DESC")
    List<ProductReportProjection> findTopSellingProducts();
}