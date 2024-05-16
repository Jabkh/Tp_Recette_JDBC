package org.example.Util.Ihm;

import org.example.DAO.IngredientDAO;
import org.example.DAO.IngredientRecetteDAO;
import org.example.entity.Ingredient;
import org.example.entity.IngredientRecette;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class IngredientIhm {

    private Scanner scanner;

    private IngredientDAO ingredientDAO;

    private IngredientRecetteDAO ingredientRecetteDAO;

    public IngredientIhm(Scanner scanner) {
        this.scanner = scanner;
        this.ingredientDAO = new IngredientDAO();
        this.ingredientRecetteDAO = new IngredientRecetteDAO();
    }

    public void start() {
        int entry;
        while (true) {
            System.out.println("--- Menu Ingrédient ---");
            System.out.println("1/ Créer un ingrédient");
            System.out.println("2/ Lier un ingrédient à une recette");
            System.out.println("3/ Afficher tous les ingrédients");
            entry = scanner.nextInt();
            scanner.nextLine();

            switch (entry) {
                case 1:
                    createIngredient();
                    break;
                case 2:
                    linkIngredientToRecette();
                    break;
                case 3:
                    showAll();
                    break;
                case 4:
                    addIngredientToRecipe();
                    break;
                case 5:
                    removeIngredientFromRecipe();
                    break;
                default:
                    return;
            }
        }
    }

    private void createIngredient() {
        System.out.println("-- Création d'un ingrédient --");
        System.out.println("Nom de l'ingrédient :");
        String name = scanner.nextLine();
        try {
            Ingredient ingredient = ingredientDAO.save(Ingredient.builder().nom(name).build());
            if (ingredient != null) {
                System.out.println("Ingrédient créé : " + ingredient);
            } else {
                System.out.println("Erreur lors de la création");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void linkIngredientToRecette() {
        System.out.println("-- Lier un ingrédient à une recette --");
        System.out.println("ID de l'ingrédient :");
        int id_ingredient = scanner.nextInt();
        scanner.nextLine();
        System.out.println("ID de la recette :");
        int id_recette = scanner.nextInt();
        scanner.nextLine();

        try {
            IngredientRecette recetteIngredient = ingredientRecetteDAO.save(
                    IngredientRecette.builder().ingredient_id(id_ingredient).recette_id(id_recette).build()
            );
            if (recetteIngredient != null) {
                System.out.println("Liaison créée : " + recetteIngredient);
            } else {
                System.out.println("Erreur lors de la création de la liaison");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addIngredientToRecipe() {
        System.out.println("-- Ajouter un ingrédient à une recette --");
        System.out.println("ID de la recette :");
        int recipeId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nom de l'ingrédient à ajouter :");
        String ingredientName = scanner.nextLine();

        try {
            // Vérifier si l'ingrédient existe déjà dans la base de données
            Ingredient existingIngredient = ingredientDAO.getByNom(ingredientName);
            if (existingIngredient == null) {
                // Si l'ingrédient n'existe pas, le créer
                existingIngredient = ingredientDAO.save(Ingredient.builder().nom(ingredientName).build());
            }

            // Ajouter l'ingrédient à la recette
            IngredientRecette recetteIngredient = ingredientRecetteDAO.save(
                    IngredientRecette.builder().ingredient_id(existingIngredient.getId_ingredient()).recette_id(recipeId).build()
            );
            if (recetteIngredient != null) {
                System.out.println("Ingrédient ajouté à la recette : " + existingIngredient);
            } else {
                System.out.println("Erreur lors de l'ajout de l'ingrédient à la recette");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeIngredientFromRecipe() {
        System.out.println("-- Supprimer un ingrédient d'une recette --");
        System.out.println("ID de la recette :");
        int recipeId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("ID de l'ingrédient à supprimer :");
        int ingredientId = scanner.nextInt();
        scanner.nextLine();

        try {
            // Supprimer l'ingrédient de la recette
            boolean removed = ingredientRecetteDAO.delete(IngredientRecette.builder()
                    .ingredient_id(ingredientId)
                    .recette_id(recipeId)
                    .build());

            if (removed) {
                System.out.println("Ingrédient supprimé de la recette avec succès");
            } else {
                System.out.println("Erreur lors de la suppression de l'ingrédient de la recette");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void showAll() {
        System.out.println("--- Afficher tous les ingrédients ---");
        try {
            List<Ingredient> ingredients = ingredientDAO.get();
            for (Ingredient ingredient : ingredients) {
                System.out.println(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
