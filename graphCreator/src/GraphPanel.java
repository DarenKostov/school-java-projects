/*
    Daren Kostov

    The graph panel
    

*/


import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

class GraphPanel extends JPanel{

    List<Node> nodes= new ArrayList<Node>();
    ConcurrentHashMap<Node, ConcurrentHashMap<Node, Integer>> links= new ConcurrentHashMap<Node, ConcurrentHashMap<Node, Integer>>();

    Node SelectedNode=null;


    //adds a node
    public void addNode(int x, int y, String label){
        Node node=new Node(x, y, label);
    
        nodes.add(node);


        /*add to the 2d table

        we are adding w
        - means 0
        = means any int value
        + means un-initialized or doesn't exist

        goal:

          a b c w
        a = = = -
        b = = = -
        c = = = -
        w - - - -

        */

        links.put(node, new ConcurrentHashMap<Node, Integer>());
        /*^^^^^^
          a b c
        a = = =
        b = = =
        c = = =
        w + + +
        */


        for(Node row : links.keySet()){
        
            links.get(node).put(row, 0);
            /*^^^^^^
                // a b c w
              a = = = +
              b = = = +
              c = = = +
              w - - - -
            */
            links.get(row).put(node, 0);
            /*^^^^^^
                a b c w
              a = = = -
              b = = = -
              c = = = -
              w + + + -
            */
        }

        
        System.out.println("added node");
    }


    public boolean addLink(int x1, int y1, int x2, int y2, int cost){
        Node node1=returnNodeAtCords(x1, y1);
        Node node2=returnNodeAtCords(x2, y2);

        //dont connect a node to itself or if either of the node are null
        if(node1==node2 || node1==null || node1==null){
            return false;
        }

        //add the link for both nodes
        links.get(node1).put(node2, cost);
        links.get(node2).put(node1, cost);

        return true;
        
    }



    //selects a node, and returns you whether the action was successfull or not
    public boolean selectNode(int x, int y){
        for(Node node : nodes){
            if(node.withinMe(x, y)){
                SelectedNode=node;
                return true;
            }
        }
        SelectedNode=null;
        return false;
    }
    
    public GraphPanel(){
        super();
    }



    public String calcShortestDistance(int x1, int y1, int x2, int y2){
        
        Node node2=returnNodeAtCords(x1, y1);
        Node node1=returnNodeAtCords(x2, y2);



        System.out.println(node1.getLabel()+"--"+node2.getLabel());
            
        //dont connect a node to itself or if either of the node are null
        if(node1==node2){
            return "node cannot be the same\nif you are connecting nodes, make sure nothing is selected";
        }
        if(node1==null || node1==null){
            return "one or both nodes don't exist\nHow did we get here?";
        }


        //make sure the nodes are connected
        if(!areNodesConnected(node1, node2)){
            return "nodes are not connected";
        }


        String output="Optimal path:\n"+node1.getLabel();
        Stack<Node> shortestPath=new Stack<Node>();
        
        
        int totalCost=findShortesPath(shortestPath, node1, node2);


        //make the path
        shortestPath.pop();
        while(!shortestPath.empty()){
            output+="->"+shortestPath.pop().getLabel();
        }

        output+="\nWith the cost of "+totalCost;
        


        
        return output;
    }

    //this uses Djikstra's Algorithm (from c++'s graphing assignment)
    private int findShortesPath(Stack<Node> path, Node node1, Node node2){

        List<Node> notVisited=new ArrayList<Node>();
        HashMap<Node, Integer> shortestDistanceFromDestination=new HashMap<Node, Integer>();
        HashMap<Node, Node> previousNode=new HashMap<Node, Node>();
        

        notVisited.addAll(nodes);


        //set up
        for(Node node : nodes){
            notVisited.add(node);
            shortestDistanceFromDestination.put(node, Integer.MAX_VALUE);
            previousNode.put(node, null);
        }
        shortestDistanceFromDestination.put(node1, 0);



        while(!notVisited.isEmpty()){

            Node current=null;

            //get the node with smallest distance
            {
                int val=Integer.MAX_VALUE;
                for(Node node : notVisited){
                    if(shortestDistanceFromDestination.get(node)<val){
                        val=shortestDistanceFromDestination.get(node);
                        current=node;
                    }
                }
            }

            //get all adjacent nodes
            for(Node node : nodes){
                if(links.get(current).get(node)==0)
                    continue;


                //if the previous path costed more, replace it with this (cheaper) path
                int linkSum=links.get(current).get(node)+shortestDistanceFromDestination.get(current);
                if(shortestDistanceFromDestination.get(node)>linkSum){
                    previousNode.put(node, current);
                    shortestDistanceFromDestination.put(node, linkSum);
                }
            }

            //we just visited this node
            notVisited.remove(current);
        
        }
        

        //store the shortest path
        Node current=node2;
        while(current!=null){
            path.add(current);
            current=previousNode.get(current);
        }
        

        //return the cost of the shortest path
        return shortestDistanceFromDestination.get(node2);



    }


    private boolean areNodesConnected(Node node1, Node node2){
        Queue<Node> queue=new LinkedList<Node>();
        List<Node> connectedNodes= new ArrayList<Node>();

        Node currentNode=node1;

        
        //add connected nodes to the queue
        for(Node node : links.get(currentNode).keySet()){
            if(links.get(currentNode).get(node)==0)
                continue;

            queue.add(node);
        }
        connectedNodes.add(currentNode);


        //go through the entire queue
        while(!queue.isEmpty()){
            currentNode=queue.remove();

            //the second node is connected
            if(currentNode==node2)
                return true;
            
            connectedNodes.add(currentNode);

            //if the node wasnt recorded to be cnnected add it to the queue
            for(Node node : links.get(currentNode).keySet()){
                if(links.get(currentNode).get(node)==0)
                    continue;
                
                if(!connectedNodes.contains(node)){
                    queue.add(node);
                }
            }
        
        }

        
        
        return false;
    
    }

    
    private Node returnNodeAtCords(int x, int y){
        for(Node node : nodes){
            if(node.withinMe(x, y)){
                return node;
            }
        }
        return null;
    }
    
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        
        g.setColor(new Color(255, 255, 255));


        //draw links
        for(Node node1 : nodes){
            for(Node node2 : nodes){
                if(links.get(node1).get(node2)>0){
                    g.drawLine(node1.getX(), node1.getY(), node2.getX(), node2.getY());
                    
                    g.setColor(new Color(0, 255, 0));
                    g.drawString(""+links.get(node1).get(node2), (node1.getX()+node2.getX())/2, (node1.getY()+node2.getY())/2);
                    g.setColor(new Color(255, 255, 255));

                }
            }
        }



        //draw nodes
        for(Node node : nodes){

            //draw the selected node differently
            if(SelectedNode==node){
                g.setColor(new Color(255, 100, 0));
                node.draw(g);
                g.setColor(new Color(255, 255, 255));
                continue;
            }
            
            node.draw(g);
        }
        
    }

}
