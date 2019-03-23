package ai;

public class node {

    private float g; //distance to start
    private float h; //heuristic distance to end
    private float f; //cost function

    private node parent;
    private Tuple position;

    public node(Tuple position) {
        this.position = position;
        //default parent to itself
        parent = this;
        g = 0;
        h = 0;
        f = 0;
    }

    public boolean equals(node otherNode) {
        return (position.x == otherNode.getPosition().x & position.y == otherNode.getPosition().y);
    }

    public Tuple getPosition() {
        return position;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public node getParent() {
        return parent;
    }

    public void setParent(node parent) {
        this.parent = parent;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getG() {
        return g;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getH() {
        return h;
    }
}