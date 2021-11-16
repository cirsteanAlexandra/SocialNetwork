package Controller.NewController;

import Domain.User;
import Repository.UserRepo;

import java.util.List;

public class UserController extends Controller<Long, User> {

    public UserController(UserRepo rep) {
        super.repo=rep;
    }


    /**
     * Adds a friend to an user
     * @param user the user that has a new friend
     * @param username the username to be added
     */
    public void addFriend(User user,String username){
        user.addFriend(username);
    }
    /**
     * Removess a friend from an user
     * @param user the user that has no longer that friend
     * @param username the username that has to be removed
     */
    public void removeFriend(User user,String username){
        user.removeFriend(username);
    }

    /**
     * Removes the user forom all of his friends
     * @param username the user that needs to be removed
     */
    public void removeUserFromAllFriends(String username){
        for (User el: (List<User>)repo.getAll()){
            el.removeFriend(username);
        }
    }

}
