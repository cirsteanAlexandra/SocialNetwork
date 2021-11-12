package tests.repository.db;

import Domain.Persone;
import Domain.Relationship;
import Domain.User;
import Repository.Db.PersoneDbRepo;
import Repository.Db.RelationshipDbRepo;
import Repository.Db.UserDbRepo;
import Repository.UserRepo;
import Utils.Exceptions.EntityRepoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipDbRepoTest {
    @BeforeEach
    void setUp(){
        PersoneDbRepo repoP=new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","852456");
        repoP.save(new Persone(1L,"wewe","weew"));
        repoP.save(new Persone(2L,"weew","erui"));

        UserRepo repoU=new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","852456");
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
        RelationshipDbRepo repo=new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","852456");
        repo.save(new Relationship(1L,"mama","mea"));
        repo.save(new Relationship(2L,"biscuit","mama"));
        repo.save(new Relationship(3L,"macaron","mea"));
    }

    @AfterEach
    void tearDown(){
        RelationshipDbRepo repo=new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","852456");
        repo.restoreToDefault();

        UserDbRepo repoU=new UserDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","852456");
        repoU.restoreToDefault();

        PersoneDbRepo repoP=new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","852456");
        repoP.restoreToDefault();

    }

    @Test
    void save() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        try {
            assertTrue(
                    repo.save(new Relationship(4L, "belea", "noua")));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
    }

    @Test
    void saveWithNoID() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        try {
            assertTrue(
                    repo.save(new Relationship("blah", "meh")));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
    }

    @Test
    void saveExistentId(){
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        try{
            assertTrue(repo.save(new Relationship(1L,"acadeaua","weew")));
        }catch(EntityRepoException e){
            assertTrue(true);
        }
        assertTrue(repo.getSize()==3);
    }

    @Test
    void saveExistentUsernames(){
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        try{
            assertTrue(
                    repo.save(new Relationship(5L,"mama","mea")));
        }catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
    }

    @Test
    void get() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        Relationship rel= repo.get(1L);
        assertEquals(new Relationship(1L,"mama","mea"),rel);
    }

    @Test
    void getNull() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        Relationship rel= repo.get(10L);
        assertNull(rel);
    }

    @Test
    void update() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        try{
            assertTrue(
                    repo.update(1L,new Relationship(5L,"acadea","vespuci")));
            assertNull(repo.get(1L));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),3);
    }

    @Test
    void updateInexistent() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        try{
            assertTrue(repo.update(7L,new Relationship(5L,"acadea","erui")));
            assertTrue(false);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
    }

    @Test
    void delete() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        try{
            assertTrue(
                    repo.delete(1L));
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void deleteInexistent() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
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
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        List<Relationship> list=repo.getAll();
        List<Relationship> comparableList= Arrays.asList(new Relationship(1L,"mama","mea"),
                new Relationship(2L,"biscuit","mama"),
                new Relationship(3L,"macaron","mea"));
        assertEquals(list,comparableList);
    }


    @Test
    void getByOther() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        try{
            assertEquals(repo.getByOther("biscuit","mama"),new Relationship(2L,"biscuit","mama"));
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void getByOtherInexistent() {
        RelationshipDbRepo repo = new RelationshipDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "852456");
        try{
            assertEquals(repo.getByOther("hjer","mea"),null);
        }catch(Exception e){
            assertTrue(false);
        }
    }
}