/*

  Daren Kostov

  a semi car class, like vehicle but more specific

*/
import java.awt.Graphics;
import java.awt.Color;

class Semi extends Vehicle{

  public Semi(int x, int y){
    super(x, y);

    width=120;
    height=40;

    maxSpeed=50;
    accelaration=0.1f;
  }
  
  public void draw(Graphics g){
    g.setColor(Color.ORANGE);
    g.fillRect(x, y, width, height);
  }


}
