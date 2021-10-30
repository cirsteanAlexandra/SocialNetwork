package Controller;

import Domain.Entity;
import Domain.User;
import Repository.MemoryRepo;
import Utils.Exceptions.EntityRepoException;

public abstract class Controller<T,E extends Entity> {
    MemoryRepo repo;

    public boolean add(E entity){
        if(repo.save(entity)==false)
            throw new EntityRepoException("The user is already in data base\n");
        return true;
    }

    public boolean removeById(T id){
        if (!repo.delete(id))
            throw new EntityRepoException("There in not a user with that id\n");
        return true;
    }

    public abstract boolean removeByOthers(String... others);

    public E getById(T id){
        return (E) repo.get(id);
    }

    public abstract E getByOther(String... others);

}
