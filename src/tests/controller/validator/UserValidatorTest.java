package tests.controller.validator;

import Controller.Validator.UserValidator;
import Controller.Validator.Validator;
import Domain.Persone;
import Domain.User;
import Utils.Exceptions.Exception;
import Utils.Exceptions.UserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidatorTest {

    @Test
    void validate() {
        User user= new User(1L,"floricica",new Persone("cretulina","buzoiescu"));
        Validator<User> vali= new UserValidator();
        assertTrue(vali.validate(user));

        User user1= new User(1L,"",new Persone("","buzoiescu"));
        try{
            vali.validate(user1);
        }
        catch (UserException e){
            assertEquals(e.getCode(),2);
            assertEquals(e.getDescription(),"The first name and the last name shouldn't be empty\nUsername shoundn't be empty\n");
        }

    }

    @Test
    void checkUserName() {
        User user= new User(1L,"floricica1",new Persone("cretulina","buzoiescu"));
        Validator<User> vali= new UserValidator();
        assertTrue(vali.validate(user));

        User user1= new User(1L,"floricica1$",new Persone("cretulina","buzoiescu"));
        try{
            vali.validate(user1);
        }
        catch (Exception e){
            assertEquals(e.getCode(),2);
            assertEquals(e.getDescription(),"Username shound contain english letters or/and digits\n");
        }

        User user2= new User(1L,"",new Persone("cretulina","buzoiescu"));
        try{
            vali.validate(user2);
        }
        catch (UserException e){
            assertEquals(e.getCode(),2);
            assertEquals(e.getDescription(),"Username shoundn't be empty\n");
        }
    }


    @Test
    void checkId() {
        User user= new User(-1L,"floricica",new Persone("cretulina","buzoiescu"));
        Validator<User> vali= new UserValidator();
        try{
            vali.validate(user);
        }
        catch (UserException e){
            assertEquals(e.getCode(),2);
            assertEquals(e.getDescription(),"Id should be a pozitive number\n");
        }
    }
}