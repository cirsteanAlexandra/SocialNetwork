package tests.domain;

import Domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUsername() {
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        assertTrue(user.getUsername().equals("acadea"));
    }

    @Test
    void setUsername() {
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        user.setUsername("babushka");
        assertTrue(user.getUsername().equals("babushka"));
    }

    @Test
    void getPers() {
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        Persone pers=user.getPers();
        assertTrue(pers.getFirstName().equals("Vasile"));
        assertTrue(pers.getLastName().equals("Purcel"));
    }

    @Test
    void setPers() {
        User user= new User("acadea", new Persone("True","Vasile"));
        Persone pers2= new Persone ("Fake","Vasile");
        user.setPers(pers2);
        Persone pers=user.getPers();
        assertTrue(pers.getFirstName().equals("Fake"));
        assertTrue(pers.getLastName().equals("Vasile"));
    }

    @Test
    void testToString() {
        User user= new User("acadea", new Persone("True","Vasile"));
        user.setId(1L);
        assertTrue(user.toString().equals("User{" +
                "id" +"1"+
                "username='" + "acadea" + '\'' +
                ", pers=" + "Persone{"+
                "firstName='" + "True" + '\'' +
                ", lastName='" + "Vasile" + '\'' +
                '}' +
                '}'));
    }

    @Test
    void getId(){
        User user= new User(1L,"acadea", new Persone("True","Vasile"));
        assertTrue(user.getId()==1L);
    }

    @Test
    void setId(){
        User user= new User(1L,"acadea", new Persone("True","Vasile"));
        user.setId(2L);
        assertTrue(user.getId()==2L);
    }

    @Test
    void testEquals(){
        User user1= new User(1L,"acadea", new Persone("True","Vasile"));
        User user2= new User(1L,"maslin", new Persone("Fake","Vasile"));
        assertTrue(user1.equals(user2));

        User user3= new User(2L,"acadea", new Persone("Fake","Vasile"));
        assertTrue(user1.equals(user3));

        User user4= new User(2L,"maslin", new Persone("True","Vasile"));
        assertFalse(user1.equals(user4));
    }
}