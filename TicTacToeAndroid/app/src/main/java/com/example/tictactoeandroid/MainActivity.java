package com.example.tictactoeandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


//why is the default an error and not a warning?
@SuppressWarnings("SuspiciousIndentation")

public class MainActivity extends AppCompatActivity implements OnClickListener{


    Button[][] button=new Button[3][3];
    char[][] board=new char[3][3];
    char turn='O';


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button[0][0]= (Button)this.findViewById(R.id.button1);
        button[0][1]= (Button)this.findViewById(R.id.button2);
        button[0][2]= (Button)this.findViewById(R.id.button3);
        button[1][0]= (Button)this.findViewById(R.id.button4);
        button[1][1]= (Button)this.findViewById(R.id.button5);
        button[1][2]= (Button)this.findViewById(R.id.button6);
        button[2][0]= (Button)this.findViewById(R.id.button7);
        button[2][1]= (Button)this.findViewById(R.id.button8);
        button[2][2]= (Button)this.findViewById(R.id.button9);
        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){     
                board[x][y]=' ';
                button[x][y].setOnClickListener(this);
            }
        }

        AIMove();
        turn='X';
                
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //check who won and anounces the winner if anyoone has won
    public void checkWhoWon(){
        if(winning()){
            Toast.makeText(this, turn+" has won!", Toast.LENGTH_SHORT).show();
            resetBoard();
            turn='O';
            AIMove();
            turn='X';
        }else if(full()){
            Toast.makeText(this, "Nobody has won!", Toast.LENGTH_SHORT).show();
            resetBoard();
            turn='O';
            AIMove();
            turn='X';
        }
    }


    @Override
    public void onClick(View v){
        Button b=(Button)v;

        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){
                if(b.equals(button[x][y])){
                    //only X is playing as a human, so no need to check for O (AI) 
                    if(board[x][y]==' ' && turn=='X'){
                        b.setEnabled(false);
                        b.setText("X");
                        board[x][y]='X';
                        checkWhoWon();
                                                
                        //ai's turn
                        turn='O';
                        AIMove();
                        checkWhoWon();

                        //it's out turn
                        turn='X';
                        
                    }
                }
                
            }
        }

    }

    //gives a rand int between 0 to 2
    public int genRand(){
        return (int)(Math.random()*3);
    }

    //performs the ai's move
    public void AIMove(){

        if(AIPlayBestMove('O'))
            return;
        if(AIPlayBestMove('X'))
            return;

        //play randomly
        // if(false)

        //we don't want this to run forever in the small chance that we never get a valid position
        int count=0;
        while(count++<100000){
            int x=genRand(), y=genRand();

            if(board[x][y]==' '){
                button[x][y].setText("O");
                board[x][y]='O';
                button[x][y].setEnabled(false);
                break;
            }else{
                //already occupied? try again!
                continue;//a useless continue
            }

        
        }        

        
    }

    //plays the ai's best move depending on its goal
    public boolean AIPlayBestMove(char seek){


        //seek this player
        seek=seek;//useless variable assignment
        
        //avoid this player
        char avoid= seek=='O'? 'X' : 'O';     

        //=all comments assume seek is 'O' and avoid is 'X'
        
        //check columns
        for(int x=0; x<3; x++){
        
            //where on the Y is the best move?, -1 means nowhere    
            int bestY=-1;
            
            for(int y=0; y<3; y++){

                int spot=board[x][y];

                if(spot==seek){
                    //an O spot! lets do nothing!
                }else if(spot==' '){ 
                    //A blank spot! lets remeber where this spot is so we can play there if the conditions are met
                    if(bestY==-1){
                        bestY=y;
                    }else{
                        //we already recorded a blank space? there must be 2 or 3 blank spaces then, meaning this isnt a winning column
                        bestY=-1;
                        break;
                    }
                }else if(spot==avoid){
                    //an X spot! we cant place there and win given the context of columns
                    bestY=-1;
                    break;
                }
            }

            //did we find a good blank space to play on?
            if(bestY!=-1){
                //we did? play there!
                button[x][bestY].setText("O");
                board[x][bestY]='O';
                button[x][bestY].setEnabled(false);
                return true;
            }else{
                //no? try the next column/rows
                continue;//a useless continue
            }        
        }    


        //check columns
        for(int y=0; y<3; y++){
        
            //where on the X is the best move?, -1 means nowhere    
            int bestX=-1;
            
            for(int x=0; x<3; x++){

                int spot=board[x][y];

                if(spot==seek){
                    //an O spot! lets do nothing!
                }else if(spot==' '){ 
                    //A blank spot! lets remeber where this spot is so we can play there if the conditions are met
                    if(bestX==-1){
                        bestX=x;
                    }else{
                        //we already recorded a blank space? there must be 2 or 3 blank spaces then, meaning this isnt a winning row
                        bestX=-1;
                        break;
                    }
                }else if(spot==avoid){
                    //an X spot! we cant place there and win given the context of rows
                    bestX=-1;
                    break;
                }
            }

            //did we find a good blank space to play on?
            if(bestX!=-1){
                //we did? play there!
                button[bestX][y].setText("O");
                board[bestX][y]='O';
                button[bestX][y].setEnabled(false);
                return true;
            }else{
                //no? try the next row/diagonals
                continue;//a useless continue
            }        
        }   
         

        //check diagonals
        for(int d=1; d>-2; d-=2){

            //get d in a 0-1 rangle
            int d1=(1-d)/2;
            
        
            //where on the X and Y is the best move?, -1 means nowhere    
            int bestXY=-1;

            for(int xy=0; xy<3; xy++){

                //when d is 1 y will go from 0 to 2, when d is -1 y will go from 2 to 0
                
                int spot=board[xy][d1*2+(xy*d)];

                if(spot==seek){
                    //an O spot! lets do nothing!
                }else if(spot==' '){ 
                    //A blank spot! lets remeber where this spot is so we can play there if the conditions are met
                    if(bestXY==-1){
                        bestXY=xy;
                    }else{
                        //we already recorded a blank space? there must be 2 or 3 blank spaces then, meaning this isnt a winning diagonal
                        bestXY=-1;
                        break;
                    }
                }else if(spot==avoid){
                    //an X spot! we cant place there and win given the context of diagonal
                    bestXY=-1;
                    break;
                }

            }
                
            //did we find a good blank space to play on?
            if(bestXY!=-1){
                //we did? play there!
                button[bestXY][d1*2+(bestXY*d)].setText("O");
                board[bestXY][d1*2+(bestXY*d)]='O';
                button[bestXY][d1*2+(bestXY*d)].setEnabled(false);
                return true;
            }else{
                //no? try the next diagonal or stop checking
                continue;//a useless continue
            }        
        
        }

        //we didn't get tpo play a move
        return false;

        
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

    //check if all spaces have been filled, the board is full
    public boolean full(){
        for(int x=0; x<3; x++)
            for(int y=0; y<3; y++)
                if(board[x][y]==' ')
                    return false;  
        return true;  
    }

    //resets the board
    public void resetBoard(){
        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){     
                board[x][y]=' ';
                button[x][y].setText(" ");
                button[x][y].setEnabled(true);
            }
        }
    
    }


}