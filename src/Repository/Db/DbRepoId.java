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
    int size;
    String sql;

    public DbRepoId(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.size=0;
    }

    @Override
    public boolean save(E entity) {
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
             return setGetStatement(resultSet,id);
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
            throw new EntityRepoException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Id id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            setDeleteStatement(ps,id);
            ps.executeUpdate();
            size--;
            return true;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public List<E> getAll() {
        List<E> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
             list=setAllStatement(resultSet);
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
             list=setAllIdStatement(resultSet);
             return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public abstract Id generateId();


    @Override
    public E getByOther(String... other){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
            return setGetOtherStatement(resultSet,other);
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    };

    protected abstract void setStatement(PreparedStatement st);

    protected abstract void setSQLString();

    protected abstract void setSaveStatement(PreparedStatement ps,E entity);

    protected abstract E setGetStatement(ResultSet ps,Id id);

    protected abstract E setGetOtherStatement(ResultSet ps,String... other);

    protected abstract List<Id> setAllIdStatement(ResultSet ps);

    protected abstract List<E> setAllStatement(ResultSet ps);

    protected abstract void setDeleteStatement(PreparedStatement ps,Id id);

    protected abstract void setUpdateStatement(PreparedStatement ps,Id id,E entity);
}

