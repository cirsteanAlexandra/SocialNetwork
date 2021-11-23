package Controller.Validator;

import Domain.Message;
import Domain.User;
import Utils.Exceptions.Exception;
import Utils.Exceptions.MessageException;
import Utils.Exceptions.UserException;

public class MessageValidator extends AbstractValidator<Message> {
    public MessageValidator() {
    }

    @Override
    public boolean validate(Message entity) {
        listOfErrors=new String();
        Validator validU= ContextValidator.createValidator(Strategy.USER);
        try{
            if(entity.getId()!=null)checkId(entity.getId());
        }catch(Exception e){
            listOfErrors+=e.getDescription();
        }
        try{
            validU.validate(entity.getFrom());
        }catch(UserException e){
            listOfErrors+=e.getDescription();
        }
        for(User user:entity.getReceivers()){
            try{
                validU.validate(user);
            }catch(UserException e){
                listOfErrors+=user.getUsername()+" "+e.getDescription();
            }
        }
        if(!listOfErrors.isEmpty()) throw new MessageException(listOfErrors);
        return true;
    }
}
