package Repository;

import Domain.Entity;

import java.util.List;


public interface Repository<Id, E extends Entity<Id>> {
     /**
      * Saves an entity to repository
      * @param entity the object to be saved
      * @return true if it has been saved with succes, flse otherwise
      */
     boolean save(E entity);

     /**
      * Retrives the corespondent object of that id
      * @param id the id of the object to be found
      * @return the object that has that id or null if there is no object with that id
      */
     E get(Id id);

     /**
      * Replaces and Entity with that id with a new Entity
      * @param id the id of the object to be replased
      * @param entity the entity to be replased with
      * @return true if the entity has been updated with succes, false otherwise
      */
     boolean update (Id id,E entity);

     /**
      * Deletes the object from the repository with that id
      * @param id the id of the object to be deleted
      * @return true if it was deleted with succes, false otherwise
      */
     boolean delete (Id id);

     /**
      * Gives the current Number of entities stored in repository
      * @return the current Number of entities
      */
     int getSize();

     /**
      * Gives a list with the entities stored in repository
      * @return a list of entities
      */
     List<E> getAll();
}
