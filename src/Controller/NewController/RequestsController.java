package Controller.NewController;

import Domain.Relationship;
import Repository.Db.RequestsDbRepo;

public class RequestsController  extends Controller<Long, Relationship>{

    public RequestsController(RequestsDbRepo rep) {
        super.repo=rep;
    }
    //ceva functie de search pt cereri de prietenie
    // nvm am facut o in main controller

    //ceva fct pt update
    public void UpdateStatus(Long id,Relationship entity){
        repo.update(id,entity);
    }

}
