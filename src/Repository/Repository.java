package Repository;

import Domain.Persone;

import javax.swing.text.html.parser.Entity;

public interface Repository<Id, E extends Persone> {
    public boolean save(E entity);
    public E get(int id);
    public boolean update (int id);
    public boolean delete (int id);
}
