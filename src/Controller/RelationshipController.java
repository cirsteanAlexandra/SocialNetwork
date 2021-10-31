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


}
