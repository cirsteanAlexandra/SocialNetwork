package Controller.Validator;

import Domain.Entity;
import Utils.Exceptions.PersoneException;
import Utils.Exceptions.UserException;

public abstract class AbstractValidator<E extends Entity> implements Validator<E> {
    protected String listOfErrors;

    @Override
    public abstract boolean validate(E entity);

    public boolean checkUserName(String username){
        if(username=="")
            throw new UserException("Username shoundn't be empty\n");
        if(checkUserNameChar(username)==false)
            throw new UserException("Username shound contain english letters or/and digits\n");
        return true;
    }
    public boolean checkUserNameChar(String username){
        for (int i=0;i<username.length();i++){
            if (!((username.charAt(i)>='a' && username.charAt(i)<='z')||
                    (username.charAt(i)>='A' && username.charAt(i)<='Z')||
                    (username.charAt(i)>='0' && username.charAt(i)<='9')))
                return false;
        }
        return true;
    }

    public boolean checkId(Long id){
        if(id<0)
            throw new UserException("Id should be a pozitive number\n");
        return true;
    }


    public boolean checkName(String name){
        if(name=="") throw new PersoneException("The first name and the last name shouldn't be empty\n");
        for (int i=0;i<name.length();i++){
            if (!(name.charAt(i)>='a' && name.charAt(i)<='z')||
                    (name.charAt(i)>='A' && name.charAt(i)<='Z') || (name.charAt(i)=='-'))
                throw new PersoneException("The first name and the last name should contain english characters\n");
            return false;
        }
        return true;
    }
}
