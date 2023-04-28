/*
  Daren Kostov

  A general class for vehicles

*/

import java.awt.Graphics;
import java.awt.Color;


class Vehicle{

  int x;
  int y;
  int width;
  int height;

  float accelaration;
  float speed;
  float maxSpeed;

  double nextX;
  double nextY;

  boolean didWeCalculateTheFuture;

  public Vehicle(int x, int y){
    this.x=x;
    this.y=y;
    this.didWeCalculateTheFuture=false;
    speed=0;
      
    nextX=x;
    nextY=y;
  }  


  //getters
  public int getX(){
    return x;
  }  
  public int getY(){
    return y;
  }  
  public int getWidth(){
    return width;
  }  
  public int getHeight(){
    return width;
  }  


  //calculates where the vehicle will be in the next step depending on its enviroment (vehicles around it)
  public void calculateFuture(Vehicle outsideVehicle){



    //have we calculated our future in this step before?
    if(didWeCalculateTheFuture==false){
      didWeCalculateTheFuture=true;
      nextX+=speed;
      nextY+=0;
    }


    //we are not an "outside vehicle"
    if(outsideVehicle==this){
      return;
    }


    if(areTheyInFront(outsideVehicle)){
      nextX=x;
      nextY=y;
      speed=0;
    }

  
  }

  //updates the vehicles position according to its calculated future
  public void update(){
    x=(int)nextX;
    y=(int)nextY;
    speed=Math.min(maxSpeed, speed+accelaration);
    didWeCalculateTheFuture=false;


    if(x>1300){
      x=0;
      nextX=0;
    }

  }

  //gets where the vehicle should be in the next step
  public int getNextX(){
    return 99;
  }  
  public int getNextY(){
    return 99;
  }  


  //are we colliding with a vehicle
  public boolean areWeColliding(Vehicle vehicle1){

    Vehicle vehicle2=this;
  
    //why are we checking if we are colliding with ourselves?
    if(vehicle1==vehicle2){
      return false;
    }

    if (vehicle2.getX() <= vehicle1.getX() + vehicle1.getWidth()) {
      if (vehicle1.getX() <= vehicle2.getX() + vehicle2.getWidth()) {
        if (vehicle2.getY() <= vehicle1.getY() + vehicle1.getHeight()) {
          if (vehicle1.getY() <= vehicle2.getY() + vehicle2.getHeight()) {
            return true;
          }
        }
      }
    }

    return false;
  
  }


  private boolean areTheyInFront(Vehicle vehicle1){
  
    Vehicle vehicle2=this;
  
    //why are we checking if we are colliding with ourselves?
    if(vehicle1==vehicle2){
      return false;
    }
    
    if (vehicle2.getX()+vehicle2.getWidth() <= vehicle1.getX() + vehicle1.getWidth()) {
      if (vehicle1.getX() <= vehicle2.getX() + vehicle2.getWidth()*2) {
        if (vehicle2.getY() <= vehicle1.getY() + vehicle1.getHeight()) {
          if (vehicle1.getY() <= vehicle2.getY() + vehicle2.getHeight()) {
            return true;
          }
        }
      }
    }
  
    return false;
  }

  public void draw(Graphics g){
    g.setColor(Color.MAGENTA);
    g.fillRect(x, y, width, height);
  }



}
