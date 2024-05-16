package org.example.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Recette {

    private int id_recette;

    private String nom;

    private int tempsPrep;

    private int tempsCuisson;

    private int difficulte;

    private Commentaire commentaire;

    private Ingredient ingredient;

    private Categorie categorie;

    private Etape etape;

}

