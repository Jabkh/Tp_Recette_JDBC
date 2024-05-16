package org.example.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientRecette {

    private int id_ingredientRecette;

    private int  ingredient_id;

    private int recette_id;
}

