package Repository;
import Domain.*;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepo<Id, E extends Entity<Id>> implements Repository<Id,E> {

    List<E> list;
    int size=0;

    private static MemoryRepo instance=null;
    public static MemoryRepo getInstance(){
        if(instance==null) return new MemoryRepo();
        return instance;

    }
    private MemoryRepo(){
        list= new ArrayList<E>();
        size=0;
    }

    @Override
    public boolean save(E entity) {
        if(get(entity.getId())!=null) return false;
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
        if(!delete(id))return false;
        list.add(entity);
        size++;
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

}
