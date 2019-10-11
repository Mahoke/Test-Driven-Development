package nl.hanze.hive;

import java.util.Objects;

public class Hex {
    private int q;
    private int r;

    public Hex(int q, int r){
        this.q = q;
        this.r = r;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hex hex = (Hex) o;
        return q == hex.q &&
                r == hex.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r);
    }
}
