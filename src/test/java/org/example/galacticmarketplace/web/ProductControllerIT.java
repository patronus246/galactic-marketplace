package org.example.galacticmarketplace.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.galacticmarketplace.domain.Product;
import org.example.galacticmarketplace.dto.ProductDto;
import org.example.galacticmarketplace.service.ProductService;
import org.example.galacticmarketplace.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Product Controller Tests")
public class ProductControllerIT {
    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private ProductDto productDto;
    private Product mockProduct;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productDto = buildProductDto("Galaxy Milk", "Milk for astronauts", 29.7);
        mockProduct = Product.builder()
                .id(PRODUCT_ID)
                .name("Galaxy Milk")
                .description("Milk for astronauts")
                .categoryID(productDto.getCategoryID())
                .price(29.7)
                .build();
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any())).thenReturn(mockProduct);
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameProduct").value(productDto.getNameProduct()))
                .andExpect(jsonPath("$.description").value(productDto.getDescription()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()));
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDto updatedProductDto = buildProductDto("Updated Galaxy Milk", "Updated description", 34.8);
        Product updatedProduct = Product.builder()
                .id(PRODUCT_ID)
                .name(updatedProductDto.getNameProduct())
                .description(updatedProductDto.getDescription())
                .price(updatedProductDto.getPrice())
                .build();

        when(productService.updateProduct(any(), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/v1/product/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameProduct").value(updatedProductDto.getNameProduct()))
                .andExpect(jsonPath("$.description").value(updatedProductDto.getDescription()))
                .andExpect(jsonPath("$.price").value(updatedProductDto.getPrice()));
    }

    @Test
    void testGetProductById() throws Exception {
        when(productService.getProductById(PRODUCT_ID)).thenReturn(mockProduct);
        mockMvc.perform(get("/api/v1/product/{id}", PRODUCT_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameProduct").value(mockProduct.getName()))
                .andExpect(jsonPath("$.description").value(mockProduct.getDescription()))
                .andExpect(jsonPath("$.price").value(mockProduct.getPrice()));
    }

    @Test
    void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById(any())).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(get("/api/v1/product/{id}", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Product Not Found"));
    }

    @Test
    void testCreateProductWithNegativePrice() throws Exception {
        ProductDto invalidProductDto = buildProductDto("Star Milk", "Invalid price product", -5.0);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProductDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"))
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("price"));
    }


    @Test
    void testCreateProductWithInvalidName() throws Exception {
        ProductDto invalidNameProductDto = buildProductDto("Regular Milk", "Milk without cosmic term", 10.0);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidNameProductDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"))
                .andExpect(jsonPath("$.invalidParams[0].fieldName").value("nameProduct"))
                .andExpect(jsonPath("$.invalidParams[0].reason").value("Product name must contain a cosmic term (e.g., 'star', 'galaxy', 'comet')"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(PRODUCT_ID);

        mockMvc.perform(delete("/api/v1/product/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProductProductNotFound() throws Exception {
        doNothing().when(productService).deleteProduct(PRODUCT_ID);
        mockMvc.perform(delete("/api/v1/product/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private static ProductDto buildProductDto(String name, String description, double price) {
        return ProductDto.builder()
                .nameProduct(name)
                .categoryID(UUID.randomUUID().toString())
                .description(description)
                .price(price)
                .build();
    }

}