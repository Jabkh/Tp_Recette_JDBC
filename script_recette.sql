use Gestion_Recette;

-- drop tables ingredient_recette,Ingredient,Etape,Categorie,Commentaire,Recette;

CREATE TABLE Ingredient(
   Id_Ingredient INT AUTO_INCREMENT,
   nom VARCHAR(50),
   PRIMARY KEY(Id_Ingredient)
);

CREATE TABLE Etape(
   Id_Etape INT AUTO_INCREMENT,
   description VARCHAR(50),
   PRIMARY KEY(Id_Etape)
);

CREATE TABLE Categorie(
   Id_Categorie INT AUTO_INCREMENT,
   nom VARCHAR(50),
   PRIMARY KEY(Id_Categorie)
);

CREATE TABLE Commentaire(
   Id_Commentaire INT AUTO_INCREMENT,
   description VARCHAR(50),
   PRIMARY KEY(Id_Commentaire)
);

CREATE TABLE Recette(
   Id_Recette INT AUTO_INCREMENT,
   tempsPrep int,
   tempsCuisson int,
   difficulte INT,
   Etape_Id INT,
   Commentaire_id INT,
   Categorie_id INT NOT NULL,
   PRIMARY KEY(Id_Recette),
   FOREIGN KEY(Etape_Id) REFERENCES Etape(Id_Etape) on delete cascade,
   FOREIGN KEY(Commentaire_id) REFERENCES Commentaire(Id_Commentaire) on delete cascade,
   FOREIGN KEY(Categorie_id) REFERENCES Categorie(Id_Categorie) on delete cascade
);

CREATE TABLE ingredient_recette(
   Ingredient_id INT,
   Recette_id INT,
   Id_IngredientRecette INT AUTO_INCREMENT,
   PRIMARY KEY(Id_IngredientRecette),
   FOREIGN KEY(Ingredient_id) REFERENCES Ingredient(Id_Ingredient) on delete cascade,
   FOREIGN KEY(Recette_id) REFERENCES Recette(Id_Recette) on delete cascade
);

