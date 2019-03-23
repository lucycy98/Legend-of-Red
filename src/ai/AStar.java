package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar {

    private Tuple startPos;
    private Tuple endPos;
    private node startNode;
    private node endNode;
    private List<node> openList = new ArrayList<node>();
    private List<node> closedList = new ArrayList<node>();
    List<node> path = new ArrayList<node>();
    private int[][] map;

    public AStar(Tuple start, Tuple end, int[][] map) {
        startPos = start;
        endPos = end;
        startNode = new node(start);
        endNode = new node(end);
        openList.add(startNode);
        this.map = map;
    }

    public void findPath() {
        while (openList.size() > 0) {

            node currentNode = openList.get(0);

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).getF() < currentNode.getF()) {
                    currentNode = openList.get(i);
                }
            }

            openList.remove(currentNode);
            closedList.add(currentNode);

            if (currentNode.equals(endNode)) {
                path.add(currentNode);
                while (!currentNode.getParent().equals(currentNode)) {
                    path.add(currentNode.getParent());
                    currentNode = currentNode.getParent();
                }
                Collections.reverse(path);
                break;
            }
            //reverse path

            List<node> children = new ArrayList<node>();
            Tuple[] neighbours = {new Tuple(0, -1), new Tuple(0, 1), new Tuple(-1, 0), new Tuple(1, 0), new Tuple(-1, -1), new Tuple(1, -1), new Tuple(-1, 1), new Tuple(1, 1)};

            for (Tuple neighbour : neighbours) {
                Tuple neighbourTuple = new Tuple(currentNode.getPosition().x + neighbour.x, currentNode.getPosition().y + neighbour.y);

                //check for boundary
                //check for obstacles

                node neighbourNode = new node(neighbourTuple);
                neighbourNode.setParent(currentNode);
                children.add(neighbourNode);
            }

            for (node child : children) {
                for (node closed_child : closedList) {
                    if (child.equals(closed_child)) {
                        continue;
                    }
                }

                child.setG(currentNode.getG() + 1);
                child.setH((float) Math.pow(child.getPosition().x - endNode.getPosition().x, 2) + (float) Math.pow(child.getPosition().y - endNode.getPosition().y, 2));
                child.setF(child.getG() + child.getH());

                for (node open_child : openList) {
                    if (child.equals(open_child) & child.getG() < open_child.getG()) {
                        continue;
                    }
                }
                openList.add(child);
            }

        }
    }

}