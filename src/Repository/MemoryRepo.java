package Repository;
import Domain.*;
import Utils.Generator;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public abstract class MemoryRepo<Id, E extends Entity<Id>> implements Repository<Id,E> {

    protected List<E> list;
    protected int size=0;

    protected void initiateRepo(){
        list= new ArrayList<E>();
        size=0;
    }

    @Override
    public abstract boolean save(E entity);

    protected boolean saveToRepo(E entity) {
        if(entity.getId()==null) entity.setId(generateId());
        else if(get(entity.getId())!=null) return false;
        list.add(entity);
        size++;
        return true;
    }

    @Override
    public E get(Id id) {
        E entity = null;
        for (E ent : list) {
            if (ent.getId() == id)
                entity = ent;
        }
        return entity;
    }

    @Override
    public boolean update(Id id,E entity) {
        E entity_copy=get(id);
        if(!delete(id) && !save(entity)){
            save(entity_copy);
            return false;
        }
        save(entity);
        return true;
    }

    @Override
    public boolean delete(Id id) {
        int index=getIndexOf(id);
        if(index==-1) return false;
        list.remove(index);
        size--;
        return true;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public List<E> getAll() {
        return list;
    }

    private int getIndexOf(Id id){
        int index=0;
        for (E ent : list) {
            if (ent.getId() == id)
                return index;
            index++;
        }
        return -1;
    }

    public List<Id> getAllIds(){
        List<Id> listId= new ArrayList<Id>();
        for (E ent: list){
            listId.add(ent.getId());
        }
        return listId;
    }

    protected abstract Id generateId();

    public abstract E getByOther(String... other);
}
