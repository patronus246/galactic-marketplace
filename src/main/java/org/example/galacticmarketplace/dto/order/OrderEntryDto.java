package org.example.galacticmarketplace.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class OrderEntryDto {
    @NotNull(message = "Product ID name cannot be null")
    UUID productID;

    @NotNull(message = "Quantity cannot be null")
    int quantity;
}
