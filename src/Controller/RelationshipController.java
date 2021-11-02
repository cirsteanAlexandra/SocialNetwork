package Controller;

import Domain.Relationship;
import Repository.RelationshipMemoryRepo;
import Utils.Algoritms.Graph;

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

    public int getNumberOfCommunities(int numOfUsers){
        Graph graph= new Graph(numOfUsers,repo.getSize());
        return graph.numberOfCommunities((RelationshipMemoryRepo) repo);
    }

    public List<String> getTheMostSociableCommunity(int numOfUsers){
        Graph graph= new Graph(numOfUsers,repo.getSize());
        return graph.theMostSociableCommunity((RelationshipMemoryRepo) repo);
    }
}
