import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import java.util.*;

class Road extends JPanel{

  int laneWidth=40;
  int laneHeight=10;

  int laneSpacingVertically=0;
  int laneSpacingHorizontaly=0;

  final int lanes=4;
  final int laneSegments=20;


  ArrayList<Vehicle> cars= new ArrayList<Vehicle>();



  
  public Road(){
    super();

    //700x1300 is the size of the window, getWidth() and getHeight() return in the constructor
    laneSpacingVertically=700/(lanes);
    laneSpacingHorizontaly=1300/laneSegments;    
      
  }

  public void add(Vehicle in){
    cars.add(in);
  }


  public void paintComponent(Graphics g){
    super.paintComponent(g);

    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());


    g.setColor(Color.WHITE);
    for(int i=0; i<lanes; i++){
      for(int j=0; j<laneSegments; j++){
        g.fillRect(j*laneSpacingHorizontaly, i*laneSpacingVertically+laneSpacingVertically, laneWidth, laneHeight);
    
      }
    }

    for(Vehicle car : cars){
      car.draw(g);
    }
    

    
  }
  
}
