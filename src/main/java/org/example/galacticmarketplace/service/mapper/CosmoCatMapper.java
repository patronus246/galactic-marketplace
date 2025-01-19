package org.example.galacticmarketplace.service.mapper;

import org.example.galacticmarketplace.domain.CosmoCat;
import org.example.galacticmarketplace.dto.CosmoCat.CosmoCatDto;
import org.example.galacticmarketplace.dto.CosmoCat.CosmoCatEntryDto;
import org.example.galacticmarketplace.dto.CosmoCat.CosmoCatListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CosmoCatMapper {

    @Mapping(source = "cat_name", target = "cat_name")
    @Mapping(source = "email", target = "email")
    CosmoCatDto toCosmoCatDto(CosmoCat cat);

    @Mapping(target = "cat_id", ignore = true)
    @Mapping(source = "cat_name", target = "cat_name")
    @Mapping(source = "email", target = "email")
    CosmoCat toCosmoCat(CosmoCatDto cosmoCatDto);

    default CosmoCatListDto toCosmoCatListDto(List<CosmoCat> cats) {
        return CosmoCatListDto.builder().cosmoCats(toCosmoCatEntryDto(cats)).build();
    }

    List<CosmoCatEntryDto> toCosmoCatEntryDto(List<CosmoCat> cats);

    @Mapping(source = "cat_id", target = "cat_id")
    @Mapping(source = "cat_name", target = "cat_name")
    @Mapping(source = "email", target = "email")
    CosmoCatEntryDto toCosmoCatEntryDto(CosmoCat cat);
}
