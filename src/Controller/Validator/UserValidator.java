package Controller.Validator;

import Domain.User;
import Utils.Exceptions.Exception;
import Utils.Exceptions.PersoneException;
import Utils.Exceptions.UserException;

public class UserValidator extends AbstractValidator<User>{

    public UserValidator(){
        listOfErrors= new String();
    }

    @Override
    public boolean validate(User entity) {
        listOfErrors=new String();
        try{
            if(entity.getId()!=null){
                checkId(entity.getId());
            }
        }
        catch (Exception e){
            listOfErrors+=e.getDescription();
        }
        try{
            //PersoneValidator vali=new PersoneValidator();
            //vali.validate(entity.getPers());
            checkName(entity.getPers().getFirstName());
            checkName(entity.getPers().getLastName());
        }
        catch (PersoneException e){
            listOfErrors+=e.getDescription();
        }
        try{
            checkUserName(entity.getUsername());
        }
        catch (Exception e){
            listOfErrors+=e.getDescription();
        }
        //System.out.println(listOfErrors);
        if(!listOfErrors.isEmpty()) throw new UserException(listOfErrors);
        return true;
    }

}
