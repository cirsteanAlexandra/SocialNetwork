package tests.controller.newer;

import Controller.NewController.MainController;
import Controller.NewController.PersoneController;
import Controller.NewController.RelationshipController;
import Controller.NewController.UserController;
import Domain.Persone;
import Domain.Relationship;
import Domain.User;
import Repository.Db.PersoneDbRepo;
import Repository.Db.RelationshipDbRepo;
import Repository.Db.UserDbRepo;
import Repository.RelationshipRepo;
import Repository.UserRepo;
import Utils.Exceptions.UserRepoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainControllerTest {

    @BeforeEach
    void setUp() {
        PersoneDbRepo repoP=new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        repoP.save(new Persone(1L,"wewe","weew"));
        repoP.save(new Persone(2L,"weew","erui"));

        UserRepo repoU=new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        repoU.save(new User(1L,"mama",new Persone(1L,"wewe","weew")));
        repoU.save(new User(2L,"mea",new Persone(1L,"wewe","weew")));
        repoU.save(new User(3L,"biscuit",new Persone(1L,"wewe","weew")));
        repoU.save(new User(4L,"macaron",new Persone(1L,"wewe","weew")));
        repoU.save(new User(5L,"belea",new Persone(1L,"wewe","weew")));
        repoU.save(new User(6L,"noua",new Persone(1L,"wewe","weew")));
        repoU.save(new User(7L,"blah",new Persone(1L,"wewe","weew")));
        repoU.save(new User(8L,"meh",new Persone(1L,"wewe","weew")));
        repoU.save(new User(9L,"vespuci",new Persone(1L,"wewe","weew")));
        repoU.save(new User(10L,"acadea",new Persone(1L,"wewe","weew")));

        RelationshipDbRepo repo=new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        repo.save(new Relationship(1L,"mama","mea"));
        repo.save(new Relationship(2L,"biscuit","mama"));
        repo.save(new Relationship(3L,"macaron","mea"));
    }

    @AfterEach
    void tearDown() {
        RelationshipDbRepo repo=new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        repo.restoreToDefault();

        UserDbRepo repoU=new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        repoU.restoreToDefault();

        PersoneDbRepo repoP=new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        repoP.restoreToDefault();
    }

    @Test
    void addUserUnchangedPersone() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertTrue(cont.addUser(new User("malala",new Persone(1L,"wewe","weew"))));
        assertEquals(contU.getSize(),11);
        assertEquals(contP.getSize(),2);
    }

    @Test
    void addUserChangedPersone() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertTrue(cont.addUser(new User("malala",new Persone(5L,"abraham","link"))));
        assertEquals(contU.getSize(),11);
        assertEquals(repoP.getSize(),3);
    }

    @Test
    void addExistingUser() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addUser(new User("acadea", new Persone(5L, "abraham", "link"))));
            assertTrue(false);
        }catch(UserRepoException e){
            assertTrue(true);

        }finally{
            assertEquals(contU.getSize(),10);
            assertEquals(repoP.getSize(),2);
        }

    }

    @Test
    void addRelationship() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertTrue(cont.addRelationship(new Relationship("blah","meh")));
        assertEquals(contR.getSize(),4);
        assertEquals(contU.getSize(),10);
        assertEquals(contP.getSize(),2);
    }

    @Test
    void addRelationshipInexistingFirstUser() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addRelationship(new Relationship("b", "meh")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }finally {
            assertEquals(contR.getSize(), 3);
        }
    }

    @Test
    void addRelationshipInexistingSecondUser() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addRelationship(new Relationship("blah", "m")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }finally {
            assertEquals(contR.getSize(), 3);
        }
    }

    @Test
    void addExistingRelationship() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addRelationship(new Relationship("mama", "mea")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }finally {
            assertEquals(contR.getSize(), 3);
        }
    }

    @Test
    void addExistingInvertRelationship() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addRelationship(new Relationship("mea", "mama")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }finally {
            assertEquals(contR.getSize(), 3);
        }
    }

    @Test
    void removeByUserId() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        cont.addRelationship(new Relationship("mama","noua"));
        cont.addRelationship(new Relationship("mama","belea"));

        assertTrue(cont.removeByUserId(1L));
        assertEquals(contR.getSize(),1);
        assertEquals(contU.getSize(),9);
        assertEquals(contP.getSize(),2);
    }

    @Test
    void removeByUserIdRemovePersone() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        int lenght=contU.getSize();
        for(Long i=1L;i<=lenght;i++){
            cont.removeByUserId(i);
        }

        assertEquals(contR.getSize(),0);
        assertEquals(contU.getSize(),0);
        assertEquals(contP.getSize(),1);
    }

    @Test
    void removeByInexistedUserId() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            assertTrue(cont.removeByUserId(15L));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 3);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 2);
        }
    }

    @Test
    void removeByRelationshipId() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        cont.addRelationship(new Relationship(5L,"mama","noua"));
        cont.addRelationship(new Relationship(6L,"mama","belea"));

        assertTrue(cont.removeByRelationshipId(5L));
        assertEquals(contR.getSize(),4);
        assertEquals(contU.getSize(),10);
        assertEquals(contP.getSize(),2);
    }

    @Test
    void removeByInexistedRelationshipdId() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            assertTrue(cont.removeByRelationshipId(15L));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 3);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 2);
        }
    }

    @Test
    void removeUserByUsername() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        cont.addRelationship(new Relationship("mama","noua"));
        cont.addRelationship(new Relationship("mama","belea"));

        assertTrue(cont.removeUserByUsername("mama"));
        assertEquals(contR.getSize(),1);
        assertEquals(contU.getSize(),9);
        assertEquals(contP.getSize(),2);
    }

    @Test
    void removeUserByUsernameRemovePersone() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        cont.removeUserByUsername("mama");cont.removeUserByUsername("mea");
        cont.removeUserByUsername("biscuit");cont.removeUserByUsername("macaron");
        cont.removeUserByUsername("belea");cont.removeUserByUsername("noua");
        cont.removeUserByUsername("meh");cont.removeUserByUsername("blah");
        cont.removeUserByUsername("vespuci");cont.removeUserByUsername("acadea");

        assertEquals(contR.getSize(),0);
        assertEquals(contU.getSize(),0);
        assertEquals(contP.getSize(),1);
    }

    @Test
    void removeInexistingUserByUsername() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            assertTrue(cont.removeUserByUsername("jweih"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 3);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 2);
        }
    }


    @Test
    void removeX2User() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            assertTrue(cont.removeByUserId(1L));
            assertTrue(cont.removeUserByUsername("mama"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 1);
            assertEquals(contU.getSize(), 9);
            assertEquals(contP.getSize(), 2);
        }
    }

    @Test
    void removeRelationshipByUsernames() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        cont.addRelationship(new Relationship(5L,"mama","noua"));
        cont.addRelationship(new Relationship(6L,"mama","belea"));

        assertTrue(cont.removeRelationshipByUsernames("belea","mama"));
        assertEquals(contR.getSize(),4);
        assertEquals(contU.getSize(),10);
        assertEquals(contP.getSize(),2);
    }

    @Test
    void removeInexistedRelationshipByUsernamesd() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            assertTrue(cont.removeRelationshipByUsernames("capri","maluma"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 3);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 2);
        }
    }

    @Test
    void removeX2Relationship() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            cont.removeByRelationshipId(1L);
            assertTrue(cont.removeRelationshipByUsernames("mama","mea"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 2);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 2);
        }
    }

    @Test
    void getUserById() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getUserById(1L),new User(1L,"mama",new Persone(1L,"wewe","weew")));

    }

    @Test
    void getInexistedUserById() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertEquals(cont.getUserById(15L), null);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getRelationshipById() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getRelationshipById(1L),new Relationship(1L,"mama","mea"));

    }

    @Test
    void getInexistedRelationshipById() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertEquals(cont.getRelationshipById(15L), null);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getUserByOther() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        assertEquals(cont.getUserByOther("mama"),new User(1L,"mama",new Persone(1L,"wewe","weew")));

    }

    @Test
    void getInexistedUserByOther() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertEquals(cont.getUserByOther("hjew"), null);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getRelationshipByOther() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getRelationshipByOther("mama","mea"),new Relationship(1L,"mama","mea"));

    }

    @Test
    void getInexistedRelationshipByOther() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertEquals(cont.getRelationshipByOther("wewwe","wehhjew"), null);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getAllUsers() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        List<User> list=cont.getAllUsers();
        assertEquals(list.size(),10);

        User user=new User(1L,"mama",new Persone("wewe","weew"));
        user.addFriend("mea");
        user.addFriend("biscuit");
        assertEquals(list.get(5),user);
    }

    @Test
    void getAllRelationships() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        List<Relationship> list=cont.getAllRelationships();
        assertEquals(list.size(),3);

        Relationship rel= new Relationship(1L,"mama","mea");
        assertEquals(list.get(0),rel);
    }

    @Test
    void getUserSize() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getUserSize(),10);
    }

    @Test
    void getRelationshipSize() {
        UserRepo repoU= new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        PersoneDbRepo repoP= new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");
        RelationshipRepo repoR= new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getRelationshipSize(),3);
    }

}