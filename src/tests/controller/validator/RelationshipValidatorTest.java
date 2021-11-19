package tests.controller.validator;

import Controller.Validator.AbstractValidator;
import Controller.Validator.ContextValidator;
import Controller.Validator.Strategy;
import Domain.Relationship;
import Utils.Exceptions.Exception;
import Utils.Exceptions.RelationshipException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        }catch(RelationshipException e){
            assertEquals(e.getDescription(),"Id should be a pozitive number\n");
            assertEquals(e.getCode(),3);

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
        }catch(RelationshipException e){
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
        }catch(RelationshipException e){
            assertTrue(e.getCode()==3);
        }
    }
}