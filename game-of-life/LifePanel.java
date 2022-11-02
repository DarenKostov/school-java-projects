import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class LifePanel extends JPanel {

	boolean[][] cell;
	int[][] cellColor;
	double width;
	double height;
	
	
	
	
	public LifePanel(boolean[][] grid, int[][] col) {
		cell=grid;
		cellColor=col;
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		width=(double)this.getWidth() /cell.length;
		height=(double)this.getHeight() /cell[0].length;
		
		//draw the dead cells, leave the alive cells the background color
		for (int x=0; x<cell.length;x++) {
			for (int y=0; y<cell[0].length;y++) {
				
				//set color, the more recently alive, the less saturated and more bright it is, less recently alive the more saturated and less bright it is
				Color color = Color.getHSBColor( (float)cellColor[x][y]/360, 1-(float)cellColor[x][y]/360, (float)cellColor[x][y]/360);
				g.setColor(new Color(color.getRed(), color.getBlue(), color.getGreen()));

				
				
				//if its an alive cell don't draw anything, leaving it white
				if(!cell[x][y])
					g.fillRect((int)(x*width), (int)(y*height), (int)width+1, (int)height+1);
			}		
		}
		
		//draw separating lines
		g.setColor(Color.BLACK);
		for (int x=1; x<cell.length;x++) {
			g.drawLine((int)Math.floor(x*width), 0, (int)Math.floor(x*width), this.getHeight());
		}
		for (int y=1; y<cell[0].length;y++) {
			g.drawLine(0, (int)Math.floor(y*height), this.getWidth(), (int)Math.floor(y*height));
		}
		
		
	}
	
	
}
