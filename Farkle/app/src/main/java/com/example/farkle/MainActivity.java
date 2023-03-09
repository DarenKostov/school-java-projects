/*
    Daren Kostov
    Farkle- a game with 6 dice where you try to get the most points without forfeiting your score

    Last time edited 3/9/2023
    
*/


package com.example.farkle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    //=number of dies
        
    //L=locked die, H=hot die, S=score die
    char[] buttonState=new char[6];    
    ImageButton[] button=new ImageButton[buttonState.length];

    //=number of die states

    int[] dieImage=new int[6];
    int[] dieValue=new int[6];
    
    Button roll;
    Button score;
    Button stop;
    
    TextView currentScoreTV;
    TextView totalScoreTV;
    TextView currentRoundTV;

    int currentScore, totalScore, currentRound;

        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button[0]= (ImageButton)this.findViewById(R.id.imageButton1);
        button[1]= (ImageButton)this.findViewById(R.id.imageButton2);
        button[2]= (ImageButton)this.findViewById(R.id.imageButton3);
        button[3]= (ImageButton)this.findViewById(R.id.imageButton4);
        button[4]= (ImageButton)this.findViewById(R.id.imageButton5);
        button[5]= (ImageButton)this.findViewById(R.id.imageButton6);

        for(int i=0; i<button.length; i++){
            button[i].setOnClickListener(this);
            button[i].setEnabled(false);
            buttonState[i]='H';
            button[i].setBackgroundColor(Color.LTGRAY);
            
        }


        roll= (Button)this.findViewById(R.id.buttonRoll);
        score= (Button)this.findViewById(R.id.buttonScore);
        stop= (Button)this.findViewById(R.id.buttonStop);

        
        roll.setOnClickListener(this);
        score.setOnClickListener(this);
        stop.setOnClickListener(this);

        score.setEnabled(false);
        stop.setEnabled(false);

            
        currentScoreTV= (TextView)this.findViewById(R.id.textViewCurrentScore);
        totalScoreTV= (TextView)this.findViewById(R.id.textViewTotalScore);
        currentRoundTV= (TextView)this.findViewById(R.id.textViewCurrentRound);

        dieImage[0]=R.drawable.state1;
        dieImage[1]=R.drawable.state2;
        dieImage[2]=R.drawable.state3;
        dieImage[3]=R.drawable.state4;
        dieImage[4]=R.drawable.state5;
        dieImage[5]=R.drawable.state6;

        
    }


    @Override
    public void onClick(View v){
        
            
        if(v.equals(roll)){
            for(int i=0; i<button.length; i++){
                if(buttonState[i]=='H'){
                    int rand=(int)(Math.random()*6);
                    dieValue[i]=rand;
                    button[i].setImageResource(dieImage[rand]);
                    button[i].setEnabled(true);
                    roll.setEnabled(false);
                    stop.setEnabled(false);
                    score.setEnabled(true);
                }

            }
        }else if(v.equals(score)){

            boolean nothingSelected=true;
            //get the amount of times a dies is seen
            int[] valueCount=new int[6];
            for(int i=0; i<buttonState.length; i++){
                if(buttonState[i]=='S'){
                    valueCount[dieValue[i]]++;
                    nothingSelected=false;
                }
            }

            //nothing selected? perhaps teher are no valid die combinations
            if(nothingSelected){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    				alertDialogBuilder.setTitle("No score!");
    				alertDialogBuilder
    					.setMessage("Forfeit score and go to next round?")
    					.setCancelable(false)
    					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
    						public void onClick(DialogInterface dialog,int id) {

                                //reset the round
                                goToNextRound();            
    							dialog.dismiss();
    						}
    					  })
    					.setNegativeButton("No",new DialogInterface.OnClickListener() {
    						public void onClick(DialogInterface dialog,int id) {
    							dialog.cancel();
    						}
    					});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
                return;
            }












            //any invalid moves? stop the scoring and warn the player!
            if((valueCount[1]>0 && valueCount[1]<3) || (valueCount[2]>0 && valueCount[2]<3) || (valueCount[3]>0 && valueCount[3]<3) || (valueCount[5]>0 && valueCount[5]<3)){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder.setTitle("Invalid combination of dies selected!");
				alertDialogBuilder
					.setMessage("Please select a valid die combination?")
					.setCancelable(false)
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
    					}
					  });
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();    
                return;
            }

            


            //=calculate the score            

            //calculate 1's
            if(valueCount[0]<3)
                currentScore+=valueCount[0]*100;
            else
                currentScore+=(valueCount[0]-2)*1000;

            //calculate 2's
            if(valueCount[1]>=3)
                currentScore+=(valueCount[1]-2)*200;
                
            //calculate 3's
            if(valueCount[2]>=3)
                currentScore+=(valueCount[2]-2)*300;
            
            //calculate 4's
            if(valueCount[3]>=3)
                currentScore+=(valueCount[3]-2)*400;
                
            //calculate 5's
            if(valueCount[4]<3)
                currentScore+=valueCount[4]*50;
            else
                currentScore+=(valueCount[4]-2)*500;
                
            //calculate 6's
            if(valueCount[5]>=3)
                currentScore+=(valueCount[5]-2)*600;

            currentScoreTV.setText("Current Score: "+currentScore);

            int lockedCount=0;

            //lock the selected dies and count how many are locked
            for(int i=0; i<button.length; i++){
            button[i].setEnabled(false);
                if(buttonState[i]=='S'){
                    buttonState[i]='L';
                    button[i].setBackgroundColor(Color.BLUE);
                    lockedCount++;
                }else if(buttonState[i]=='L')
                    lockedCount++;
                    
            }

            if(lockedCount==6){
                for(int i=0; i<button.length; i++){
                    buttonState[i]='H';
                    button[i].setBackgroundColor(Color.LTGRAY);

                }
            }


            roll.setEnabled(true);
            stop.setEnabled(true);
            score.setEnabled(false);
            
        
        }else if(v.equals(stop)){
            totalScore+=currentScore;
            goToNextRound();            
        
        }else{
            for(int i=0; i<button.length; i++){
                if(v.equals(button[i])){
                    if(buttonState[i]=='H'){
                        button[i].setBackgroundColor(Color.RED);
                        buttonState[i]='S';
                    }else{
                        button[i].setBackgroundColor(Color.LTGRAY);
                        buttonState[i]='H';
                    }
                
                }

            }
        }

    
    }


    //gors to the necxt round, it does't add to the total score
    public void goToNextRound(){
        currentRound++;
        currentScore=0;
        totalScoreTV.setText("Total Score: "+totalScore);
        currentScoreTV.setText("Current Score: 0");
        currentRoundTV.setText("Current Round: "+currentRound);

        for(int i=0; i<button.length; i++){
            button[i].setEnabled(false);
            buttonState[i]='H';
            button[i].setBackgroundColor(Color.LTGRAY);
        }

        roll.setEnabled(true);
        score.setEnabled(false);
        stop.setEnabled(false);
    }



    // @Override
    // public boolean onCreateOptionsMenu(Menu menu){
    //     getMenuInflater().infate(R.menu.main, menu);
    //     return true;
    // }

    
}