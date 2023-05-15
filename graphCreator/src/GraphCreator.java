/*
    Daren Kostov

    Graph creator!
    this should work very similarly (code wise) to sgdt which is at
    https://github.com/DarenKostov/sgdt
    
*/

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Container;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;



import java.util.*;

class GraphCreator implements ActionListener, MouseListener{

    JFrame frame= new JFrame("Graph Creator!");
    GraphPanel panel= new GraphPanel();
    JTextField labelsTF= new JTextField();
    Container west= new Container();

    boolean haveSelected=false; //have we selected something
    int previousX=10000;
    int previousY=10000;

    public GraphCreator(){
        frame.setSize(600, 400);
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        west.setLayout(new GridLayout(3, 1));
        
        labelsTF.addActionListener(this);
        frame.add(labelsTF, BorderLayout.SOUTH);
        panel.addMouseListener(this);





        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        
    }




    @Override
    public void actionPerformed(ActionEvent event){
    }

    @Override
    public void mouseClicked(MouseEvent event){
    }

    @Override
    public void mouseEntered(MouseEvent event){

    }

    @Override
    public void mouseExited(MouseEvent event){

    }

    @Override
    public void mousePressed(MouseEvent event){

        //

        
        //if we clicked on a node we are selecting it, if not, we are adding node
        if(panel.selectNode(event.getX(), event.getY())){//selecting node

            //have we previously selected a node? if so we are checking the most cost effective path
            if(haveSelected){
                String output=panel.calcShortestDistance(event.getX(), event.getY(), previousX, previousY);
                JOptionPane.showMessageDialog(frame, output);
                panel.selectNode(10000, 10000);
                haveSelected=false;
                frame.repaint();
                return;
            }
        
            haveSelected=true;
            previousX=event.getX();
            previousY=event.getY();
        }else{//adding node

            //we have selectd a node? desect it and exit
            if(haveSelected){
                haveSelected=false;
                frame.repaint();
                return;
            }
            
            //make sure the node label is not empty
            if("".equals(labelsTF.getText())){
                JOptionPane.showMessageDialog(frame, "Node must have a label");
                return;
            }
            panel.addNode(event.getX(), event.getY(), labelsTF.getText());
        }
        frame.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent event){
        if(haveSelected==true){

            //check if the connection is even possible
            if(!panel.addLink(event.getX(), event.getY(), previousX, previousY, 0)){
                return;
            }

            
            int num=0;

            //make sure we are adding the cost in integers
            try{
                num=Integer.parseInt(labelsTF.getText());
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(frame, "Edges can only be labeled as integers!");
                return;
            }
            

            panel.addLink(event.getX(), event.getY(), previousX, previousY, num);
            haveSelected=false;
            panel.selectNode(10000, 10000);
            frame.repaint();
        }
        // haveSelected=false;
    }








    public static void main(String[] args){
        new GraphCreator();
    }
}
