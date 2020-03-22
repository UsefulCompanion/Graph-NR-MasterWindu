public class Node {

    private String name;
    private int x, y;

    public Node(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
