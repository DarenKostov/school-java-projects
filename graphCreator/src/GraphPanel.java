/*
    Daren Kostov

    The graph panel
    

*/


import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

class GraphPanel extends JPanel{

    List<Node> nodes= new ArrayList<Node>();
    ConcurrentHashMap<Node, ConcurrentHashMap<Node, Integer>> links= new ConcurrentHashMap<Node, ConcurrentHashMap<Node, Integer>>();

    Node SelectedNode=null;


    //adds a node
    public void addNode(int x, int y, String label){
        nodes.add(new Node(x, y, label));
        System.out.println("added node");
    }

    //selects a node, and returns you whether the action was successfull or not
    public boolean SelectNode(int x, int y){
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


    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        
        g.setColor(new Color(255, 255, 255));


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
