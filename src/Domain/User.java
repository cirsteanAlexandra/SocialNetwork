package Domain;
import Domain.Persone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<Long>{
    String username;
    Persone pers;
    List<String>friendsList;

    public User(String username, Persone pers) {
        initialize(username,pers);
    }

    public User(Long id,String username, Persone pers) {
        initialize(username,pers);
        super.setId(id);
    }

    private void initialize(String username, Persone pers){
        this.username = username;
        this.pers = pers;
        friendsList=new ArrayList<String>();
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

    public void addFriend(String userName){
        friendsList.add(userName);
    }

    public void removeFriend(String userName){
        friendsList.remove(userName);
    }

    public List<String> getFriendsList(){
        return friendsList;
    }

    public int getNumberOfFriends(){
        return friendsList.size();
    }

    @Override
    public String toString() {
        if(getId()!=null)
            return "User{" +
                "id" + super.getId().toString()+
                "username='" + username + '\'' +
                ", pers=" + pers +
                '}';
        return "User{" +
                "username='" + username + '\'' +
                ", pers=" + pers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return (getId()!=null && getId()==((User) o).getId()) || Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username,getId());
    }
}
