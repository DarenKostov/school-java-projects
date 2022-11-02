/*
	Daren Kotsov
	GUI TicTacToe
	9/28/2022
 */






import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GuiTicTacToe implements ActionListener {
	
	//start of GUI
	
	JFrame frame = new JFrame();
	JButton[][] button = new JButton[3][3];
	Container center = new Container();
	
	//label that tells the score of each payer
	JLabel xLabel= new JLabel("X wins:0");
	JLabel oLabel= new JLabel("O wins:0");
	
	//buttons that apply the changes to the player names
	JButton xChangeName= new JButton("Change X's name");
	JButton oChangeName= new JButton("Change O's name");
	
	//input of player names
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	
	
	//names of players
	String XplayerName="X";
	String OplayerName="O";
	Container north = new Container();

	//end of GUI
	
	
	
	int xwins=0;
	int owins=0;
	
	
	
	
	String turn="X";

	
	
	
	
	
	public GuiTicTacToe() {
		
		frame.setSize(444,444);
		frame.setResizable(false);
		//center grid container 
		frame.setLayout(new BorderLayout());
		center.setLayout(new GridLayout(3, 3));
		
		
		//add the buttons to the center container and actionListener
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				button[i][j]= new JButton(" ");
				center.add(button[i][j]);
				button[i][j].addActionListener(this);
			}
		}
		
		frame.add(center, BorderLayout.CENTER);
		
		
		//north container 
		north.setLayout(new GridLayout(3, 2));
		north.add(xLabel);
		north.add(oLabel);
		north.add(xChangeName);
		north.add(oChangeName);
		north.add(xChangeField);
		north.add(oChangeField);
		frame.add(north, BorderLayout.NORTH);
		
		
		//add the change name buttons to the actionListener
		xChangeName.addActionListener(this);
		oChangeName.addActionListener(this);


		
		

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new GuiTicTacToe();
	}

	
	//checks if the current player has won
	public boolean winning(String[][] board) {
		if(board[0][0]==turn && board[1][0]==turn && board[2][0]==turn)
			return true;
		if(board[0][1]==turn && board[1][1]==turn && board[2][1]==turn)
			return true;
		if(board[0][2]==turn && board[1][2]==turn && board[2][2]==turn)
			return true;
		if(board[0][0]==turn && board[0][1]==turn && board[0][2]==turn)
			return true;
		if(board[1][0]==turn && board[1][1]==turn && board[1][2]==turn)
			return true;
		if(board[2][0]==turn && board[2][1]==turn && board[2][2]==turn)
			return true;
		if(board[0][0]==turn && board[1][1]==turn && board[2][2]==turn)
			return true;
		if(board[2][0]==turn && board[1][1]==turn && board[0][2]==turn)
			return true;
		return false;
	}
	//checks if there is a tie
	public boolean tie(String[][] board) {
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(board[i][j]==" ")
					return false;
				
			}
		}
		return true;
	}
	
	//resets the board
	public void resetBoard() {
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				button[i][j].setText(" ");
			}
		}
		//reset the turn as well
		turn="X";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton current;
		boolean gridButton=false;
		
		//temp copy of the board so we don't do current.getText() a large number of times but instead 10 times
		String[][] board = new String[3][3];
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(e.getSource().equals(button[i][j])) {
					current=button[i][j];
					if(current.getText()==" ")
					current.setText(turn);
				}
				//after the move is played so we have the updated board
				board[i][j]=button[i][j].getText();
				
			}
		}
		
		
		
		//changes the names of the players if the text boxes are not empty
		if(!gridButton) {
			if(e.getSource().equals(xChangeName) && !xChangeField.getText().isEmpty()) {
				System.out.println("xChangeField: "+xChangeField.getText());
				XplayerName=xChangeField.getText();
				xLabel.setText(XplayerName+" wins: "+ xwins);
				return;
			}else if(e.getSource().equals(oChangeName) && !oChangeField.getText().isEmpty()) {
				OplayerName=oChangeField.getText();
				oLabel.setText(OplayerName+" wins: "+ owins);
				return;
			}
			
			
			
		}
		
		//when someone wins, add in their score and reset the board
		if (winning(board)) {
			if(turn=="O") {
				owins++;
				oLabel.setText(OplayerName+" wins: "+ owins);
				resetBoard();
				return;
			}else if(turn=="X") {
				xwins++;
				xLabel.setText(XplayerName+" wins: "+ xwins);
				resetBoard();
				return;
			}
		}
		
		//when at a tie reset the board
		if(tie(board)) {
			resetBoard();
			return;
		}
		
		//flip turns
		turn = turn == "O" ? "X" : "O";

		
		
		
		
		
		
	}

	

	
	
}





