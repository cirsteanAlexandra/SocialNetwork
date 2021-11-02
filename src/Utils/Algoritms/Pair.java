package Utils.Algoritms;

import java.util.Objects;

public class Pair<E,T> {
    E first;
    T second;

    public Pair(E first, T second) {
        this.first = first;
        this.second = second;
    }

    public E getFirst(){
        return first;
    }

    public T getSecond(){
        return second;
    }

    public void setFirst(E first) {
        this.first = first;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
