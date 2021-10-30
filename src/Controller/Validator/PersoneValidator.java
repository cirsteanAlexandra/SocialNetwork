package Controller.Validator;

import Domain.Entity;
import Domain.Persone;
import Utils.Exceptions.PersoneException;

public class PersoneValidator extends AbstractValidator<Persone> {

    public PersoneValidator(){
        listOfErrors=new String();
    }

    @Override
    public boolean validate(Persone entity) {
        checkName(entity.getFirstName());
        checkName(entity.getLastName());
        return true;
    }


}
