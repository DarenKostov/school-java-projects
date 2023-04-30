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

  float accelaration, drag;
  float dx, dy;
  float maxSpeed;

  double nextX;
  double nextY;

  boolean didWeCalculateTheFuture;

  boolean someoneOnLeft=false;
  boolean someoneOnRight=false;
  boolean someoneAtFront=false;

  Box frontBox=new Box();
  Box leftBox=new Box();
  Box rightBox=new Box();


  public Vehicle(int x, int y){
    this.x=x;
    this.y=y;
    this.didWeCalculateTheFuture=false;

    drag=0.95f;
    
    dx=0;
    dy=0;
      
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
    return height;
  }  


  //calculates where the vehicle will be in the next step depending on its enviroment (vehicles around it)
  public void calculateFuture(Vehicle outsideVehicle){



    //have we calculated our future in this step before?
    if(didWeCalculateTheFuture==false){
      didWeCalculateTheFuture=true;
      nextX+=dx;
      nextY+=dy;

      updateBoundryBoxes();
      
    }  



    if(areTheyInFront(outsideVehicle)){
      someoneAtFront=true;
      System.out.println("front");
    }
    if(areTheyOnTheLeft(outsideVehicle)){
      someoneOnLeft=true;
      System.out.println("left");
    }
    if(areTheyOnTheRight(outsideVehicle)){
      someoneOnRight=true;
      System.out.println("right");
    }
    // if(areWeColliding(outsideVehicle)){
      // nextX=x;
      // nextY=y;
      // dx=0;
      // dy=5;

  
  }


  //uodates our collision boxes1
  private void updateBoundryBoxes(){

    frontBox.y=y+height/2-175/2;
    frontBox.x=x+width;
    frontBox.w=225;
    frontBox.h=175;



    leftBox.y=(int)(y+height/2-175*1.5);
    leftBox.x=x+width/2-300;
    leftBox.w=200*3;
    leftBox.h=175;

    rightBox.y=(int)(y+height/2+175*0.5);
    rightBox.x=x+width/2-300;
    rightBox.w=200*3;
    rightBox.h=175;
  
  }

  
  //updates delta values
  private void updateDeltas(){
  
    if(someoneAtFront){
      if(someoneOnLeft){ //L, ?
        if(!someoneOnRight){ // L, r
          //front and left are blocked, go right
          dx=0;
          dy=8;
          return;
        }else{ //L, R
          //front, left and right are blocked, stop
          dx=0;
          return;
        }
      }else{ //l, ?
        if(someoneOnRight){ //l, R
          //front and right are blocked, go left
          dx=0;
          dy=-8;
          return;
        }else{ //l, r
          //only front is blocked, go right
          dx=0;
          dy=8;
          return;
        }
      }
    }

  
  }

  
  //updates the vehicles position according to its calculated future
  public void update(){

    updateDeltas();

    someoneOnLeft=false;
    someoneOnRight=false;
    someoneAtFront=false;
  
    x=(int)nextX;
    y=(int)nextY;
    dx=Math.min(maxSpeed, dx+accelaration);
    
    dy*=drag;
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

    if(vehicle2.getX()<=vehicle1.getX()+vehicle1.getWidth()){
      if(vehicle1.getX()<=vehicle2.getX()+vehicle2.getWidth()){
        if(vehicle2.getY()<=vehicle1.getY()+vehicle1.getHeight()){
          if(vehicle1.getY()<=vehicle2.getY()+vehicle2.getHeight()){
            return true;
          }
        }
      }
    }

    return false;
  
  }

  //tells us if the vehicle is in front of us
  private boolean areTheyInFront(Vehicle vehicle1){
  
    Vehicle vehicle2=this;
  
    //why are we checking if we are colliding with ourselves?
    if(vehicle1==vehicle2){
      return false;
    }


    

    //check if the real vehicle is in front of us
    if (vehicle2.getX()+vehicle2.getWidth()/2 <= vehicle1.getX()+vehicle1.getWidth()){
      if (vehicle1.getX() <= vehicle2.getX()+vehicle2.getWidth()*1.5){
        if (vehicle2.getY() <= vehicle1.getY()+vehicle1.getHeight()){
          if (vehicle1.getY() <= vehicle2.getY()+vehicle2.getHeight()){
            return true;
          }
        }
      }
    }


    //check if a theoretical vehicle is in front of us in the case the vehicle wraps back to the begining 
    if (vehicle2.getX()+vehicle2.getWidth()/2 <= vehicle1.getX() + vehicle1.getWidth()+1300) {
      if (vehicle1.getX()+1300 <= vehicle2.getX() + vehicle2.getWidth()*1.5) {
        if (vehicle2.getY() <= vehicle1.getY() + vehicle1.getHeight()) {
          if (vehicle1.getY() <= vehicle2.getY() + vehicle2.getHeight()) {
            return true;
          }
        }
      }
    }


  
    return false;
  }

  //tells us if the vehicle is left of us
  private boolean areTheyOnTheLeft(Vehicle vehicle1){

    //we are at the edge;
    if(y<100){
      return true;
    }
  
    Vehicle vehicle2=this;
    if(vehicle1==vehicle2){
      return false;
    }
    
    //check if the real vehicle is left of us
    if (vehicle2.getX() <= vehicle1.getX() + vehicle1.getWidth()) {
      if (vehicle1.getX() <= vehicle2.getX() + vehicle2.getWidth()) {
        if (vehicle2.getY() + vehicle2.getHeight()<= vehicle1.getY() + vehicle1.getHeight()) {
          if (vehicle1.getY() <= vehicle2.getY() + vehicle2.getHeight()*2) {
            return true;
          }
        }
      }
    }
  
    //check if a theoretical vehicle is left of us in the case the vehicle wraps back to the begining 
    if (vehicle2.getX() <= vehicle1.getX() + vehicle1.getWidth()+1300) {
      if (vehicle1.getX()+1300 <= vehicle2.getX() + vehicle2.getWidth()) {
        if (vehicle2.getY() + vehicle2.getHeight()<= vehicle1.getY() + vehicle1.getHeight()) {
          if (vehicle1.getY() <= vehicle2.getY() + vehicle2.getHeight()*2) {
            return true;
          }
        }
      }
    }
  
    return false;
  }

  
  //tells us if the vehicle is right of us
  private boolean areTheyOnTheRight(Vehicle vehicle1){
    
    //we are at the edge;
    if(y>550){
      return true;
    }
    
    Vehicle vehicle2=this;
    if(vehicle1==vehicle2){
      return false;
    }
    
    //check if the real vehicle is right of us
    if (vehicle2.getX() <= vehicle1.getX() + vehicle1.getWidth()) {
      if (vehicle1.getX() <= vehicle2.getX() + vehicle2.getWidth()) {
        if (vehicle2.getY() - vehicle2.getHeight()<= vehicle1.getY() + vehicle1.getHeight()) {
          if (vehicle1.getY() <= vehicle2.getY()) {
            return true;
          }
        }
      }
    }
  
    //check if a theoretical vehicle is right of us in the case the vehicle wraps back to the begining 
    if (vehicle2.getX() <= vehicle1.getX() + vehicle1.getWidth()+1300) {
      if (vehicle1.getX()+1300 <= vehicle2.getX() + vehicle2.getWidth()) {
        if (vehicle2.getY() - vehicle2.getHeight()<= vehicle1.getY() + vehicle1.getHeight()) {
          if (vehicle1.getY() <= vehicle2.getY()) {
            return true;
          }
        }
      }
    }
  
    return false;
  }

  public void draw(Graphics g){
    frontBox.draw(g);
    leftBox.draw(g);
    rightBox.draw(g);
    
  }

  private class Box{

    public int x;
    public int y;
    public int w;
    public int h;

    public Box(){
      x=0;
      y=0;
      w=0;
      h=0;
    }
    
    public Box(int x, int y, int w, int h){
      this.x=x;
      this.y=y;
      this.w=w;
      this.h=h;
    }

    public void draw(Graphics g){
      g.setColor(new Color(255, 0, 0, 50));
      g.fillRect(x, y, w, h);
    }
    
  
  }

}
