/*
    Daren Kostov


    this program simulates traffic 

    
*/

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Container;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Traffic implements ActionListener, Runnable{

    JFrame frame = new JFrame("Traffic Simulation!");
    Road road=new Road();


    //sim controls
    JButton start= new JButton("start");
    JButton stop= new JButton("stop");


    //add car buttons
    JButton[] addSemi= new JButton[4];
    JButton[] addSports= new JButton[4];
    JButton[] addSUV= new JButton[4];
    JButton[] addT34= new JButton[4];


    //containers for the buttons
    Container controls= new Container();
    Container addCars= new Container();

    boolean running=false;

        
    public Traffic(){
        frame.setSize(1300, 740);
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());
        frame.add(road, BorderLayout.CENTER);


        controls.setLayout(new GridLayout(1, 2));
        controls.add(start);
        start.addActionListener(this);
        controls.add(stop);
        stop.addActionListener(this);
        frame.add(controls, BorderLayout.SOUTH);


        addCars.setLayout(new GridLayout(4*4, 1));
        for(int i=0; i<4; i++){
            addSemi[i]=new JButton("add semi");
            addCars.add(addSemi[i]);
            addSemi[i].addActionListener(this);
            

            addSports[i]=new JButton("add sports");
            addCars.add(addSports[i]);
            addSports[i].addActionListener(this);

            addSUV[i]=new JButton("add SUV");
            addCars.add(addSUV[i]);
            addSUV[i].addActionListener(this);

        
            addT34[i]=new JButton("add T34");
            addCars.add(addT34[i]);
            addT34[i].addActionListener(this);
        }
        frame.add(addCars, BorderLayout.WEST);

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        
        Vehicle daCar2=new Semi(200, 300);
        road.add(daCar2);
        
    }



    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource().equals(start)){
            if(running) return;

            running=true;
            Thread t=new Thread(this);
            t.start();        
        }
    
        if(event.getSource().equals(stop)){
            running=false;
        }
    }

    @Override
    public void run() {
        while(running){
            road.update();
            frame.repaint();
            try{
                Thread.sleep(10);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    
    }

        
    public static void main(String[] args){
        new Traffic();
    }


    
}
