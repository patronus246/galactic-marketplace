package org.example.galacticmarketplace.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Category {
    UUID id;
    String name;
}