/*
    Daren Kostov
    
    this is a node class that stores it's location and label

    
*/
import java.awt.Graphics;

class Node{

    int x, y;
    String label;
    
    public Node(int x, int y, String label){
        this.x=x;
        this.y=y;
        this.label=label;    
    }


    //tells you if a point is within the node
    public boolean withinMe(int x, int y){
        if(Math.sqrt(Math.pow(x-this.x, 2)+Math.pow(y-this.y, 1))<40){
            return true;
        }
        return false;
    }

    public void draw(Graphics g){
        g.drawOval(x-20, y-20, 40, 40);
        g.drawString(label, x-5, y+5);
    }



}
