package Domain;
import Domain.Persone;

public class User extends Entity<Long>{
    String username;
    Persone pers;

    public User(String username, Persone pers) {
        this.username = username;
        this.pers = pers;
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

    @Override
    public String toString() {
        return "User{" +
                "id" + super.getId().toString()+
                "username='" + username + '\'' +
                ", pers=" + pers +
                '}';
    }
}
