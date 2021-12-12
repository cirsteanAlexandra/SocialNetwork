package Controller.NewController;

import Domain.Relationship;
import Repository.Db.RequestsDbRepo;

import java.util.ArrayList;
import java.util.List;

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

    public int deleteAllRelationsByUsername(String username){
        List<Long> listId= new ArrayList<>();
        for(Relationship rel: (List<Relationship>) repo.getAll()){
            if(rel.getFirstUserName().equals(username) || rel.getSecondUserName().equals(username)){
                listId.add(rel.getId());
            }
        }
        for(Long el: listId){
            repo.delete(el);
        }
        return listId.size();
    }
}
