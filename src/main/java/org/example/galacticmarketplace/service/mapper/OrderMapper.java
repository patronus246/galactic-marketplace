package org.example.galacticmarketplace.service.mapper;

import org.example.galacticmarketplace.domain.order.OrderEntry;
import org.example.galacticmarketplace.dto.order.OrderEntryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "productID", target = "productID")
    @Mapping(source = "quantity", target = "quantity")
    OrderEntry toOrderItem(OrderEntryDto orderItemDto);
}
