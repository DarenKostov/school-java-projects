/*

  Daren Kostov

  a SUV car class, like vehicle but more specific

*/
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;

class SUV extends Vehicle{

  public SUV(int x, int y){
    super(x, y);

    width=60;
    height=30;

    maxSpeed=10;
    accelaration=0.05f;

    try{
      vehicleImage=ImageIO.read(new File("suv.png"));
    }catch(Exception ex){
      ex.printStackTrace();
    }


  }
  
  public void draw(Graphics g){
    super.draw(g);
  }


}
