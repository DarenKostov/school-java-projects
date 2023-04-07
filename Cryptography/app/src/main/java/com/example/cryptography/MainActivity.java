/*
    Daren Kostov
    this is an app that encrypts messages


    HOW TO USE:
        click either Caesar, Scytale, or Vigenere to change the cipher
        the selected cipher will be grayed out

        click encrypt or decrypt to encrypt or decrypt the text you have inputted in the 1st text box
        the second text box will also tell you what it expects

        and finnaly bellow the decrypt button your output will be shown
         



    sources used:
    https://www.tutorialspoint.com/how-to-change-the-font-size-of-textview-in-android
    https://stackoverflow.com/questions/4622517/hide-a-edittext-make-it-visible-by-clicking-a-menu
    https://stackoverflow.com/questions/7200108/android-gettext-from-edittext-field
    https://stackoverflow.com/questions/5385024/mod-in-java-produces-negative-numbers
    
*/

package com.example.cryptography;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class MainActivity extends AppCompatActivity implements OnClickListener{

    Button caesar;
    Button vigenere;
    Button scytale;
    Button encrypt;
    Button decrypt;
    
    TextView output;

    EditText input;
    EditText input2;

    char mode;

    Pattern lettersOnly=Pattern.compile("[A-Z]+");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caesar=(Button)this.findViewById(R.id.caesar);
        vigenere=(Button)this.findViewById(R.id.vigenere);
        scytale=(Button)this.findViewById(R.id.scytale);
        encrypt=(Button)this.findViewById(R.id.encrypt);
        decrypt=(Button)this.findViewById(R.id.decrypt);

        output=(TextView)this.findViewById(R.id.output);
        
        input=(EditText)this.findViewById(R.id.input);
        input2=(EditText)this.findViewById(R.id.second_input);

        caesar.setOnClickListener(this);
        vigenere.setOnClickListener(this);
        scytale.setOnClickListener(this);
        encrypt.setOnClickListener(this);
        decrypt.setOnClickListener(this);

        caesar.setEnabled(false);
        input2.setHint("offset (number)");
        mode='c';

    }

    @Override
    public void onClick(View v){

        //==mode switch
        if(v.equals(caesar)){
            caesar.setEnabled(false);
            vigenere.setEnabled(true);
            scytale.setEnabled(true);
            mode='c';
            input2.setHint("offset (number)");
        }else if(v.equals(vigenere)){
            caesar.setEnabled(true);
            vigenere.setEnabled(false);
            scytale.setEnabled(true);
            mode='v';
            input2.setHint("key (string)");
        }else if(v.equals(scytale)){
            caesar.setEnabled(true);
            vigenere.setEnabled(true);
            scytale.setEnabled(false);
            mode='s';
            input2.setHint("rows (number)");

        //==encrypt
        }else if(v.equals(encrypt)){
            String in=formatTheInput();
            
            switch(mode){
                case 'c':
                    try {
                        output.setText(encryptCaesar(in, Integer.parseInt(input2.getText().toString()))); 
					}catch(NumberFormatException e) {
                        Toast.makeText(this, "Invalid offset, must be an integer.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                case 'v':
                    output.setText(encryptVigenere(in, formatTheInput2())); 
                    break;
                case 's':
                    try {
                        output.setText(encryptScytale(in, Integer.parseInt(input2.getText().toString()))); 
					}catch(NumberFormatException e) {
                        Toast.makeText(this, "Invalid rows, must be an integer.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
            
            }
        //==decrypt
        }else if(v.equals(decrypt)){
            String in=formatTheInput();
            
            switch(mode){
                case 'c':
                    try {
                        output.setText(encryptCaesar(in, 26-Integer.parseInt(input2.getText().toString()))); 
					}catch(NumberFormatException e) {
                        Toast.makeText(this, "Invalid offset, must be an integer.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                case 'v':
                    output.setText(dencryptVigenere(in, formatTheInput2())); 
                    break;
                case 's':
                    try {
                        output.setText(encryptScytale(in, Integer.parseInt(input2.getText().toString()))); 
					}catch(NumberFormatException e) {
                        Toast.makeText(this, "Invalid rows, must be an integer.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
            
            }
        }
    }

    String formatTheInput(){
        //make all upper case and if there is no input default it to an empty String
        Matcher matcher = lettersOnly.matcher(input.getText()==null? "" : input.getText().toString().toUpperCase());
        String out="";

        while(matcher.find())
            out+=matcher.group();

        return out;
    }
    
    String formatTheInput2(){
        //make all upper case and if there is no input default it to an empty String
        Matcher matcher = lettersOnly.matcher(input2.getText()==null? "" : input2.getText().toString().toUpperCase());
        String out="";

        while(matcher.find())
            out+=matcher.group();

        return out;
    }

    String encryptCaesar(String in, int shift){
        String out="";
        
        for(int i=0; i<in.length(); i++){

            //shift and make sure not to go over Z
            int j=in.charAt(i)-'A';
            j+=shift;
            j=Math.floorMod(j, 26);//makes it work with negatives
            out+=(char)('A'+j);
            
        }
        return out;
    
    }

    String encryptVigenere(String in, String key){
        String out="";
        
        for(int i=0; i<in.length(); i++){
            int j=in.charAt(i)-'A';
        
            //k is key segment => (k)ey (its a coincidence that k is after j)
            int k=key.charAt(i%key.length())-'A';

            //shift and make sure not to go over Z
            j+=k;
            j%=26;
            j=Math.floorMod(j, 26);//makes it work with negatives
            out+=(char)('A'+j);
        }
        return out;
    }
    
    String dencryptVigenere(String in, String key){
        String out="";
        
        for(int i=0; i<in.length(); i++){
            int j=in.charAt(i)-'A';

            //k is key segment => (k)ey
            int k=key.charAt(i%key.length())-'A';

            //shift and make sure not to go over Z
            j-=k;
            j=Math.floorMod(j, 26);//makes it work with negatives
            out+=(char)('A'+j);
        }
        return out;
    }

    String encryptScytale(String in, int rows){

        
        //rows=0 will cause division by 0
        if(rows==0)
            Toast.makeText(this, "Invalid rows, must be an integer that is not 0", Toast.LENGTH_SHORT).show();
        
        String out="";

        int cols=(int)Math.ceil(1.0*in.length()/rows);

        
        char[][] array=new char[cols][rows];

        for(int i=0; i<cols; i++)
            for(int j=0; j<rows; j++)
                array[i][j]='!';
        


        
        /*
            cols can be 0 but it will never devide
            because the in.length will have to be also 0
        */
        for(int i=0; i<in.length(); i++){
            array[i%cols][i/cols]=in.charAt(i);        
        }

        for(char[] row : array){
            for(char single : row)
                if(single!='!')
                    out+=single;
            
            // out+='\n';    
        }
    return out;
    }

    
}