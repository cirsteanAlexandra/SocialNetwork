package Controller.NewController;

import Domain.Persone;
import Repository.Db.PersoneDbRepo;

public class PersoneController extends  Controller<Long, Persone>{
    public PersoneController(PersoneDbRepo repo) {
        super.repo = repo;
    }
}
