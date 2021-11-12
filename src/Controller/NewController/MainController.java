package Controller.NewController;


import Domain.Persone;
import Domain.Relationship;
import Domain.User;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.RelationshipRepoException;
import Utils.Exceptions.UserRepoException;

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
            Persone pers= contP.getByOther(user.getPers().getFirstName(),user.getPers().getLastName());
            if(pers==null) contP.add(user.getPers());
            else user.getPers().setId(pers.getId());
        }
        catch(Exception e){
        }
        finally{
            contU.add(user);
        }
        return true;
    }

    public boolean addRelationship(Relationship rel) {
        if(contU.getByOther(rel.getFirstUserName())==null || contU.getByOther(rel.getSecondUserName())==null )
        throw new EntityRepoException("A relationship is only applied between tow existing users\n");
        contR.add(rel);
        return true;
    }

    public boolean removeByUserId(Long id) {
        User user= contU.getById(id);
        if(user==null) throw new UserRepoException("There isnt an user with that if");
        contR.deleteAllRelationsByUsername(user.getUsername());
        contU.removeById(id);
        removeSinglePersoneFromUsers(user.getPers());
        return true;
    }

    public boolean removeByRelationshipId(Long id) {
        contR.removeById(id);
        return true;
    }

    public boolean removeUserByUsername(String username) {
        User user= contU.getByOther(username);
        if(user==null) throw new UserRepoException("There isnt an user with that username");
        contR.deleteAllRelationsByUsername(user.getUsername());
        contU.removeByOthers(username);
        removeSinglePersoneFromUsers(user.getPers());
        return true;
    }

    private boolean removeSinglePersoneFromUsers(Persone persone){
        boolean found=false;
        for(User user: contU.getAll()){
            if(user.getPers().equals(persone))found=true;
        }
        if(!found) contP.removeById(persone.getId());
        return found;
    }

    public boolean removeRelationshipByUsernames(String username1,String username2) {
        contR.removeByOthers(username1,username2);
        return true;
    }


    public User getUserById(Long id) {
        User user= contU.getById(id);
        if(user==null) throw new UserRepoException("There isnt an user with that id");
        Persone pers=contP.getById(user.getPers().getId());
        user.setPers(pers);
        return user;
    }

    public Relationship getRelationshipById(Long id) {
        Relationship rel = contR.getById(id);
        if(rel==null) throw new RelationshipRepoException("There isnt a relationship with that id");
        return rel;
    }

    public User getUserByOther(String username) {
        User user= contU.getByOther(username);
        if(user==null) throw new UserRepoException("There isnt an user with that username");
        Persone pers=contP.getById(user.getPers().getId());
        user.setPers(pers);
        return user;
    }

    public Relationship getRelationshipByOther(String username1,String username2) {
        Relationship rel = contR.getByOther(username1,username2);
        if(rel==null) throw new RelationshipRepoException("There isnt a relationship with that usernames");
        return rel;
    }

    public List<User> getAllUsers() {
        Map<String,User> listU= new HashMap<>();
        ///loading users
        for(User el:contU.getAll()){
            listU.put(el.getUsername(),el);
        }
        ///loading persones
        for (Map.Entry<String,User> el: listU.entrySet()){
            Persone pers=contP.getById(el.getValue().getPers().getId());
            el.getValue().setPers(pers);
        }
        ///loading friends
        for(Relationship rel: contR.getAll()){
            //for the first user
            User user1=listU.get(rel.getFirstUserName());
            user1.addFriend(rel.getSecondUserName());
            listU.put(user1.getUsername(),user1);

            //for the second user
            User user2=listU.get(rel.getSecondUserName());
            user2.addFriend(rel.getFirstUserName());
            listU.put(user2.getUsername(),user2);
        }
        ///creating list of users
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

    public int getNumberOfCommunities(){
        return contR.getNumberOfCommunities(contU.getSize());
    }

    public List<String> getTheMostSociableCommunity(){
        return contR.getTheMostSociableCommunity(contU.getSize());
    }
}
