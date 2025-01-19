package org.example.galacticmarketplace.web;

import jakarta.validation.Valid;
import org.example.galacticmarketplace.domain.Product;
import org.example.galacticmarketplace.dto.ProductDto;
import org.example.galacticmarketplace.service.ProductService;
import org.example.galacticmarketplace.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@Validated
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productMapper.toProductDto(productService.getProductById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        return ResponseEntity.ok(productMapper.toProductDto(productService.createProduct(product)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        return ResponseEntity.ok(productMapper.toProductDto(productService.updateProduct(id, product)));
    }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
       productService.deleteProduct(id);
       return ResponseEntity.noContent().build();
   }
}