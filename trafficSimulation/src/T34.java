/*

  Daren Kostov

  a T34 tank class, like vehicle but more specific

*/
import java.awt.Graphics;
import java.awt.Color;

class T34 extends Vehicle{

  public T34(int x, int y){
    super(x, y);

    width=140;
    height=120;

    maxSpeed=5;
    accelaration=1;

  }
  
  public void draw(Graphics g){
    super.draw(g);
    g.setColor(Color.GREEN);
    g.fillRect(x, y, width, height);
  }


}
