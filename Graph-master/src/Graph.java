import java.awt.*;

public class Graph {

    private int nodeAmount;
    private Node[] nodes;

    private int[][] matrix;

    private boolean[] visited;
    private int[] distance, lastNodeIndex;

    public Graph(int nodeAmount) {
        this.nodes = new Node[nodeAmount];
        this.matrix = new int[nodeAmount][nodeAmount];
        this.nodeAmount = 0;
    }

    public int getNodeIndexByNode(Node node) {
        int index = -1;
        for (int i = 0; i < nodeAmount; i++) {
            if (nodes[i].equals(node)) {
                index = i;
            }
        }
        return index;
    }

    public int getNodeIndex(String nodeName) {
        int index = -1;
        for (int i = 0; i < nodeAmount; i++) {
            if (nodes[i].getName().equals(nodeName)) {
                index = i;
            }
        }
        return index;

    }

    public void insertNode(String nodeName, int x, int y) {
        if (nodeAmount < nodes.length) {
            int nodeAlreadyExisting = getNodeIndex(nodeName);
            if (nodeAlreadyExisting == -1 && !nodeName.isBlank()) {
                Node newNode = new Node(nodeName, x, y);
                nodes[nodeAmount] = newNode;
                matrix[nodeAmount][nodeAmount] = 0;
                nodeAmount++;
            } else {
                System.out.println("---Invalid");
            }
        } else {
            System.out.println("---Full");
        }

    }

    public void insertPath(String place1, String place2, int length) {
        int indexPlace1 = getNodeIndex(place1);
        int indexPlace2 = getNodeIndex(place2);
        matrix[indexPlace1][indexPlace2] = length;
        matrix[indexPlace2][indexPlace1] = length;
    }

    @Override
    public String toString() {
        String output = "Node amount: " + nodeAmount + "\nMax Nodes: " + nodes.length + "\n\nMatrix:";

        for (int j = 0; j < nodeAmount; j++) {
            output += "\n | ";
            for (int i = 0; i < nodeAmount; i++) {
                output += matrix[j][i] + " \t";
            }
        }

        return output;
    }

    public void depthFirstSearch(String startNodeName) {
        int startIndex = getNodeIndex(startNodeName);
        if (startIndex != -1) {
            visited = new boolean[nodeAmount];
            visitNode(startIndex);
        } else {
            System.out.println("---Unavailable");
        }
    }

    private void visitNode(int indexNode) {
        visited[indexNode] = true;
        System.out.println("Visited node : " + nodes[indexNode].getName());
        for (int indexTargetNode = 0; indexTargetNode < nodeAmount; indexTargetNode++) {
            if (matrix[indexNode][indexTargetNode] > 0 && !visited[indexTargetNode]) {
                visitNode(indexTargetNode);
            }
        }
    }

    //------

    public void searchShortestWayBruteForce(String startNodeName, String endNodeName) {
        int indexStart = getNodeIndex(startNodeName);
        int indexEnd = getNodeIndex(endNodeName);

        if (indexStart != -1 && indexEnd != -1 && indexStart != indexEnd) {
            visited = new boolean[nodeAmount];
            runPath(indexStart, indexEnd, startNodeName, 0);
        }
    }

    private void runPath(int indexNode, int indexEnd, String path, int length) {
        int currentLength;
        String currentPath;

        visited[indexNode] = true;

        if (indexNode == indexEnd) {
            System.out.println("End: " + path + " : " + length);
        } else {
            for (int indexTargetNode = 0; indexTargetNode < nodeAmount; indexTargetNode++) {
                if (matrix[indexNode][indexTargetNode] > 0 && !visited[indexTargetNode]) {
                    currentPath = path + " - " + nodes[indexTargetNode].getName();
                    currentLength = length + matrix[indexNode][indexTargetNode];
                    runPath(indexTargetNode, indexEnd, currentPath, currentLength);
                }
            }
        }
        visited[indexNode] = false;
    }

    private int getClosestNode() {
        int indexOfClosestNode = -1;
        int minimalDistance = Integer.MAX_VALUE;
        for (int targetNode = 0; targetNode < nodeAmount; targetNode++) {
            if (!visited[targetNode] && distance[targetNode] < minimalDistance) {
                minimalDistance = distance[targetNode];
                indexOfClosestNode = targetNode;
            }
        }
        return indexOfClosestNode;
    }

    //Greedy
    public void searchShortestWay(String startNodeName, String endNodeName) {
        int indexStart = getNodeIndex(startNodeName);
        int indexEnd = getNodeIndex(endNodeName);
        int indexActiveNode;
        int currentDistance;

        visited = new boolean[nodeAmount];
        distance = new int[nodeAmount];
        lastNodeIndex = new int[nodeAmount];
        for (int i = 0; i < nodeAmount; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        distance[indexStart] = 0;
        lastNodeIndex[indexStart] = indexStart;

        for (int i = 1; i < nodeAmount; i++) {
            indexActiveNode = getClosestNode();
            visited[indexActiveNode] = true;

            for (int targetNode = 0; targetNode < nodeAmount; targetNode++) {
                int distanceToTargetNode = matrix[indexActiveNode][targetNode];

                if (distanceToTargetNode > 0 && !visited[targetNode]) {
                    currentDistance = distance[indexActiveNode] + distanceToTargetNode;

                    if (currentDistance < distance[targetNode]) {
                        distance[targetNode] = currentDistance;
                        lastNodeIndex[targetNode] = indexActiveNode;
                    }
                }
            }

        }

        String shortestPath = endNodeName;
        indexActiveNode = indexEnd;
        while (indexActiveNode != indexStart) {
            indexActiveNode = lastNodeIndex[indexActiveNode];
            shortestPath = nodes[indexActiveNode].getName() + " - " + shortestPath;
        }
        System.out.println("Shortest Path: " + shortestPath + " - Distance: " + distance[indexEnd]);

    }

    public void render(Graphics g) {

        g.setColor(Color.black);
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                if(i != j && matrix[i][j] != 0) {
                    g.drawLine(20+nodes[i].getX(), 20+nodes[i].getY(), 20+nodes[j].getX(), 20+nodes[j].getY());

                    int middleX, middleY;

                    if(nodes[i].getX()>nodes[j].getX()) middleX = (nodes[i].getX() - nodes[j].getX())/2+nodes[j].getX();
                    else middleX = (nodes[j].getX() - nodes[i].getX())/2+nodes[i].getX();

                    if(nodes[i].getY()>nodes[j].getY()) middleY = (nodes[i].getY() - nodes[j].getY())/2+nodes[j].getY();
                    else middleY = (nodes[j].getY() - nodes[i].getY())/2+nodes[i].getY();

                    g.drawString("" + matrix[i][j], middleX+20, middleY+20);
                }
            }
        }

        g.setColor(Color.white);
        for(int i = 0; i < nodeAmount; i++) {
            g.fillOval(20+nodes[i].getX()-10,20+nodes[i].getY()-10,20,20);
            g.drawString(nodes[i].getName(),nodes[i].getX(),20+nodes[i].getY()-10);
        }

    }

}