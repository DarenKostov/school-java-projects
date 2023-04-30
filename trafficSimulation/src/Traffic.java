/*
    Daren Kostov


    this program simulates traffic 

    
*/

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Container;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Traffic implements ActionListener, Runnable{

    JFrame frame = new JFrame("Traffic Simulation!");
    Road road=new Road();


    //sim controls/stats
    JButton start= new JButton("start");
    JButton stop= new JButton("stop");
    JLabel throughputLabel=  new JLabel("Throughput: 0"); 
    

    //add car buttons
    JButton[] addSemi= new JButton[4];
    JButton[] addSports= new JButton[4];
    JButton[] addSUV= new JButton[4];
    JButton[] addT34= new JButton[4];


    //containers for the buttons
    Container controls= new Container();
    Container addCars= new Container();


    
    boolean running=false;
    int carCount=0;
    long time=0;
        
    public Traffic(){
        frame.setSize(1300, 740);
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());
        frame.add(road, BorderLayout.CENTER);


        controls.setLayout(new GridLayout(1, 3));
        controls.add(start);
        start.addActionListener(this);
        controls.add(stop);
        stop.addActionListener(this);
        controls.add(throughputLabel);
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

        
    }



    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource().equals(start)){
            if(running) return;

            running=true;
            carCount=road.getCarCount();
            time=System.currentTimeMillis();
            
            Thread t=new Thread(this);
            t.start();        
        }
    
        if(event.getSource().equals(stop)){
            running=false;
        }

        //are we adding a semi?
        for(int i=0; i<4; i++){
            if(event.getSource().equals(addSemi[i])){
                Vehicle car=new Semi(200, 175*i+70);
                road.add(car);
                frame.repaint();
            }
        }

        
        //are we adding a SUV?
        for(int i=0; i<4; i++){
            if(event.getSource().equals(addSUV[i])){
                Vehicle car=new SUV(200, 175*i+70);
                road.add(car);
                frame.repaint();
            }
        }


        //are we adding a sports car?
        for(int i=0; i<4; i++){
            if(event.getSource().equals(addSports[i])){
                Vehicle car=new Sports(200, 175*i+70);
                road.add(car);
                frame.repaint();
            }
        }



        //are we adding a T34?
        for(int i=0; i<4; i++){
            if(event.getSource().equals(addT34[i])){
                Vehicle car=new T34(200, 175*i+10);
                road.add(car);
                frame.repaint();
            }
        }
        
        
    }

    @Override
    public void run() {
        while(running){
            road.update();

            carCount+=road.getCarCount();
            double throughput=1000*carCount/(double)(System.currentTimeMillis()-time);
            throughputLabel.setText("Throughput: "+throughput);
            
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
