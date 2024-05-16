package org.example.Util.Ihm;


import java.util.Scanner;

    public class Ihm {
        private Scanner scanner;

        private RecetteIhm recetteIhm;

        private IngredientIhm ingredientIhm;

        public Ihm() {
            scanner = new Scanner(System.in);
            recetteIhm = new RecetteIhm(scanner);
            ingredientIhm = new IngredientIhm(scanner);
        }

        public void start() {
            int entry;
            while (true) {
                System.out.println("--- Application gestion de recettes ---");
                System.out.println("1/ Menu recettes");
                System.out.println("2/ Menu ingrédients");
                entry = scanner.nextInt();
                scanner.nextLine();

                switch (entry) {
                    case 1:
                        recetteIhm.start();
                        break;
                    case 2:
                        ingredientIhm.start();
                        break;
                    default:
                        return;
                }
            }
        }
    }
