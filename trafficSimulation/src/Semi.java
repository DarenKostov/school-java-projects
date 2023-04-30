/*

  Daren Kostov

  a semi car class, like vehicle but more specific

*/
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;

class Semi extends Vehicle{

  public Semi(int x, int y){
    super(x, y);

    width=120;
    height=40;

    maxSpeed=7;
    accelaration=0.1f;

    try{
      vehicleImage=ImageIO.read(new File("semi.png"));
    }catch(Exception ex){
      ex.printStackTrace();
    }


  }
  
  public void draw(Graphics g){
    super.draw(g);
  }


}
