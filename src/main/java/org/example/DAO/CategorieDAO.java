package org.example.DAO;

import org.example.entity.Categorie;
import org.example.util.DatabaseManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO extends BaseDAO<Categorie> {
    @Override
    public Categorie save(Categorie element) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "INSERT INTO categorie (nom) VALUE (?)";
            statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getNom());
            int row = statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (row != 1) {
                throw new SQLException();
            }
            if (resultSet.next()) {
                element.setId_categorie(resultSet.getInt(1));
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
    public boolean delete(Categorie element) throws SQLException {
        return false;
    }

    @Override
    public Categorie update(Categorie element) throws SQLException {
        return null;
    }

    @Override
    public Categorie get(int id) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM categorie WHERE id_categorie = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Categorie.builder()
                        .id_categorie(resultSet.getInt("id_categorie"))
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
    public List<Categorie> get() throws SQLException {
        try {
            List<Categorie> categories = new ArrayList<>();
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM categorie";
            statement = connection.prepareStatement(request);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                categories.add(Categorie.builder()
                        .id_categorie(resultSet.getInt("id_categorie"))
                        .nom(resultSet.getString("nom"))
                        .build());
            }
            return categories;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        } finally {
            close();
        }
    }

    public List<Categorie> search(String searchValue) throws SQLException {
        try {
            List<Categorie> categories = new ArrayList<>();
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM categorie WHERE nom LIKE ?";
            String value = "%" + searchValue + "%";
            statement = connection.prepareStatement(request);
            statement.setString(1, value);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                categories.add(Categorie.builder()
                        .id_categorie(resultSet.getInt("id_categorie"))
                        .nom(resultSet.getString("nom"))
                        .build());
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }
}
