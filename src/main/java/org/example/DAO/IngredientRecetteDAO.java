package org.example.DAO;

import org.example.entity.IngredientRecette;
import org.example.entity.Recette;
import org.example.util.DatabaseManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IngredientRecetteDAO extends BaseDAO<IngredientRecette> {

    @Override
    public IngredientRecette save(IngredientRecette element) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "INSERT INTO ingredient_recette (ingredient_id, recette_id) VALUES (?, ?)";
            statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, element.getIngredient_id());
            statement.setInt(2, element.getRecette_id());
            int row = statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (row != 1) {
                throw new SQLException();
            }
            if (resultSet.next()) {
                element.setId_ingredientRecette(resultSet.getInt(1));
            }
            connection.commit();
            return element;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
            return null;
        } finally {
            close();
        }
    }

    @Override
    public boolean delete(IngredientRecette element) throws SQLException {

    }

    @Override
    public IngredientRecette update(IngredientRecette element) throws SQLException {

    }

    @Override
    public IngredientRecette get(int id) throws SQLException {

    }

    @Override
    public List<IngredientRecette> get() throws SQLException {

    }

    public List<Recette> getRecetteByIngredientId(int id) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            List<Recette> recettes = new ArrayList<>();
            request = "SELECT r.id as recetteId, r.nom as recetteNom, r.tempsPrep as recetteTempsPrep, r.tempsCuisson as recetteTempsCuisson, r.difficulte as recetteDifficulte FROM recette as r INNER JOIN ingredient_recette as ir ON ir.recette_id = r.id WHERE ir.ingredient_id = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recettes.add(Recette.builder()
                        .id_recette(resultSet.getInt("recetteId"))
                        .nom(resultSet.getString("recetteNom"))
                        .tempsPrep(resultSet.getInt("recetteTempsPrep"))
                        .tempsCuisson(resultSet.getInt("recetteTempsCuisson"))
                        .difficulte(resultSet.getInt("recetteDifficulte"))
                        .build());
            }
            return recettes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

}
