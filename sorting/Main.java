/*
	Daren Kotsov
	Sorting
	11/04/2022
	
	
 */




import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
		
	
	String filePath;
	Scanner file;	
	String[] dataSet;
	int[] input;
	
	
	private Main(){
	
		filePath="./data.txt";
		
		try{
			file= new Scanner(new File(filePath));
		}catch(FileNotFoundException e) {
			System.out.println("Where file?");
			System.exit(0);
			
		}
		
		
		dataSet= file.nextLine().split(", ");
	
	
	
		input= new int[dataSet.length];
		
		for(int i=0; i<input.length; i++){
			input[i]=Integer.parseInt(dataSet[i]);
		}
		
		
		long time1, time2;
		
		time1=System.currentTimeMillis();

		Sort.Bubble(input);
		

		time2=System.currentTimeMillis();



		System.out.println(time2-time1+"; "+time1+", "+time2);
		
		
	}

	public static void main(String[] args) {
		new Main();
	}

}
