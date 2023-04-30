/*

  Daren Kostov

  a SUV car class, like vehicle but more specific

*/
import java.awt.Graphics;
import java.awt.Color;

class SUV extends Vehicle{

  public SUV(int x, int y){
    super(x, y);

    width=220;
    height=60;

    maxSpeed=7;
    accelaration=0.05f;

  }
  
  public void draw(Graphics g){
    super.draw(g);
    g.setColor(Color.MAGENTA);
    g.fillRect(x, y, width, height);
  }


}
