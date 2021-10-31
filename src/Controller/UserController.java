package Controller;

import Domain.User;
import Repository.UserMemoryRepo;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.UserRepoException;

public class UserController extends Controller<Long, User>{

    public UserController(UserMemoryRepo rep) {
        super.repo=rep;
    }


    public void addFriend(User user,String username){
        user.addFriend(username);
    }

    public void removeFriend(User user,String username){
        user.removeFriend(username);
    }
}
