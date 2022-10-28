/*
	Daren Kotsov
	Cli TicTacToe
	9/28/2022
 */




import java.util.Scanner;


public class CliTicTacToe {

	char[][] board= new char[3][3];
	char turn='X';
	//0 is X; 1 is O
	int[] wins= {0, 0};
	//all row and column values
	final char[] COL= {'1', '2', '3'};
	final char[] ROW= {'a', 'b', 'c'};
	
	public CliTicTacToe() {
		
		System.out.println("Welcome to Cli TicTacToe!");
		System.out.println("Type the column followed by the row, leaving no punctuation or spaces. ex: "+randomExample());
		
		Scanner scanner = new Scanner(System.in);
		
		//infinite loop
		while(true) {
			
			//clear the board
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					board[i][j]=' ';
				}
			}
			
			
			boolean tie=true;
			

			//the previous winner is the one who starts the game
			System.out.println("It's "+turn+" turn");
	
			PrintBoard();
			
			while(true) {
				String input = scanner.nextLine();
				
				
				//for debugging or testing
				//instant O win
				if(input.charAt(0)=='O') {
					turn='O';
					break;
				}
				//instant X win
				if(input.charAt(0)=='X') {
					turn='X';
					break;
				}	
				
				
				//check if input is exactly 2 characters long
				if(input.length()!=2) {
					System.out.println("Type the column and the roll, without any spaces or punctuation, ex: "+randomExample());
					continue;
				}
				//check if first character is between 1 and 3
				switch(input.charAt(0)) {
					case '1':
					case '2':
					case '3':
						break;
					default:
						System.out.println("The column must be 1, 2, or 3. ex: "+randomExample());
						continue;
				}
				
				//check if second character is between a and b
				switch(input.charAt(1)) {
				case 'a':
				case 'b':
				case 'c':
					break;
				default:
					System.out.println("The roll must be a, b, or c. ex: "+randomExample());
					continue;
				}
					
				int x=input.charAt(0)-'1';
				int y=input.charAt(1)-'a';
				
				//check if the chosen space is occupied
				if(board[x][y]==' ')
					board[x][y]=turn;
				else {
					System.out.println("this space is occupied");
					continue;
				}
				
				PrintBoard();
				
				//if its a winning move or a tie, break out of the loop
				if(winning())
					break;
				tie=true;
				tieLoop:
				for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						if(board[i][j]==' ') {
							tie=false;
							break tieLoop;
						}
					}
				}
				if(tie)
					break;
				
				
				
				
				//flip turns
				turn = turn == 'O' ? 'X' : 'O';
				}
			
			if(tie)
				System.out.println("Nobody won :(");
			else
				System.out.println(turn+" won!!!");
			
			
			//according to https://stackoverflow.com/a/16868321 it's not such an inefficient method
			wins[turn == 'O' ? 1 : 0]++;
			
			
			System.out.println("X wins: "+wins[0]+"; O wins: "+wins[1]);
			System.out.println("Play again? [Y/n]");
	
			//if the user doesn't say any variation of yes stop the function, otherwise run it again
			switch(scanner.nextLine()) {
				case "":
				case "y":
				case "yes":
				case "Y":
				case "Yes":
				case "YES":
					break;
				default:
					scanner.close();
					return;
			}
		}
	}
	
	//check if the current player has won
	public boolean winning() {
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
	
	//print the board
	public void PrintBoard() {
		System.out.println("  1 2 3");
		System.out.println("a "+board[0][0]+"|"+board[1][0]+"|"+board[2][0]);
		System.out.println("  -----");
		System.out.println("b "+board[0][1]+"|"+board[1][1]+"|"+board[2][1]);
		System.out.println("  -----");
		System.out.println("c "+board[0][2]+"|"+board[1][2]+"|"+board[2][2]);
	}
	
	//generates random example
	public String randomExample() {
		return Character.toString(COL[(int)(Math.random()*3)])+Character.toString(ROW[(int)(Math.random()*3)]);
	}
	
	
	public static void main(String[] args) {
		new CliTicTacToe();

	}

}
