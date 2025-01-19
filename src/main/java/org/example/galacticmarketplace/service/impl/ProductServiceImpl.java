package org.example.galacticmarketplace.service.impl;

import org.example.galacticmarketplace.domain.Product;
import org.example.galacticmarketplace.service.ProductService;
import org.example.galacticmarketplace.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final List<Product> products = new ArrayList<>(buildAllProductMock());

    @Override
    public Product createProduct(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public Product getProductById(UUID productId) {
        return Optional.of(products.stream()
                        .filter(details -> details.getId().equals(productId)).findFirst())
                .get()
                .orElseThrow(() -> {
                    log.info("Product with id {} not found in mock", productId);
                    return new ProductNotFoundException(productId);
                });
    }

    @Override
    public Product updateProduct(UUID productId, Product updatedProduct) {
        Product existingProduct = getProductById(productId);

        Product updatedExistingProduct = Product.builder()
                .id(productId)
                .categoryID(updatedProduct.getCategoryID())
                .name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .price(updatedProduct.getPrice())
                .build();

        log.info("Product with id {} updated successfully", productId);
        products.set(products.indexOf(existingProduct), updatedExistingProduct);
        return updatedExistingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public void deleteProduct(UUID productId) {
        Optional<Product> productOptional = products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();

        if (productOptional.isPresent()) {
            products.remove(productOptional.get());
            log.info("Product with id {} deleted successfully", productId);
        } else {
            log.info("Product with id {} not found, no action taken", productId);
        }
    }

    private List<Product> buildAllProductMock() {
        return List.of(
                Product.builder()
                        .id(UUID.randomUUID())
                        .categoryID(UUID.randomUUID().toString())
                        .name("Galaxy Glow Notebook")
                        .description("A notebook that illuminates your ideas like stars in the galaxy.")
                        .price(9.49)
                        .build(),

                Product.builder()
                        .id(UUID.randomUUID())
                        .categoryID(UUID.randomUUID().toString())
                        .name("Lunar Crystal Lamp")
                        .description("A mesmerizing lamp that shines like the glow of the moon.")
                        .price(34.99)
                        .build(),

                Product.builder()
                        .id(UUID.randomUUID())
                        .categoryID(UUID.randomUUID().toString())
                        .name("Nebula Wireless Charger")
                        .description("Charge your devices with the elegance of a nebula's swirling colors.")
                        .price(19.99)
                        .build(),

                Product.builder()
                        .id(UUID.randomUUID())
                        .categoryID(UUID.randomUUID().toString())
                        .name("Aurora Scented Candle")
                        .description("Fill your room with the magical aroma of the northern lights.")
                        .price(11.49)
                        .build(),

                Product.builder()
                        .id(UUID.randomUUID())
                        .categoryID(UUID.randomUUID().toString())
                        .name("Starfall Mug")
                        .description("A mug designed to bring the beauty of a shooting star to your coffee break.")
                        .price(14.99)
                        .build()
        );
    }

}
