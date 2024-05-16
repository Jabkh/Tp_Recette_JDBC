package org.example.DAO;

import org.example.entity.Categorie;
import org.example.entity.Etape;
import org.example.entity.Ingredient;
import org.example.entity.Recette;
import org.example.util.DatabaseManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecetteDAO extends BaseDAO<Recette> {

    private IngredientDAO ingredientDAO;

    private EtapeDAO etapeDAO;

    private CategorieDAO categorieDAO;

    private CommentaireDAO commentaireDAO;

    @Override
    public Recette save(Recette element) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "INSERT INTO recette (nom, tempsPrep, tempsCuisson, difficulte, ingredient_id, categorie_id, etape_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getNom());
            statement.setInt(2, element.getTempsPrep());
            statement.setInt(3, element.getTempsCuisson());
            statement.setInt(4, element.getDifficulte());
            statement.setInt(5, element.getIngredient().getId_ingredient());
            statement.setInt(6, element.getCategorie().getId_categorie());
            statement.setInt(7, element.getEtape().getId_etape());
            int row = statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (row != 1) {
                throw new SQLException();
            }
            if (resultSet.next()) {
                element.setId_recette(resultSet.getInt(1));
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
    public boolean delete(Recette element) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "DELETE FROM recette WHERE id_recette = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, element.getId_recette());
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
    public Recette update(Recette element) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "UPDATE recette SET nom = ?, tempsPrep = ?, tempsCuisson = ?, difficulte = ?, ingredient_id = ?, categorie_id = ?, etape_id = ? WHERE id_recette = ?";
            statement = connection.prepareStatement(request);
            statement.setString(1, element.getNom());
            statement.setInt(2, element.getTempsPrep()); // En minutes
            statement.setInt(3, element.getTempsCuisson()); // En minutes
            statement.setInt(4, element.getDifficulte());
            statement.setInt(5, element.getIngredient().getId_ingredient());
            statement.setInt(6, element.getCategorie().getId_categorie());
            statement.setInt(7, element.getEtape().getId_etape());
            statement.setInt(8, element.getId_recette());
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
    public Recette get(int id) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM recette WHERE id_recette = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Recette.builder()
                        .id_recette(resultSet.getInt("id_recette"))
                        .nom(resultSet.getString("nom"))
                        .tempsPrep(resultSet.getInt("tempsPrep"))
                        .tempsCuisson(resultSet.getInt("tempsCuisson"))
                        .difficulte(resultSet.getInt("difficulte"))
                        .ingredient(getIngredientById(resultSet.getInt("ingredient_id")))
                        .categorie(getCategorieById(resultSet.getInt("categorie_id")))
                        .etape(getEtapeById(resultSet.getInt("etape_id")))
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
    public List<Recette> get() throws SQLException {
        try {
            List<Recette> recettes = new ArrayList<>();
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM recette";
            statement = connection.prepareStatement(request);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recettes.add(Recette.builder()
                        .id_recette(resultSet.getInt("id_recette"))
                        .nom(resultSet.getString("nom"))
                        .tempsPrep(resultSet.getInt("tempsPrep")) // En minutes
                        .tempsCuisson(resultSet.getInt("tempsCuisson")) // En minutes
                        .difficulte(resultSet.getInt("difficulte"))
                        .ingredient(getIngredientById(resultSet.getInt("ingredient_id")))
                        .categorie(getCategorieById(resultSet.getInt("categorie_id")))
                        .etape(getEtapeById(resultSet.getInt("etape_id")))
                        .build());
            }
            return recettes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        } finally {
            close();
        }
    }

    public List<Recette> search(String searchField, String value) throws SQLException {
        try {
            List<Recette> recettes = new ArrayList<>();
            connection = DatabaseManager.getConnection();
            if ("difficulte".equalsIgnoreCase(searchField)) {
                request = "SELECT * FROM recette WHERE difficulte = ?";
            } else {
                request = "SELECT * FROM recette WHERE " + searchField + " LIKE ?";
                value = "%" + value + "%";
            }
            statement = connection.prepareStatement(request);
            statement.setString(1, value);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recettes.add(Recette.builder()
                        .id_recette(resultSet.getInt("id_recette"))
                        .nom(resultSet.getString("nom"))
                        .tempsPrep(resultSet.getInt("tempsPrep"))
                        .tempsCuisson(resultSet.getInt("tempsCuisson"))
                        .difficulte(resultSet.getInt("difficulte"))
                        .ingredient(getIngredientById(resultSet.getInt("ingredient_id")))
                        .categorie(getCategorieById(resultSet.getInt("categorie_id")))
                        .etape(getEtapeById(resultSet.getInt("etape_id")))
                        .build());
            }
            return recettes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    private Ingredient getIngredientById(int id) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM categorie WHERE id_ingredient() = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Ingredient.builder()
                        .id_ingredient(resultSet.getInt("id_categorie"))
                        .nom(resultSet.getString("nom"))
                        .build();
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    private Categorie getCategorieById(int id) throws SQLException {
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
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }


    private Etape getEtapeById(int id) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM etape WHERE id_etape = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Etape.builder()
                        .id_etape(resultSet.getInt("id_etape"))
                        .description(resultSet.getString("description"))
                        .build();
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    public List<Recette> getAllRecetteByCategorieId(int id) throws SQLException {
        List<Recette> recettes = new ArrayList<>();
        try {
            connection = DatabaseManager.getConnection();
            request = "SELECT r.id_recette AS recetteId, r.nom AS recetteNom, r.temps_prep AS recetteTempsPrep, r.temps_cuisson AS recetteTempsCuisson, r.difficulte AS recetteDifficulte FROM recette AS r INNER JOIN recette_categorie AS rc ON rc.id_recette = r.id_recette WHERE rc.id_categorie = ?";
            statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Recette recette = Recette.builder()
                        .id_recette(resultSet.getInt("recetteId"))
                        .nom(resultSet.getString("recetteNom"))
                        .tempsPrep(resultSet.getInt("recetteTempsPrep"))
                        .tempsCuisson(resultSet.getInt("recetteTempsCuisson"))
                        .difficulte(resultSet.getInt("recetteDifficulte"))
                        .commentaire(commentaireDAO.get(resultSet.getInt("recetteId")))
                        .ingredient(ingredientDAO.get(resultSet.getInt("recetteId")))
                        .categorie(categorieDAO.get(id))
                        .etape(etapeDAO.get(resultSet.getInt("recetteId")))
                        .build();
                recettes.add(recette);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close();
        }
        return recettes;
    }


}
