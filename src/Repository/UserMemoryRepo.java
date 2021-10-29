package Repository;

import Domain.User;
import Utils.Generator;

import java.util.ArrayList;

public class UserMemoryRepo extends MemoryRepo<Long, User>{

    public UserMemoryRepo(){
        initiateRepo();
    }

    public User getByUserName(String username){
        User user=null;
        for(User use:getAll()){
            if(use.getUsername().equals(username))
                user=use;
        }
        return user;
    }

    @Override
    public boolean save(User entity) {
        if(getByUserName(entity.getUsername())!=null)return false;
        return saveToRepo(entity);
    }

    @Override
    protected Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    public User getByOther(String... other) {
        return getByUserName(other[0]);
    }
}
