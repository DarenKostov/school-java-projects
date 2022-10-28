/*
	Daren Kotsov
	Game of Life
	10/4/2022
	
	on line 27 you can change the grid size
	on line 29 you can change the brush size
	on line 31 you can change the fade out effect's opacity
	
	
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameOfLife implements MouseListener, ActionListener, Runnable {

	//replaces the brush with a glider
	final boolean gliderMode=false;
	
	// range is 1 to infinity
	final int gridSize=200;
	
	//range is 1 to infinity
	final int brushSize=10;
	
	//range is 1 to 0
	final float fadeOutEffect=0.99f;

	//speed of simulation, less is faster, range is 0 to infinity
	final int speed=10;
	
	
	
	

	
	
	//glider #
	int glider=0;
	//cell[0] is current, cell[1] is next
	boolean[][][] cell= new boolean[2][gridSize][gridSize];
	//stores the color of the cell
	int[][] cellColor=new int[gridSize][gridSize];
	
	//if a the game is running automatically
	boolean running=false;
	
	JFrame frame = new JFrame("Game of Life simulation.");
	LifePanel panel = new LifePanel(cell[0], cellColor);
	
	//buttons
	Container south = new Container();
	JButton step= new JButton("Step");
	JButton start= new JButton("Start");
	JButton stop= new JButton("Stop");


	
	public GameOfLife(){

		frame.setSize(1000,1000);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		
		for (int i=0; i<cell.length;i++) {
			for (int x=0; x<cell[0].length;x++) {
				for (int y=0; y<cell[0][0].length;y++) {
					//false is dead cell; true is alive cell
					cell[0][x][y]=false;
				}
			}
		}
		
		panel.addMouseListener(this);
		
		
		//south container
		south.setLayout(new GridLayout(1,3));
		south.add(step);
		south.add(start);
		south.add(stop);
		step.addActionListener(this);
		start.addActionListener(this);
		stop.addActionListener(this);
		

		frame.add(south, BorderLayout.SOUTH);
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		new GameOfLife();
	}



	



	public void mouseReleased(MouseEvent e) {
		
		//get mouse grid coordinates
		double width=(double)panel.getWidth()/cell[0].length;
		double height=(double)panel.getHeight()/cell[0][0].length;
		int x=Math.min(cell[0].length-1, (int)(e.getX()/width));
		int y=Math.min(cell[0][0].length-1, (int)(e.getY()/height));

		
		//if glider mode is on make make gliders, if not draw with a square brush
		if(gliderMode){
			spawnGlider(x, y, glider);
			glider=(glider+1)%4;
		}else{
		
		    for(int i=-brushSize+1; i<brushSize; i++){
			      for(int j=-brushSize+1; j<brushSize; j++){
			       //set the values of the cells surrounding the cell, and the cell itself
			      	if(MaxMin(0, cell[0].length-1,x+i) && MaxMin(0, cell[0][0].length-1,y+j))
			      		cell[0][x+i][y+j]=!cell[0][x+i][y+j];
		       }
		     }
	}


		frame.repaint();
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		//do only one loop
		if(e.getSource().equals(step)){
			recalculate();
		}
		
		//start the game loop
		if(e.getSource().equals(start)){
			if(!running) {
				running=true;
				Thread t =new Thread(this);
				t.start();		
			}
		}
		
		//stop the loop
		if(e.getSource().equals(stop))
			running=false;
	
		
		
	}
	
	public void run() {
		while(running) {
			recalculate();
			
			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	//from my personal project:
	//https://editor.p5js.org/PROGRESS478/sketches/kak9oLww9
	
	//calculates the next state of every cell and applies it
	void recalculate() {
		
		for(int x=0; x<cell[0].length; x++){
		    for(int y=0; y<cell[0][0].length; y++){
		    	
		    	//calculate color
				cellColor[x][y]+=cell[0][x][y]? 360:0;
				cellColor[x][y]=(int)Math.min(360,cellColor[x][y]*fadeOutEffect);
		    	
		      int surrounding=0;
		      for(int i=-1; i<2; i++){
		        for(int j=-1; j<2; j++){
		        	//get the values of the cells surrounding the cell, and the cell itself
		        	if(MaxMin(0, cell[0].length-1,x+i) && MaxMin(0, cell[0][0].length-1,y+j)) 
		        		surrounding+=cell[0][x+i][y+j]? 1:0;
		        }
		      }
		      //remove the value of the cell itself
		      surrounding-=cell[0][x][y]? 1:0;

		      if(surrounding<2)
		    	  cell[1][x][y]=false;
		      if(surrounding>3)
		    	  cell[1][x][y]=false;
		      if(surrounding==3)
		    	  cell[1][x][y]=true;
		      if(surrounding==2)
		    	  cell[1][x][y]=cell[0][x][y];

		    }
		  }
		
		//apply the new cell values
		for(int x=0; x<cell[0].length; x++){
		    for(int y=0; y<cell[0][0].length; y++){
		    	cell[0][x][y]=cell[1][x][y];
		    }
		}
		
	
		frame.repaint();

		
		
		
		
	}
	
	
	

	
	//returns true if the value is between the min and max
	public boolean MaxMin(int min, int max, int n) {
		return n<=max && n>=min;
	} 
	
	public void spawnGlider(int x, int y, int rot) {
		
		switch(rot) {
		case 0:
			cell[0][x+1][y+1]=true;
			cell[0][x][y+1]=true;
			cell[0][x-1][y+1]=true;
			cell[0][x-1][y]=true;
			cell[0][x][y-1]=true;
			break;
		case 1:
			cell[0][x+1][y+1]=true;
			cell[0][x][y+1]=true;
			cell[0][x-1][y+1]=true;
			cell[0][x+1][y]=true;
			cell[0][x][y-1]=true;
			break;
		case 2:
			cell[0][x+1][y-1]=true;
			cell[0][x][y-1]=true;
			cell[0][x-1][y-1]=true;
			cell[0][x-1][y]=true;
			cell[0][x][y+1]=true;
			break;
		case 3:
			cell[0][x+1][y-1]=true;
			cell[0][x][y-1]=true;
			cell[0][x-1][y-1]=true;
			cell[0][x+1][y]=true;
			cell[0][x][y+1]=true;	
		}

		
	}
	
	
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}









}
