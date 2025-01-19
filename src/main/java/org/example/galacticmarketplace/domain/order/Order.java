package org.example.galacticmarketplace.domain.order;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Order {
    UUID id;
    List<OrderEntry> entries;
    Double totalPrice;
}
