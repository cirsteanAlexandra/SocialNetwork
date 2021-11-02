package tests.controller;

import Controller.Controller;
import Controller.RelationshipController;
import Domain.Relationship;
import Repository.RelationshipFileRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RelationshipControllerTest {

    @Test
    void add() {
        RelationshipFileRepo repo=new RelationshipFileRepo();
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
        RelationshipFileRepo repo=new RelationshipFileRepo();
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
        RelationshipFileRepo repo=new RelationshipFileRepo();
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
        RelationshipFileRepo repo=new RelationshipFileRepo();
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
        RelationshipFileRepo repo=new RelationshipFileRepo();
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
        RelationshipFileRepo repo=new RelationshipFileRepo();
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
        RelationshipFileRepo repo=new RelationshipFileRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));

        Assertions.assertEquals(cont.getById(1L).getFirstUserName(),"a");
        Assertions.assertEquals(cont.getById(1L).getSecondUserName(),"b");
        Assertions.assertEquals(cont.getById(3L),null);
    }

    @Test
    void getByOther() {
        RelationshipFileRepo repo=new RelationshipFileRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));

        Assertions.assertEquals(cont.getByOther("b","c").getId(),2L);
        Assertions.assertEquals(cont.getByOther("m","n"),null);
    }

    @Test
    void getAll() {
        RelationshipFileRepo repo=new RelationshipFileRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        cont.add(new Relationship(3L,"b","d"));

        Assertions.assertEquals(cont.getSize(),3);
        Assertions.assertEquals(cont.getAll().size(),3);
        Assertions.assertEquals(cont.getAll().get(0).getId(),1L);
        Assertions.assertEquals(cont.getAll().get(1).getId(),2L);
        Assertions.assertEquals(cont.getAll().get(2).getId(),3L);
    }


    @Test
    void deleteAllRelationsByUsername() {
        RelationshipFileRepo repo=new RelationshipFileRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        cont.add(new Relationship(3L,"b","d"));
        Assertions.assertEquals(cont.deleteAllRelationsByUsername("b"),3);
        Assertions.assertEquals(cont.getSize(),0);
        Assertions.assertEquals(cont.deleteAllRelationsByUsername("a"),0);
    }

    @Test
    void getNumberOfCommunities() {
        RelationshipFileRepo repo=new RelationshipFileRepo();
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

        Assertions.assertEquals(
                cont.getNumberOfCommunities(11),3);
        cont.removeById(7L);
        Assertions.assertEquals(cont.getNumberOfCommunities(11),4);

    }

    @Test
    void getTheMostSociableCommunity() {
        RelationshipFileRepo repo=new RelationshipFileRepo();
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
        Assertions.assertEquals(cont.getTheMostSociableCommunity(11),list);

        cont.removeById(2L);
        cont.removeById(4L);
        List<String> list1=new ArrayList<>();
        list1.add("a");list1.add("b");list1.add("d");
        Assertions.assertEquals(cont.getTheMostSociableCommunity(11),list1);
    }
}