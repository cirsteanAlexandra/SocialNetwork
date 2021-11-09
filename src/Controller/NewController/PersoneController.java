package Controller.NewController;

import Domain.Persone;
import Repository.Db.PersoneDbRepo;

public class PersoneController extends  Controller<Long, Persone>{
    PersoneDbRepo repo;

    public PersoneController(PersoneDbRepo repo) {
        this.repo = repo;
    }
}
