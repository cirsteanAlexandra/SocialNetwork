package Repository;

import Domain.Relationship;
import Domain.User;
import Utils.Generator;

import java.util.ArrayList;
import java.util.List;

public class RelationshipMemoryRepo extends  MemoryRepo<Long, Relationship>{

    public RelationshipMemoryRepo(){initiateRepo();}

    @Override
    public boolean save(Relationship entity) {
        if(getByUserNames(entity.getFirstUserName(), entity.getSecondUserName())!=null)return false;
        return saveToRepo(entity);
    }

    @Override
    protected Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    public Relationship getByOther(String... other) {
        return getByUserNames(other[0],other[1]);
    }

    public Relationship getByUserNames(String username1, String username2){
        Relationship relation=null;
        for(Relationship rel:getAll()){
            if(rel.getFirstUserName().equals(username1) && rel.getSecondUserName().equals(username2))
                relation=rel;
        }
        return relation;
    }



}
