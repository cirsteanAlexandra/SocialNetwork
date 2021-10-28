package Repository;

import Domain.Entity;
import Domain.Persone;

import java.util.List;


public interface Repository<Id, E extends Entity<Id>> {
     boolean save(E entity);
     E get(Id id);
     boolean update (Id id,E entity);
     boolean delete (Id id);
     int getSize();
     List<E> getAll();
}
