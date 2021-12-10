package tests.repository.db;

import Domain.Persone;
import Domain.Relationship;
import Domain.User;
import Repository.Db.PersoneDbRepo;
import Repository.Db.RequestsDbRepo;
import Repository.Db.UserDbRepo;
import Repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.Connections;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RequestsDbRepoTest {

    @BeforeEach
    void setUp() {
        tearDown();
        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.save(new Persone(1L,"wewe","weew"));
        repoP.save(new Persone(2L,"weew","erui"));

        UserRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.save(new User(1L,"a",new Persone(1L,"wewe","weew")));
        repo.save(new User(2L,"biscuit",new Persone(1L,"wewe","weew")));
        repo.save(new User(3L,"macaron",new Persone(1L,"wewe","weew")));

        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoQ.save(new Relationship(1L,"a","biscuit"));
        repoQ.save(new Relationship(2L,"a","macaron"));
    }

    @AfterEach
    void tearDown() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoQ.restoreToDefault();

        UserDbRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.restoreToDefault();

        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.restoreToDefault();


    }

    @Test
    void save() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            Relationship rel = new Relationship(3L, "macaron", "biscuit", LocalDate.now(), "pending");
            repoQ.save(rel);
            assertTrue(true);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void saveExistingId() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            Relationship rel = new Relationship(2L, "macaron", "biscuit", LocalDate.now(), "pending");
            repoQ.save(rel);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }


    @Test
    void update() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            Relationship rel = new Relationship(1L,"a", "biscuit", LocalDate.now(), "accept");
            repoQ.update(1L,rel);
            System.out.println("aici ajuns");
            assertEquals(repoQ.getAll().get(0),rel);
            assertTrue(true);
        }catch(Exception e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    void updateInexistingId() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            Relationship rel = new Relationship(1L,"a", "biscuit", LocalDate.now(), "accept");
            repoQ.update(3L,rel);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getAll() {
    }
}