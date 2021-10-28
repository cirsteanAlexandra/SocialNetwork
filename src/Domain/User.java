package Domain;
import Domain.Persone;

import java.util.Objects;

public class User extends Entity<Long>{
    String username;
    Persone pers;

    public User(String username, Persone pers) {
        this.username = username;
        this.pers = pers;
    }

    public User(Long id,String username, Persone pers) {
        this.username = username;
        this.pers = pers;
        super.setId(id);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId()==((User) o).getId() || Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username,getId());
    }
}
