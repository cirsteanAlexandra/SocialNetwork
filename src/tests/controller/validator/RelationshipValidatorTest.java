package tests.controller.validator;

import Controller.Validator.AbstractValidator;
import Controller.Validator.ContextValidator;
import Controller.Validator.Strategy;
import Domain.Relationship;
import Utils.Exceptions.Exception;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipValidatorTest {

    @Test
    void validateForId() {
        Relationship rel=new Relationship("acadea","melancolie");
        AbstractValidator vali=ContextValidator.createValidator(Strategy.RELATIONSHIP);
        assertTrue(vali.validate(rel));

        Relationship rel1=new Relationship(1L,"acadea","melancolie");
        assertTrue(vali.validate(rel1));

        Relationship rel2=new Relationship(-1L,"acadea","melancolie");
        try{
            vali.validate(rel2);
        }catch(Exception e){
            assertTrue(e.getCode()==3);
            assertEquals(e.getDescription(),"Id should be a pozitive number\n");
        }
    }

    @Test
    void validateForFirstUsername(){
        Relationship rel=new Relationship("","melancolie");
        AbstractValidator vali=ContextValidator.createValidator(Strategy.RELATIONSHIP);
        try{
            vali.validate(rel);
        }catch(Exception e){
            assertTrue(e.getCode()==3);
        }

        Relationship rel1=new Relationship("acadea&","melancolie");
        try{
            vali.validate(rel1);
        }catch(Exception e){
            assertTrue(e.getCode()==3);
        }
    }

    @Test
    void validateForSecondUsername(){
        Relationship rel=new Relationship("acadea","");
        AbstractValidator vali=ContextValidator.createValidator(Strategy.RELATIONSHIP);
        try{
            vali.validate(rel);
        }catch(Exception e){
            assertTrue(e.getCode()==3);
        }

        Relationship rel1=new Relationship("acadea","melancolie$3");
        try{
            vali.validate(rel1);
        }catch(Exception e){
            assertTrue(e.getCode()==3);
        }
    }
}