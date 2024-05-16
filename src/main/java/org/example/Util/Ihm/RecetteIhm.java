package org.example.Util.Ihm;

import org.example.DAO.CommentaireDAO;
import org.example.DAO.EtapeDAO;
import org.example.DAO.IngredientDAO;
import org.example.DAO.RecetteDAO;
import org.example.entity.Commentaire;
import org.example.entity.Etape;
import org.example.entity.Ingredient;
import org.example.entity.Recette;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RecetteIhm {
    private Scanner scanner;
    private RecetteDAO recetteDAO;
    private CommentaireDAO commentaireDAO;
    private IngredientDAO ingredientDAO;
    private EtapeDAO etapeDAO;

    public RecetteIhm(Scanner scanner) {
        this.scanner = scanner;
        recetteDAO = new RecetteDAO();
        commentaireDAO = new CommentaireDAO();
        ingredientDAO = new IngredientDAO();
        etapeDAO = new EtapeDAO();
    }

    public void start() {
        int entry;
        while (true) {
            System.out.println("--- Menu Recette ---");
            System.out.println("1/ Créer une recette");
            System.out.println("2/ Récupérer les recettes");
            System.out.println("3/ Rechercher une recette");
            entry = scanner.nextInt();
            scanner.nextLine();

            switch (entry) {
                case 1:
                    createRecette();
                    break;
                case 2:
                    getAllRecettes();
                    break;
//                case 3:
//                    searchRecette();
//                    break;
                default:
                    return;
            }
        }
    }

    private void createRecette() {
        System.out.println("-- Création de recette --");
        System.out.println("Nom de la recette :");
        String nom = scanner.nextLine();
        System.out.println("Temps de préparation :");
        int tempsPrep = scanner.nextInt();
        System.out.println("Temps de cuisson :");
        int tempsCuisson = scanner.nextInt();
        System.out.println("Difficulté (1 à 5) :");
        int difficulte = scanner.nextInt();
        scanner.nextLine();


        Recette recette = Recette.builder()
                .nom(nom)
                .tempsPrep(tempsPrep)
                .tempsCuisson(tempsCuisson)
                .difficulte(difficulte)
                .build();

        try {
            Recette recetteCreated = recetteDAO.save(recette);
            if (recetteCreated != null) {
                System.out.println("Recette créée " + recetteCreated);
            } else {
                System.out.println("Erreur lors de la création");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllRecettes() {
        System.out.println("-- Récupération des recettes --");
        try {
            List<Recette> recettes = recetteDAO.get();
            int i = 0;
            for (Recette recette : recettes) {
                List<Commentaire> commentaires = (List<Commentaire>) commentaireDAO.get(recette.getId_recette());
                List<Ingredient> ingredients = (List<Ingredient>) ingredientDAO.get(recette.getId_recette());
                List<Etape> etapes = (List<Etape>) etapeDAO.get(recette.getId_recette());
                i++;
                System.out.println(i + "/ " + recette);
                for (Commentaire commentaire : commentaires) {
                    System.out.println(commentaire);
                }
                for (Ingredient ingredient : ingredients) {
                    System.out.println(ingredient);
                }
                for (Etape etape : etapes) {
                    System.out.println(etape);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    private void searchRecette() {
//        System.out.println("-- Recherche de recettes --");
//        System.out.println("Critère de recherche :");
//        System.out.println("1/Nom 2/Difficulté 3/Temps de préparation 4/Temps de cuisson ");
//        int value = scanner.nextInt();
//        scanner.nextLine();
//        String searchCase = "";
//        switch (value) {
//            case 1:
//                searchCase = SearchCase.NAME.toString();
//                break;
//            case 2:
//                searchCase = SearchCase.DIFFICULTE.toString();
//                break;
//            case 3:
//                searchCase = SearchCase.TEMPS_PREP.toString();
//                break;
//            case 4:
//                searchCase = SearchCase.TEMPS_CUISSON.toString();
//                break;
//        }
//
//        System.out.println("Valeur de la recherche :");
//        String searchValue = scanner.nextLine();
//
//        try {
//            List<Recette> recettes = recetteDAO.search(searchCase, searchValue);
//            int i = 0;
//            for (Recette recette : recettes) {
//                List<Ingredient> Ingredients = ingredientDAO.search(researchCase,researchValue);
//                recette.setCommentaire(commentaireDAO.get(recette.getId_recette()));
//                recette.setIngredient(ingredientDAO.get(recette.getId_recette()));
//                recette.setEtape(etapeDAO.get(recette.getId_recette()));
//                i++;
//                System.out.println(i + "/ " + recette);
//                for (Ingredient ingredient : Ingredients) {
//                    System.out.println(ingredient);
//                }
//
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
