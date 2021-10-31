package Controller;

import Domain.Relationship;
import Domain.User;
import Repository.MemoryRepo;
import Repository.RelationshipMemoryRepo;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.RelationshipRepoException;

import java.util.ArrayList;
import java.util.List;

public class RelationshipController extends Controller<Long, Relationship>{

    public RelationshipController(RelationshipMemoryRepo rep) {
        super.repo=rep;
    }

    public int deleteAllRelationsByUsername(String username){
        List<Long> listId= new ArrayList<>();
        for(Relationship rel: (List<Relationship>) repo.getAll()){
            if(rel.getFirstUserName().equals(username) || rel.getSecondUserName().equals(username)){
                listId.add(rel.getId());
            }
        }
        for(Long el: listId){
            repo.delete(repo.get(el));
        }
        return listId.size();
    }
}
