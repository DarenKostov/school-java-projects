import javax.swing.JPanel;
import java.awt.Graphics;


class GraphPanel extends JPanel{

    public GraphPanel(){
        super();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawLine(0, 0, 200, 200);
    }

}
