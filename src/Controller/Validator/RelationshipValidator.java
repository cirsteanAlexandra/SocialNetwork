package Controller.Validator;

import Domain.Relationship;
import Utils.Exceptions.Exception;
import Utils.Exceptions.RelationshipException;
import Utils.Exceptions.UserException;

public class RelationshipValidator extends AbstractValidator<Relationship>{
    public RelationshipValidator() {
        listOfErrors=new String();
    }

    @Override
    public boolean validate(Relationship entity) {
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
            checkUserName(entity.getFirstUserName());
            checkUserName(entity.getSecondUserName());
        }
        catch (Exception e){
            listOfErrors+=e.getDescription();
        }
        //System.out.println(listOfErrors);
        if(!listOfErrors.isEmpty()) throw new RelationshipException(listOfErrors);
        return true;
    }
}
