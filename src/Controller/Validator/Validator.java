package Controller.Validator;

import Domain.Entity;

public interface Validator<E extends  Entity> {
    boolean validate(E entity);
}
