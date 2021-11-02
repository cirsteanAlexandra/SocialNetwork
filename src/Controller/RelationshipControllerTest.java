package Controller;

import Domain.Relationship;
import Repository.RelationshipMemoryRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RelationshipControllerTest {

    @Test
    void add() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        try{
            cont.add(new Relationship(1L,"a","b"));
            assertTrue(true);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void addException() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        try{
            cont.add(new Relationship(1L,"a","b"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        try{
            cont.add(new Relationship(2L,"a","b"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void removeById() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        try{
            cont.removeById(1L);
            assertTrue(cont.getSize()==1);
            assertTrue(true);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void removeByIdException() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        try{
            cont.removeById(3L);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }
    @Test
    void removeByOthers() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        try{
            cont.removeByOthers("a","b");
            assertTrue(cont.getSize()==1);
            assertTrue(true);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void removeByOthersException() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        try{
            cont.removeByOthers("a","d");
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getById() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));

        assertEquals(cont.getById(1L).getFirstUserName(),"a");
        assertEquals(cont.getById(1L).getSecondUserName(),"b");
        assertEquals(cont.getById(3L),null);
    }

    @Test
    void getByOther() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));

        assertEquals(cont.getByOther("b","c").getId(),2L);
        assertEquals(cont.getByOther("m","n"),null);
    }

    @Test
    void getAll() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        cont.add(new Relationship(3L,"b","d"));

        assertEquals(cont.getSize(),3);
        assertEquals(cont.getAll().size(),3);
        assertEquals(cont.getAll().get(0).getId(),1L);
        assertEquals(cont.getAll().get(1).getId(),2L);
        assertEquals(cont.getAll().get(2).getId(),3L);
    }


    @Test
    void deleteAllRelationsByUsername() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        cont.add(new Relationship(3L,"b","d"));
        assertEquals(cont.deleteAllRelationsByUsername("b"),3);
        assertEquals(cont.getSize(),0);
        assertEquals(cont.deleteAllRelationsByUsername("a"),0);
    }

    @Test
    void getNumberOfCommunities() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        repo.save(new Relationship(1L,"a","b"));
        repo.save(new Relationship(2L,"b","c"));
        repo.save(new Relationship(3L,"b","d"));
        repo.save(new Relationship(4L,"c","d"));
        repo.save(new Relationship(5L,"c","e"));
        repo.save(new Relationship(6L,"e","f"));
        repo.save(new Relationship(7L,"g","h"));
        repo.save(new Relationship(8L,"i","j"));
        repo.save(new Relationship(9L,"i","k"));
        repo.save(new Relationship(10L,"j","k"));

        assertEquals(
                cont.getNumberOfCommunities(11),3);
        cont.removeById(7L);
        assertEquals(cont.getNumberOfCommunities(11),4);

    }

    @Test
    void getTheMostSociableCommunity() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        repo.save(new Relationship(1L,"a","b"));
        repo.save(new Relationship(2L,"b","c"));
        repo.save(new Relationship(3L,"b","d"));
        repo.save(new Relationship(4L,"c","d"));
        repo.save(new Relationship(5L,"c","e"));
        repo.save(new Relationship(6L,"e","f"));
        repo.save(new Relationship(7L,"g","h"));
        repo.save(new Relationship(8L,"i","j"));
        repo.save(new Relationship(9L,"i","k"));
        repo.save(new Relationship(10L,"j","k"));
        List<String> list=new ArrayList<>();
        list.add("a");list.add("b");list.add("c");list.add("d");list.add("e");list.add("f");
        assertEquals(cont.getTheMostSociableCommunity(11),list);

        cont.removeById(2L);
        cont.removeById(4L);
        List<String> list1=new ArrayList<>();
        list1.add("a");list1.add("b");list1.add("d");
        assertEquals(cont.getTheMostSociableCommunity(11),list1);
    }
}