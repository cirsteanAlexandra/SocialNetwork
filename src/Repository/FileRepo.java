package Repository;

import Domain.Entity;

public abstract class FileRepo<Id, E extends Entity<Id>> extends MemoryRepo<Id,E>{
    public abstract void loadData(String... other);
    public abstract void saveData(String... other);
}
