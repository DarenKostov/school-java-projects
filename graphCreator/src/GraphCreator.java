/*
    Daren Kostov

*/

import javax.swing.JFrame;
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
    JButton nodeB= new JButton("Node");
    JButton edgeB= new JButton("Edge");
    JTextField labelsTF= new JTextField();
    Container west= new Container();


    public GraphCreator(){
        frame.setSize(600, 400);
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        west.setLayout(new GridLayout(3, 1));
        
        west.add(nodeB);
        nodeB.addActionListener(this);
        west.add(edgeB);
        edgeB.addActionListener(this);
        west.add(labelsTF);
        labelsTF.addActionListener(this);

        frame.add(west, BorderLayout.WEST);






        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        
    }




    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getSource().equals(nodeB)){
            System.out.println("Node!");
        }else if(event.getSource().equals(edgeB)){
            System.out.println("Edge!");
        }
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

    }

    @Override
    public void mouseReleased(MouseEvent event){

    }








    public static void main(String[] args){
        new GraphCreator();
    }
}
