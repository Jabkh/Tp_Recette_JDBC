package org.example.DAO;

import org.example.entity.Ingredient;
import org.example.util.DatabaseManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO extends BaseDAO<Ingredient> {

    @Override
    public Ingredient save(Ingredient element) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "INSERT INTO ingredient (nom) VALUE (?)";
            statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getNom());
            int row = statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (row != 1) {
                throw new SQLException();
            }
            if (resultSet.next()) {
                element.setId_ingredient(resultSet.getInt(1));
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
    public boolean delete(Ingredient element) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "DELETE FROM ingredient WHERE id_ingredient = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, element.getId_ingredient());
            int row = statement.executeUpdate();
            if (row != 1) {
                throw new SQLException();
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
            return false;
        } finally {
            close();
        }
    }

    @Override
    public Ingredient update(Ingredient element) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "UPDATE ingredient SET nom = ? WHERE id_ingredient = ?";
            statement = connection.prepareStatement(request);
            statement.setString(1, element.getNom());
            statement.setInt(2, element.getId_ingredient());
            int row = statement.executeUpdate();
            if (row != 1) {
                throw new SQLException();
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
    public Ingredient get(int id) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM ingredient WHERE id_ingredient = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Ingredient.builder()
                        .id_ingredient(resultSet.getInt("id_ingredient"))
                        .nom(resultSet.getString("nom"))
                        .build();
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    public Ingredient getByNom(String nom) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM ingredient WHERE nom = ?";
            statement = connection.prepareStatement(request);
            statement.setString(1, nom);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Ingredient.builder()
                        .id_ingredient(resultSet.getInt("id_ingredient"))
                        .nom(resultSet.getString("nom"))
                        .build();
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close();
        }
    }
    @Override
    public List<Ingredient> get() throws SQLException {
        try {
            List<Ingredient> ingredients = new ArrayList<>();
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM ingredient";
            statement = connection.prepareStatement(request);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ingredients.add(Ingredient.builder()
                        .id_ingredient(resultSet.getInt("id_ingredient"))
                        .nom(resultSet.getString("nom"))
                        .build());
            }
            return ingredients;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        } finally {
            close();
        }
    }
}
