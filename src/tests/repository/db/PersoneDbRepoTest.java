package tests.repository.db;

import Domain.Persone;
import Repository.Db.PersoneDbRepo;
import Utils.Exceptions.EntityRepoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersoneDbRepoTest {
    @BeforeEach
    void setUp(){
        PersoneDbRepo repo=new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");


        repo.save(new Persone(1L,"wewe","weew"));
        repo.save(new Persone(2L,"wewe","weew"));
        repo.save(new Persone(3L,"mama","mea"));
    }

    @AfterEach
    void tearDown(){
        PersoneDbRepo repo=new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare","postgres","hexagon");


        repo.restoreToDefault();
    }

    @Test
    void save() {
        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");


        try {
            assertTrue(
                    repo.save(new Persone(4L, "wwne", "reje")));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
    }

    @Test
    void saveWithNoID() {
        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");


        try {
            assertTrue(
                    repo.save(new Persone("jerk", "erjk")));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
    }

    @Test
    void saveExistentId(){

        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");


        try{
            assertTrue(repo.save(new Persone(1L,"jkwe","wejk")));
        }catch(EntityRepoException e){
            assertTrue(true);
        }
        assertTrue(repo.getSize()==3);
    }


    @Test
    void get() {

        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");


        Persone pers= repo.get(1L);
        assertEquals(new Persone(1L,"wewe","weew"),pers);
    }

    @Test
    void getNull() {

        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");


        Persone pers= repo.get(10L);
        assertEquals(pers,null);
    }

    @Test
    void update() {

        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");
        try{
            assertTrue(
                    repo.update(1L,new Persone(5L,"weew","erui")));
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

        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");

        try{
            assertTrue(repo.update(7L,new Persone(2L,"weew","erui")));
            assertTrue(false);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
    }

    @Test
    void delete() {
        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");

        try{
            assertTrue(
                    repo.delete(1L));
            assertNull(repo.get(1L));
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void deleteInexistent() {

        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");

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
        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");

        List<Persone> list=repo.getAll();
        List<Persone> comparableList= Arrays.asList(new Persone(1L,"wewe","weew"),
                new Persone(2L,"wewe","weew"),
                new Persone(3L,"mama","mea"));
        assertEquals(list,comparableList);
    }


    @Test
    void getByOther() {
        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");

        try{
            assertEquals(repo.getByOther("mama","mea"),new Persone(3L,"mama","mea"));
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void getByOtherInexistent() {

        PersoneDbRepo repo = new PersoneDbRepo("jdbc:postgresql://localhost:5432/TestReteaDeSocializare", "postgres", "hexagon");

        try{
            assertEquals(repo.getByOther("hjer","hjwek"),null);
        }catch(Exception e){
            assertTrue(false);
        }
    }

}