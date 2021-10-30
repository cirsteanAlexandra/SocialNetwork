package Controller;

import Domain.User;
import Repository.UserMemoryRepo;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.UserRepoException;

public class UserController extends Controller<Long, User>{

    public UserController(UserMemoryRepo rep) {
        super.repo=rep;
    }

    @Override
    public boolean removeByOthers(String... others) {
        User user= (User) repo.getByOther(others);
        if(user==null)
            throw new UserRepoException("There in not a user with that id\n");
        repo.delete(user.getId());
        return true;
    }

    @Override
    public User getByOther(String... others) {
        return (User) repo.getByOther(others);
    }
}
