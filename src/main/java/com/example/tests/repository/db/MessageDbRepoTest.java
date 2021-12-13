package com.example.tests.repository.db;

import com.example.Domain.Message;
import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Repository.Db.MessageDbRepo;
import com.example.Repository.Db.PersoneDbRepo;
import com.example.Repository.Db.UserDbRepo;
import com.example.Repository.MessageRepo;
import com.example.Repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.tests.Connections;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageDbRepoTest {

    @BeforeEach
    void setUp() {
        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.save(new Persone(1L,"wewe","weew"));
        repoP.save(new Persone(2L,"weew","erui"));

        UserRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.save(new User(1L,"a",new Persone(1L,"wewe","weew")));
        repo.save(new User(2L,"biscuit",new Persone(1L,"wewe","weew")));
        repo.save(new User(3L,"macaron",new Persone(1L,"wewe","weew")));
        repo.save(new User(4L,"bucalea",new Persone(1L,"wewe","weew")));
        repo.save(new User(5L,"haplea",new Persone(1L,"wewe","weew")));

        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoM.save(new Message(1L,new User(1L,"a",new Persone(1L,"wewe","weew")),"Morning", Arrays.asList(new User(2L,"biscuit",new Persone(1L,"wewe","weew")),new User(3L,"macaron",new Persone(1L,"wewe","weew"))), LocalDateTime.now()));
    }

    @AfterEach
    void tearDown() {
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoM.restoreToDefault();

        UserDbRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.restoreToDefault();

        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.restoreToDefault();

    }

    @Test
    void saveSimple() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message= new Message(user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        assertTrue(repoM.save(message));
    }

    @Test
    void saveToAll() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1=new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2=new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        User user5=new User(5L,"haplea",new Persone(1L,"wewe","weew"));

        Message message= new Message(user1, "hai la scoala",Arrays.asList(user2,user3,user4,user5),LocalDateTime.of(2019,11,2,12,53));

        assertTrue(repoM.save(message));
    }

    @Test
    void saveWithReplay() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1=new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2=new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        User user5=new User(5L,"haplea",new Persone(1L,"wewe","weew"));

        Message message1= new Message(user1, "hai la scoala",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(user2, "nu pot ca mi-e somn",Arrays.asList(user1),LocalDateTime.of(2019,11,2,12,53),message1);
        repoM.save(message1);
        assertTrue(repoM.save(message2));
    }

    @Test
    void saveWithIdSimple(){
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        assertTrue(repoM.save(message));
    }

    @Test
    void saveWithIdAll(){
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1=new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2=new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        User user5=new User(5L,"haplea",new Persone(1L,"wewe","weew"));

        Message message= new Message(2L,user1, "hai la scoala",Arrays.asList(user2,user3,user4,user5),LocalDateTime.of(2019,11,2,12,53));
        assertTrue(repoM.save(message));
    }

    @Test
    void saveExistingId(){
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(2L,user2, "hello",Arrays.asList(user1),LocalDateTime.of(2019,11,2,12,53));
        assertTrue(repoM.save(message1));
        try {
            assertTrue(repoM.save(message2));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getSimple() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(3L,user2, "hello",Arrays.asList(user1),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message1);
        assertEquals(repoM.get(2L),message1);
    }

    @Test
    void getMultiple() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2,user3,user4),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message1);
        assertEquals(repoM.get(2L),message2);
    }

    @Test
    void getInexistentId() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2,user3,user4),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message1);
        assertEquals(repoM.get(-7L),null);
    }

    @Test
    void updateMessageAllSingleIdMEss() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2,user3,user4),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(2L,user1, "salut",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message1);

        assertTrue(repoM.update(2L,message2));
        assertNotEquals(repoM.get(2L),message1);
        assertEquals(repoM.get(2L),message2);
    }

    @Test
    void updateSingleMessage() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(2L,user1, "salut",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message1);

        assertTrue(repoM.update(2L,message2));
        assertNotEquals(repoM.get(2L),message1);
        assertEquals(repoM.get(2L),message2);
    }

    @Test
    void deleteSingleMessage() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message);
        assertTrue(repoM.delete(2L));
        assertEquals(repoM.get(2L),null);
    }

    @Test
    void deleteMessageAll() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        Message message= new Message(2L,user1, "hello",Arrays.asList(user2,user3,user4),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message);
        assertTrue(repoM.delete(2L));
        assertEquals(repoM.get(2L),null);
        assertEquals(repoM.getSize(),4);
    }

    @Test
    void deleteInexistent() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message);
        try {
            assertTrue(repoM.delete(3L));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repoM.get(2L),message);
    }

    @Test
    void getSize() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        User user5=new User(5L,"haplea",new Persone(1L,"wewe","weew"));

        repoM.save(new Message(user1, "hello",Arrays.asList(user2),LocalDateTime.now()));
        repoM.save(new Message(user2, "hello",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user1, "did u make the homework?",Arrays.asList(user2),LocalDateTime.now()));
        repoM.save(new Message(user2, "nope",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user1, "ok, i'll ask the group",Arrays.asList(user2),LocalDateTime.now()));
        repoM.save(new Message(user1, "can someone give me a \"hhhaaa yeeaahhh\" ?",Arrays.asList(user2,user3,user4,user5),LocalDateTime.now()));
        repoM.save(new Message(user2, "hhhaaa yeeaahhh",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user3, "hhhaaa yeeaahhh",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user4, "hhhaaa yeeaahhh",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user5, "hhhaaa yeeaahhh",Arrays.asList(user1),LocalDateTime.now()));

        assertEquals(repoM.getSize(),15);
    }

    @Test
    void getAll() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        User user3=new User(3L,"macaron",new Persone(1L,"wewe","weew"));
        User user4=new User(4L,"bucalea",new Persone(1L,"wewe","weew"));
        User user5=new User(5L,"haplea",new Persone(1L,"wewe","weew"));

        repoM.save(new Message(user1, "hello",Arrays.asList(user2),LocalDateTime.now()));
        repoM.save(new Message(user2, "hello",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user1, "did u make the homework?",Arrays.asList(user2),LocalDateTime.now()));
        repoM.save(new Message(user2, "nope",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user1, "ok, i'll ask the group",Arrays.asList(user2),LocalDateTime.now()));
        repoM.save(new Message(user1, "can someone give me a \"hhhaaa yeeaahhh\" ?",Arrays.asList(user2,user3,user4,user5),LocalDateTime.now()));
        repoM.save(new Message(user2, "hhhaaa yeeaahhh",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user3, "hhhaaa yeeaahhh",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user4, "hhhaaa yeeaahhh",Arrays.asList(user1),LocalDateTime.now()));
        repoM.save(new Message(user5, "hhhaaa yeeaahhh",Arrays.asList(user1),LocalDateTime.now()));

        List<Message> list=repoM.getAll();
        assertEquals(list.size(),15);
        assertEquals(list.get(2).getMessage(),"hello");
        assertEquals(list.get(3).getMessage(),"hello");
    }

    @Test
    void getByOther() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(3L,user2, "hello",Arrays.asList(user1),LocalDateTime.of(2019,11,2,12,53));
        Message message3= new Message(4L,user1, "asl pls",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message1);
        repoM.save(message2);
        repoM.save(message3);

        assertEquals(repoM.getByOther("a","biscuit").getFrom().getUsername(),message1.getFrom().getUsername());
        assertEquals(repoM.getByOther("a","biscuit").getMessage(),message3.getMessage());
    }

    @Test
    void getByOtherInexistent() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(3L,user2, "hello",Arrays.asList(user1),LocalDateTime.of(2019,11,2,12,53));
        Message message3= new Message(4L,user1, "asl pls",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message1);
        repoM.save(message2);
        repoM.save(message3);

        assertEquals(repoM.getByOther("b","biscuit"),null);
    }

    @Test
    void getBySR(){
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(3L,user2, "hello",Arrays.asList(user1),LocalDateTime.of(2019,11,2,12,53));
        Message message3= new Message(4L,user1, "asl pls",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message1);
        repoM.save(message2);
        repoM.save(message3);
        assertEquals(repoM.getBySR("a","biscuit").size(),3);
        assertEquals(repoM.getBySR("a","biscuit").get(0).getMessage(),"Morning");
        assertEquals(repoM.getBySR("a","biscuit").get(1).getMessage(),"hello");
        assertEquals(repoM.getBySR("a","biscuit").get(2).getMessage(),"asl pls");
    }

    @Test
    void getBySRNonexistent(){
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(3L,user2, "hello",Arrays.asList(user1),LocalDateTime.of(2019,11,2,12,53));
        Message message3= new Message(4L,user1, "asl pls",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        repoM.save(message1);
        repoM.save(message2);
        repoM.save(message3);
        assertTrue(repoM.getBySR("b","biscuit").isEmpty());
    }

    @Test
    void getByDateTime() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(3L,user2, "hello",Arrays.asList(user1),LocalDateTime.of(2019,11,2,12,53));
        Message message3= new Message(4L,user1, "asl pls",Arrays.asList(user2),LocalDateTime.of(2019,11,3,12,53));
        repoM.save(message1);
        repoM.save(message2);
        repoM.save(message3);

        assertEquals(repoM.getByDateTime(LocalDateTime.of(2019,11,2,12,53)).size(),2);
    }

    @Test
    void getByDateTimeNonexistent() {
        MessageRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        User user1= new User(1L,"a",new Persone(1L,"wewe","weew"));
        User user2= new User(2L,"biscuit",new Persone(1L,"wewe","weew"));
        Message message1= new Message(2L,user1, "hello",Arrays.asList(user2),LocalDateTime.of(2019,11,2,12,53));
        Message message2= new Message(3L,user2, "hello",Arrays.asList(user1),LocalDateTime.of(2019,11,2,12,53));
        Message message3= new Message(4L,user1, "asl pls",Arrays.asList(user2),LocalDateTime.of(2019,11,3,12,53));
        repoM.save(message1);
        repoM.save(message2);
        repoM.save(message3);

        assertEquals(repoM.getByDateTime(LocalDateTime.of(2019,11,2,12,52)).size(),0);
    }
}