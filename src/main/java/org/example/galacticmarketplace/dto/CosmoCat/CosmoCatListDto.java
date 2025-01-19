package org.example.galacticmarketplace.dto.CosmoCat;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CosmoCatListDto {
    List<CosmoCatEntryDto> cosmoCats;
}