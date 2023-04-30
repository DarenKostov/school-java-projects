/*

  Daren Kostov

  a sports car class, like vehicle but more specific

*/
import java.awt.Graphics;
import java.awt.Color;

class Sports extends Vehicle{

  public Sports(int x, int y){
    super(x, y);

    width=40;
    height=20;

    maxSpeed=8;
    accelaration=0.1f;

  }
  
  public void draw(Graphics g){
    super.draw(g);
    g.setColor(Color.RED);
    g.fillRect(x, y, width, height);
  }


}
