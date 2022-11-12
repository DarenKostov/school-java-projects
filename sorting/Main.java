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
		
		Scanner scanner = new Scanner(System.in);
		
		filePath="./data-1k.txt";
		
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
		
		
		long time1=0, time2=0;
		
		
		switch(Integer.parseInt(scanner.nextLine())){
			case 1:
				time1=System.currentTimeMillis();
				Sort.Bubble(input);
				time2=System.currentTimeMillis();
				break;
			case 2:	
				time1=System.currentTimeMillis();
				Sort.Selection(input);
				time2=System.currentTimeMillis();
				break;
			case 3:	
				time1=System.currentTimeMillis();
				Sort.Table(input);
				time2=System.currentTimeMillis();
				break;
			case 4:	
				time1=System.currentTimeMillis();
				input=Sort.Quicksort(input);
				time2=System.currentTimeMillis();
				break;
				
		}

		for(int i=0; i<input.length; i++){
			System.out.println(input[i]);
		}
		System.out.println("time: "+(time2-time1));
		
		
	}

	public static void main(String[] args) {
		new Main();
	}

}
