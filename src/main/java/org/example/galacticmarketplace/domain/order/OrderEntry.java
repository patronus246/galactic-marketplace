package org.example.galacticmarketplace.domain.order;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class OrderEntry {
    UUID productID;
    int quantity;
}
