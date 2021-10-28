package Domain;

public class Entity<T> {
    private T id =null;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
