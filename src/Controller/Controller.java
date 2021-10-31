package Controller;

import Domain.Entity;
import Domain.User;
import Repository.MemoryRepo;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.UserRepoException;

import java.util.List;

public abstract class Controller<T,E extends Entity> {
    MemoryRepo repo;

    public boolean add(E entity){
        if(repo.save(entity)==false)
            throw new EntityRepoException("This entity is already in data base\n");
        return true;
    }

    public boolean removeById(T id){
        if (!repo.delete(id))
            throw new EntityRepoException("There is not an entity with that id\n");
        return true;
    }

    public boolean removeByOthers(String... others){
        E entity= (E) repo.getByOther(others);
        if(entity==null)
            throw new EntityRepoException("There is not an entity with that id\n");
        repo.delete(entity.getId());
        return true;
    };

    public E getById(T id){
        return (E) repo.get(id);
    }

    public E getByOther(String... others){
        return (E) repo.getByOther(others);
    };

    public List<E> getAll(){
        return repo.getAll();
    }

}
