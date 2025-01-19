package org.example.galacticmarketplace.dto.category;


import org.example.galacticmarketplace.dto.ProductEntryDto;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CategoryEntryDto {
    UUID id;
    String name;
    List<ProductEntryDto> products;
}