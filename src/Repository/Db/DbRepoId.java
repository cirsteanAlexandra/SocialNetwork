package Repository.Db;

import Domain.Entity;
import Repository.Repository;
import Utils.Exceptions.EntityRepoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DbRepoId<Id,E extends Entity<Id>>implements Repository<Id,E> {
    private String url;
    private String username;
    private String password;
    protected String sql;

    public DbRepoId(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean save(E entity) {
        //System.out.println(entity.toString());
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
             setSaveStatement(ps,entity);
             ps.executeUpdate();
             return true;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    @Override
    public E get(Id id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
             return getGetStatement(resultSet);
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    @Override
    public boolean update(Id id, E entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            setUpdateStatement(ps,id,entity);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityRepoException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Id id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            setDeleteStatement(ps,id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    protected void deleteAll(){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    @Override
    public int getSize() {
        int size=0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
            size=getSizeStatement(resultSet);
            return size;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public List<E> getAll() {
        List<E> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
             list=getAllStatement(resultSet);
             return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Id> getAllIds() {
        List<Id> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
             list=getAllIdStatement(resultSet);
             return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void restoreToDefault(){
        deleteAll();
    }

    @Override
    public abstract Id generateId();


    @Override
    public E getByOther(String... other){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);) {
            setGetOtherStatement(ps,other);
            ResultSet resultSet = ps.executeQuery();
            return getGetOtherStatement(resultSet);
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    };

    protected int getSizeStatement(ResultSet ps) throws SQLException{
        int size=0;
        if (ps.next()) {
            size = ps.getInt("size");
        }
        return size;
    }

    protected abstract void setSaveStatement(PreparedStatement ps,E entity) throws SQLException;

    protected abstract E getGetStatement(ResultSet ps) throws SQLException;

    protected E getGetOtherStatement(ResultSet ps) throws SQLException{
        return getGetStatement(ps);
    };

    protected abstract List<Id> getAllIdStatement(ResultSet ps) throws SQLException;

    protected abstract List<E> getAllStatement(ResultSet ps) throws SQLException;

    protected abstract void setDeleteStatement(PreparedStatement ps,Id id) throws SQLException;

    protected abstract void setUpdateStatement(PreparedStatement ps,Id id,E entity) throws SQLException;

    protected abstract void setGetOtherStatement(PreparedStatement ps, String... other)throws SQLException;
}

