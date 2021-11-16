package Repository.Db;

import Domain.Entity;
import Repository.Repository;
import Utils.Exceptions.EntityRepoException;

import java.sql.*;
import java.util.List;

public abstract class DbRepoId<Id,E extends Entity<Id>>implements Repository<Id,E> {
    private String url;
    private String username;
    private String password;
    protected String sql;

    /**
     * Basic constructor of a Db Repository
     * @param url to the database
     * @param username the user of the database
     * @param password the password of the user
     */
    public DbRepoId(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Saves an entity to repository
     * @param entity the object to be saved
     * @return true if it has been saved with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
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

    /**
     * Retrieves the corespondent object with that id
     * @param id the id of the object to be found
     * @return the object that has that id or null if there is no object with that id
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
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

    /**
     * Replaces an Entity with that id with a new Entity
     * @param id the id of the object to be replaced
     * @param entity the entity to be replaced with
     * @return true if the entity has been updated with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
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

    /**
     * Deletes the object from the repository with that id
     * @param id the id of the object to be deleted
     * @return true if it was deleted with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
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

    /**
     * Deletes all the data from the repository
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    protected void deleteAll(){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * Gives the current Number of entities stored in repository
     * @return the current Number of entities
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
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

    /**
     * Gives a list with all the entities stored in repository
     * @return a list of entities
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public List<E> getAll() {
        List<E> list ;
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

    /**
     * Gives a list with all the ids store din repository
     * @return a list of ids
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public List<Id> getAllIds() {
        List<Id> list ;
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

    /**
     * Deletes all the data from the database tables
     * This function should be used only in tests
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    public void restoreToDefault(){
        deleteAll();
    }

    /**
     * Generates an id for an entity
     */
    @Override
    public abstract Id generateId();


    /**
     * Checks if it is an object stored with some distinguishable components
     * @param other a list of string with distinguishable components
     * @return the object to be found or null if there is no object with that components
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public E getByOther(String... other){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            setGetOtherStatement(ps,other);
            ResultSet resultSet = ps.executeQuery();
            return getGetOtherStatement(resultSet);
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * This function converts the data from a result statement into an integer that
     * represents the total number of entities stored in repository
     * @param ps the result statements which contains the date from request
     * @return an integer which represents the total number of entities stored in repository
     * @throws SQLException when there are problems with the result statements
     */
    protected int getSizeStatement(ResultSet ps) throws SQLException{
        int size=0;
        if (ps.next()) {
            size = ps.getInt("size");
        }
        return size;
    }

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving an entity from the repository with other distinguishable
     * components
     * @param ps the result statements which contains the date from request
     * @return an entity that is stored in repository
     * @throws SQLException when there are problems with the result statements
     */
    protected E getGetOtherStatement(ResultSet ps) throws SQLException{
        return getGetStatement(ps);
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for saving an entity to repository
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param entity the entity to be stored in repository
     * @throws SQLException when there are problems with the prepared statements
     */
    protected abstract void setSaveStatement(PreparedStatement ps,E entity) throws SQLException;

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving an entity from the repository
     * @param ps the result statements which contains the date from request
     * @return an entity that is stored in repository
     * @throws SQLException when there are problems with the result statements
     */
    protected abstract E getGetStatement(ResultSet ps) throws SQLException;

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the ids from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the ids from a table
     * @throws SQLException when there are problems with the result statements
     */
    protected abstract List<Id> getAllIdStatement(ResultSet ps) throws SQLException;

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the entities from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the entities from a table
     * @throws SQLException when there are problems with the result statements
     */
    protected abstract List<E> getAllStatement(ResultSet ps) throws SQLException;

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for deleting an entity from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the entity to be deleted from the repository
     * @throws SQLException when there are problems with the prepared statements
     */
    protected abstract void setDeleteStatement(PreparedStatement ps,Id id) throws SQLException;

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for updating an entity from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the entity to be modified from the repository
     * @param entity the entity to be replaces with
     * @throws SQLException when there are problems with the prepared statements
     */
    protected abstract void setUpdateStatement(PreparedStatement ps,Id id,E entity) throws SQLException;

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for getting an entity using other distinguishable components
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param other an array of strings with distinguishable components
     * @throws SQLException when there are problems with the prepared statements
     */
    protected abstract void setGetOtherStatement(PreparedStatement ps, String... other)throws SQLException;
}

