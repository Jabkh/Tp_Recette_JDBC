package org.example.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Etape {

    private int id_etape;

    private String description;
}

