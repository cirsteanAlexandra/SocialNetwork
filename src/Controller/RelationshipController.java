package Controller;

import Domain.Relationship;
import Domain.User;
import Repository.MemoryRepo;
import Utils.Exceptions.EntityRepoException;
import Utils.Exceptions.RelationshipRepoException;

public class RelationshipController extends Controller<Long, Relationship>{

    public RelationshipController(MemoryRepo rep) {
        super.repo=rep;
    }

    @Override
    public boolean removeByOthers(String... others) {
        Relationship rel= (Relationship) repo.getByOther(others[0],others[1]);
        if(rel==null)
            throw new RelationshipRepoException("There in not a user with that id\n");
        repo.delete(rel.getId());
        return true;
    }

    @Override
    public Relationship getByOther(String... others) {
        return (Relationship) repo.getByOther(others[0],others[1]);
    }
}
