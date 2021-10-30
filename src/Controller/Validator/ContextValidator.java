package Controller.Validator;

public class ContextValidator {
    //private Strategy strategy;
    public ContextValidator(){};

    public static AbstractValidator createValidator(Strategy strategy){
      switch(strategy) {
          case PERSONE:
              return new PersoneValidator();
          case USER:
              return new UserValidator();
          case RELATIONSHIP:
              return new RelationshipValidator();
      }
      return null;
    }

}
