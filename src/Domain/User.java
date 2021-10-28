package Domain;
import Domain.Persone;

public class User {
    int id;
    String username;
    Persone pers;

    public User(int id, String username, Persone pers) {
        this.id = id;
        this.username = username;
        this.pers = pers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Persone getPers() {
        return pers;
    }

    public void setPers(Persone pers) {
        this.pers = pers;
    }
}
