package graph;

public class Node {
    private int x, y;
    private String id;
    private int distance = Integer.MAX_VALUE;
    private Node previous;
    private boolean isStart;
    private boolean isEnd;

    public Node(int x, int y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getId() { return id; }
    public int getDistance() { return distance; }
    public void setDistance(int distance) { this.distance = distance; }
    public Node getPrevious() { return previous; }
    public void setPrevious(Node previous) { this.previous = previous; }
    public boolean isStart() { return isStart; }
    public void setStart(boolean start) { isStart = start; }
    public boolean isEnd() { return isEnd; }
    public void setEnd(boolean end) { isEnd = end; }
}