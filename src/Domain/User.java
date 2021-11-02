package Domain;
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
    /**
     * Initializez the fields of the class
     * @param username
     * @param pers
     */
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

    /**
     * Return the user in an elegant way
     * @return a string that contains the data of that user
     */
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

    /**
     * Checks The Equality of 2 objects
     * @param o the object to be compared with
     * @return true, the the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return (getId()!=null && getId()==((User) o).getId()) || Objects.equals(username, user.username);
    }

    /**
     * Gives the hash code of that object
     * @return hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(username,getId());
    }
}
