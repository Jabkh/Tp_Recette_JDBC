package org.example.DAO;

import org.example.entity.Etape;
import org.example.util.DatabaseManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EtapeDAO extends BaseDAO<Etape> {
    @Override
    public Etape save(Etape element) throws SQLException {
        try {
            connection = DatabaseManager.getConnection();
            request = "INSERT INTO etape (description) VALUES (?)";
            statement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getDescription());
            int row = statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (row != 1) {
                throw new SQLException();
            }
            if (resultSet.next()) {
                element.setId_etape(resultSet.getInt(1));
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
    public boolean delete(Etape element) throws SQLException {
        return false;
    }

    @Override
    public Etape update(Etape element) throws SQLException {
        return null;
    }

    @Override
    public Etape get(int id) throws SQLException {
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
            System.out.println(e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    @Override
    public List<Etape> get() throws SQLException {
        try {
            List<Etape> etapes = new ArrayList<>();
            connection = DatabaseManager.getConnection();
            request = "SELECT * FROM etape";
            statement = connection.prepareStatement(request);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                etapes.add(Etape.builder()
                        .id_etape(resultSet.getInt("id_etape"))
                        .description(resultSet.getString("description"))
                        .build());
            }
            return etapes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        } finally {
            close();
        }
    }
}
