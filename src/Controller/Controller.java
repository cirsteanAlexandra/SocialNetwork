package Controller;

import Domain.User;
import Repository.Repository;
import Repository.UserMemoryRepo;
import Repository.MemoryRepo;
import Utils.Exceptions.UserRepoException;

public class Controller {
    MemoryRepo repoUser;

    public Controller(MemoryRepo rep) {
        this.repoUser = rep;
    }

    public boolean addUser(User user){
        if(repoUser.save(user)==false)
            throw new UserRepoException("The user is already in data base\n");
        return true;
    }

    public boolean removeUserById(Long id){
        if (!repoUser.delete(id))
            throw new UserRepoException("There in not a user with that id\n");
        return true;
    }

    public boolean removeUserByUsername(String username){
        User user= (User)repoUser.getByOther(username);
        if(user==null)
            throw new UserRepoException("There in not a user with that id\n");
        repoUser.delete(user.getId());
        return true;
    }

    public User getUserById(Long id){
        return (User)repoUser.get(id);
    }

    public User getUserByUsername(String username){
        return (User)repoUser.getByOther(username);
    }
}
