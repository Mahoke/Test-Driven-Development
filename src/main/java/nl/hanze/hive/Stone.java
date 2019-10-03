package nl.hanze.hive;

import java.util.Objects;

public class Stone{

    Hive.Player color;

    public Stone(Hive.Player color){
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stone stone = (Stone) o;
        return color == stone.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}