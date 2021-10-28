package Domain;

import java.util.Objects;

public class Persone {
    String name;
    String prename;

    public Persone(String name, String prename) {
        this.name = name;
        this.prename = prename;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persone persone = (Persone) o;
        return name.equals(persone.name) && prename.equals(persone.prename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prename);
    }

    @Override
    public String toString() {
        return "Persone{" +
                "name='" + name + '\'' +
                ", prename='" + prename + '\'' +
                '}';
    }
}
