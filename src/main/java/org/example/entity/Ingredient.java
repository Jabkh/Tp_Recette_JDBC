package org.example.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ingredient {

    private int id_ingredient;

    private String nom;
}

