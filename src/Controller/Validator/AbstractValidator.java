package Controller.Validator;

import Domain.Entity;
import Utils.Exceptions.EntityException;
import Utils.Exceptions.PersoneException;

public abstract class AbstractValidator<E extends Entity> implements Validator<E> {
    protected String listOfErrors;

    /**
     * Validates an entity
     * @param entity the object to be verified
     * @return true if it is valid, false otherwise
     */
    @Override
    public abstract boolean validate(E entity);

    /**
     * Checks if the username is null or it has forbidden characters
     * @param username the string to be verified
     * @return true if it is valid, false otherwise
     */
    public boolean checkUserName(String username){
        if(username=="")
            throw new EntityException("Username shoundn't be empty\n");
        if(checkUserNameChar(username)==false)
            throw new EntityException("Username shound contain english letters or/and digits\n");
        return true;
    }

    /**
     * Checks if the username has forbidden characters
     * @param username the string to be verified
     * @return true if it is valid, false otherwise
     */
    public boolean checkUserNameChar(String username){
        for (int i=0;i<username.length();i++){
            if (!((username.charAt(i)>='a' && username.charAt(i)<='z')||
                    (username.charAt(i)>='A' && username.charAt(i)<='Z')||
                    (username.charAt(i)>='0' && username.charAt(i)<='9')))
                return false;
        }
        return true;
    }

    /**
     * Checks if the id is valid(if it is pozitive)
     * @param id the int to be verified
     * @return true if it is valid, false otherwise
     */
    public boolean checkId(Long id){
        if(id<0)
            throw new EntityException("Id should be a pozitive number\n");
        return true;
    }

    /**
     * Checks if the name is null or it has forbidden characters
     * @param name the string to be verified
     * @return true if it is valid, false otherwise
     */
    public boolean checkName(String name){
        if(name=="") throw new PersoneException("The first name and the last name shouldn't be empty\n");
        for (int i=0;i<name.length();i++){
            if (!((name.charAt(i)>='a' && name.charAt(i)<='z')||
                    (name.charAt(i)>='A' && name.charAt(i)<='Z') || (name.charAt(i)=='-')) ||
                    name.contains("\'") || name.contains("\""))
                throw new PersoneException("The first name and the last name should contain english characters\n");
            return false;
        }
        return true;
    }
}
