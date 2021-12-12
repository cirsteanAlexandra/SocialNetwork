package tests.repository.db;

import Domain.Persone;
import Domain.User;
import Repository.Db.PersoneDbRepo;
import Repository.Db.UserDbRepo;
import Repository.UserRepo;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.UserRepoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.Connections;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDbRepoTest {
    @BeforeEach
    void setUp(){
        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.save(new Persone(1L,"wewe","weew"));
        repoP.save(new Persone(2L,"weew","erui"));

        UserRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);


        repo.save(new User(1L,"a",new Persone(1L,"wewe","weew")));
        repo.save(new User(2L,"biscuit",new Persone(1L,"wewe","weew")));
        repo.save(new User(3L,"macaron",new Persone(1L,"wewe","weew")));
    }

    @AfterEach
    void tearDown(){


        UserDbRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.restoreToDefault();

        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);

        repoP.restoreToDefault();
    }

    @Test
    void save() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try {
            assertTrue(
                    repo.save(new User(4L, "belea", new Persone(1L, "wewe", "weew"))));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
    }

    @Test
    void saveWithNoID() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try {
            assertTrue(
                    repo.save(new User("blah", new Persone(1L, "wewe", "weew"))));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
    }

    @Test
    void saveExistentId(){

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.save(new User(1L,"acadeaua",new Persone(1L,"wewe","weew"))));
        }catch(UserRepoException e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
    }

    @Test
    void saveExistentUsername(){

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.save(new User(5L,"a",new Persone(1L,"wewe","weew"))));
        }catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
    }

    @Test
    void saveExistentIdAndUsername(){

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.save(new User(1L,"a",new Persone(2L,"wewe","weew"))));
        }catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
    }

    @Test
    void get() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        User user= repo.get(1L);
        assertEquals(new User(1L,"a",new Persone(1L,"","")),user);
    }

    @Test
    void getNull() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        User user= repo.get(10L);
        assertEquals(user,null);
    }

    @Test
    void update() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try{
            assertTrue(
                    repo.update(1L,new User(5L,"acadea",new Persone(2L,"weew","erui"))));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),3);
    }

    @Test
    void updateInexistent() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(repo.update(7L,new User(5L,"acadea",new Persone(2L,"weew","erui"))));
            assertTrue(false);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
    }

    @Test
    void delete() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try{
            assertTrue(
                    repo.delete(1L));
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void deleteInexistent() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try{
            assertTrue(
                    repo.delete(5L));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getAll() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        List<User> list=repo.getAll();
        List<User> comparableList= Arrays.asList(new User(1L,"a",new Persone(1L,"wewe","weew")),
                new User(2L,"biscuit",new Persone(1L,"wewe","weew")),
                new User(3L,"macaron",new Persone(1L,"wewe","weew")));
        assertEquals(list,comparableList);
    }


    @Test
    void getByOther() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try{
            assertEquals(repo.getByOther("biscuit"),new User(2L,"biscuit",new Persone(1L,"wewe","weew")));
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void getByOtherInexistent() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try{
            assertEquals(repo.getByOther("hjer"),null);
        }catch(Exception e){
            assertTrue(false);
        }
    }
}