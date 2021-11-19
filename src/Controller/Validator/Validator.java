package Controller.Validator;

import Domain.Entity;

public interface Validator<E extends  Entity> {
    /**
     * Validates an entity
     * @param entity the object to be verified
     * @return true if it is valid, false otherwise
     */
    boolean validate(E entity);
}
