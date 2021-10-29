package Domain;

import java.util.Objects;

public class Relationship extends Entity<Long>{
    private String userName1;
    private String userName2;

    public Relationship(String firstUserName, String secondUserName) {
        initialize(firstUserName,secondUserName);
    }

    public Relationship(Long id,String firstUserName, String secondUserName) {
        initialize(firstUserName,secondUserName);
        super.setId(id);
    }

    private void initialize(String firstUserName, String secondUserName){
        this.userName1 = firstUserName;
        this.userName2 = secondUserName;
    }

    public String getFirstUserName() {
        return userName1;
    }

    public void setFirstUserName(String userName1) {
        this.userName1 = userName1;
    }

    public String getSecondUserName() {
        return userName2;
    }

    public void setSecondUserName(String userName2) {
        this.userName2 = userName2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return (getId()!=null && getId()==((Relationship) o).getId()) || (Objects.equals(userName1, that.userName1) && Objects.equals(userName2, that.userName2));
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName1, userName2,getId());
    }

    @Override
    public String toString() {
        if (getId()!=null)
            return "Relationship{" +
                "id= "+ super.getId().toString() + " between "
                +'\''+ userName1 + '\'' + " and " +
                '\'' + userName2 + '\'' +
                '}';
        return "Relationship{" + " between "
                +'\''+ userName1 + '\'' + " and " +
                '\'' + userName2 + '\'' +
                '}';
    }

}
