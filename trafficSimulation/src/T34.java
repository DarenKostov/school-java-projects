/*

  Daren Kostov

  a T34 tank class, like vehicle but more specific

*/
import java.awt.Graphics;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;

class T34 extends Vehicle{


  ArrayList<Vehicle> carsInFrontOfUs= new ArrayList<Vehicle>();


  public T34(int x, int y){
    super(x, y);

    //yes, this tank is way, way too big
    width=170;
    height=120;

    maxSpeed=5;
    accelaration=1f;


    try{
      vehicleImage=ImageIO.read(new File("t34.png"));
    }catch(Exception ex){
      ex.printStackTrace();
    }
    
  }


  //we only go forward (unless theres a tank infront of us)
  public void calculateFuture(Vehicle outsideVehicle){

    //have we calculated our future in this step before?
    if(didWeCalculateTheFuture==false){
      didWeCalculateTheFuture=true;
      nextX+=dx;
      nextY+=dy;
      updateBoundryBoxes();
    }  


    //we will only move to the side if there is a tank
    if(outsideVehicle.amIaTank()){
      if(areTheyInFront(outsideVehicle)){
        someoneAtFront=true;
      }
      if(areTheyOnTheLeft(outsideVehicle)){
        someoneOnLeft=true;
      }
      if(areTheyOnTheRight(outsideVehicle)){
        someoneOnRight=true;
      }
    }else{
      if(areWeColliding(outsideVehicle)){
        carsInFrontOfUs.add(outsideVehicle);
      }
    }

    
  }

  //updates the vehicles position according to its calculated future
  public void update(){
    super.update();

    //push and shrink the vehicle, as well as increase your max speed
    for(Vehicle vehicle : carsInFrontOfUs){
      vehicle.setWidth(vehicle.getWidth()-5);
      vehicle.setX(vehicle.getX()+5+50);
      maxSpeed+=0.1f;
    }

    carsInFrontOfUs.clear();
    
  }

  
  public boolean amIaTank(){
    return true;
  }

  public void draw(Graphics g){
    super.draw(g);
    // g.drawImage(vehicleImage, x, y, width, height, null);
  }


}
