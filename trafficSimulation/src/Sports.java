/*

  Daren Kostov

  a sports car class, like vehicle but more specific

*/
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;

class Sports extends Vehicle{

  public Sports(int x, int y){
    super(x, y);

    width=40;
    height=20;

    maxSpeed=8;
    accelaration=0.1f;

    try{
      vehicleImage=ImageIO.read(new File("sports.png"));
    }catch(Exception ex){
      ex.printStackTrace();
    }


  }
  
  public void draw(Graphics g){
    super.draw(g);
  }


}
