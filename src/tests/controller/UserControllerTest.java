package tests.controller;

import Controller.OldController.UserController;
import Domain.Persone;
import Domain.User;
import Repository.File.UserFileRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest {


    @Test
    void add() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        try {
            cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    void addException() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }

        try {
            cont.add(new User(2L, "maimuta", new Persone("Marieta", "Gazela")));
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void removeById() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.removeById(1L);
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    void removeByIdException() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.removeById(2L);
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void removeByOthers() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.removeByOthers("maimuta");
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    void removeByOthersException() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.removeByOthers("mahala");
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getById() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        assertTrue(cont.getById(1L).getUsername().equals("maimuta"));
        assertTrue(cont.getById(3L)==null);
    }

    @Test
    void getByOther() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        assertTrue(cont.getByOther("elenamea").getPers().getFirstName().equals("Marieta"));
        assertTrue(cont.getByOther("heeihriur")==null);
    }

    @Test
    void getAll() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        cont.add(new User(3L, "academicul", new Persone("varza", "murata")));
        List<User> list=cont.getAll();
        assertEquals(list.size(),3);

        assertEquals(list.get(0).getUsername(),"maimuta");
        assertEquals(list.get(1).getUsername(),"elenamea");
        assertEquals(list.get(2).getUsername(),"academicul");

    }

    @Test
    void getSize() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));

        assertEquals(cont.getSize(),2);
        cont.add(new User(3L, "academicul", new Persone("varza", "murata")));
        assertEquals(cont.getSize(),3);
    }

    @Test
    void addFriend() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        cont.addFriend(cont.getById(1L),"elenamea");
        assertEquals(cont.getById(1L).getFriendsList().size(),1);
        assertEquals(cont.getById(1L).getFriendsList().get(0),"elenamea");
        assertEquals(cont.getById(2L).getFriendsList().size(),0);
    }

    @Test
    void removeFriend() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        cont.addFriend(cont.getById(1L),"elenamea");
        cont.removeFriend(cont.getById(1L),"elenamea");
        assertEquals(cont.getById(1L).getFriendsList().size(),0);
    }

    @Test
    void removeUserFromAllFriends() {
        UserFileRepo repo = new UserFileRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        cont.add(new User(3L, "academicul", new Persone("varza", "murata")));
        cont.addFriend(cont.getById(1L),"elenamea");
        cont.addFriend(cont.getById(3L),"elenamea");
        cont.removeUserFromAllFriends("elenamea");
        assertEquals(cont.getById(1L).getFriendsList().size(),0);
        assertEquals(cont.getById(3L).getFriendsList().size(),0);
    }
}