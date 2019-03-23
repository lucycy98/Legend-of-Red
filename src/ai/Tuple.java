package ai;

public class Tuple {
    public final int x;
    public final int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Tuple otherTuple){
        return (x == otherTuple.x & y == otherTuple.y);
    }
}