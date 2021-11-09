package Controller.NewController;

import Controller.OldController.RelationshipController;
import Controller.OldController.UserController;
import Domain.Persone;
import Domain.Relationship;
import Domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {
    UserController contU;
    RelationshipController contR;
    PersoneController contP;

    public MainController(UserController contU, RelationshipController contR,PersoneController contP) {
        this.contU = contU;
        this.contR = contR;
        this.contP = contP;
    }

    public boolean addUser(User user) {
        try {
            contP.add(user.getPers());
        }
        catch(Exception e){}
        finally{
            contU.add(user);
        }
        return true;
    }

    public boolean addRelationship(Relationship rel) {
        contR.add(rel);
        return true;
    }

    public boolean removeByUserId(Long id) {
        User user= contU.getById(id);
        contU.removeById(id);
        contR.deleteAllRelationsByUsername(user.getUsername());
        return true;
    }

    public boolean removeByRelationshipId(Long id) {
        contR.removeById(id);
        return true;
    }

    public boolean removeUserByUsername(String username) {
        User user= contU.getByOther(username);
        contU.removeByOthers(username);
        contR.deleteAllRelationsByUsername(user.getUsername());
        return true;
    }

    public boolean removeRelationshipByUsernames(String username1,String username2) {
        contR.removeByOthers(username1,username2);
        return true;
    }


    public User getUserById(Long id) {
        User user= contU.getById(id);
        Persone pers=contP.getById(user.getPers().getId());
        user.setPers(pers);
        return user;
    }

    public Relationship getRelationshipById(Long id) {
        Relationship rel = contR.getById(id);
        return rel;
    }

    public User getUserByOther(String username) {
        User user= contU.getByOther(username);
        Persone pers=contP.getById(user.getPers().getId());
        user.setPers(pers);
        return user;
    }

    public Relationship getRelationshipByOther(String username1,String username2) {
        Relationship rel = contR.getByOther(username1,username2);
        return rel;
    }

    public List<User> getAllUsers() {
        Map<String,User> listU= new HashMap<>();
        for(User el:contU.getAll()){
            listU.put(el.getUsername(),el);
        }
        for (Map.Entry<String,User> el: listU.entrySet()){
            Persone pers=contP.getById(el.getValue().getPers().getId());
            el.getValue().setPers(pers);
        }
        for(Relationship rel: contR.getAll()){
            listU.get(rel.getFirstUserName()).addFriend(rel.getSecondUserName());
            listU.get(rel.getSecondUserName()).addFriend(rel.getFirstUserName());
        }
        List<User> list= new ArrayList<>();
        for (Map.Entry<String,User> el: listU.entrySet()){
            list.add(el.getValue());
        }
        return list;
    }

    public List<Relationship> getAllRelationships() {
        return contR.getAll();
    }

    public int getUserSize() {
        return contU.getSize();
    }

    public int getRelationshipSize() {
        return contR.getSize();
    }
}
