package Repository;

import Domain.Entity;

public abstract class FileRepo<Id, E extends Entity<Id>> extends MemoryRepo<Id,E>{
    /**
     * Loads data from files
     * @param other a list of strings with the name of the files
     */
    public abstract void loadData(String... other);

    /**
     * Savess data to files
     * @param other a list of strings with the name of the files
     */
    public abstract void saveData(String... other);
}
