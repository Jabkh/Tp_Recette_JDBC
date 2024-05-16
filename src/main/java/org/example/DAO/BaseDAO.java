package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {

    protected PreparedStatement statement;
    protected String request;
    protected ResultSet resultSet;
    protected Connection connection;

    public BaseDAO (){

    }

    public abstract T save (T element) throws SQLException;
    public abstract boolean delete (T element) throws SQLException;
    public abstract T update (T element) throws SQLException;
    public abstract T get (int id) throws SQLException;
    public abstract List<T> get () throws SQLException;

    protected void close () throws SQLException{
        if(!resultSet.isClosed()){
            resultSet.close();
        }
        if(!statement.isClosed()){
            statement.close();
        }
        if (!connection.isClosed()){
            connection.close();
        }
    }

}
