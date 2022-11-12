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
		
		System.out.println("Welcome to Sorting Algorithms speed testing.");
		
		System.out.println("First input a file with all the data that is to be sorted.");
		
		System.out.print("\"data-50.txt\", \"data-100.txt\", \"data-1k.txt\", \"data-10k\", \"data-100k.txt\", and \"data-1mil.txt\", ");
		System.out.println("should be initially in the original repository of this project.");
		
		System.out.print("Input file path: ");
		
		
		filePath="./data-50.txt";

		filePath=scanner.nextLine();
		
		try{
			file= new Scanner(new File(filePath));
		}catch(FileNotFoundException e) {
			System.out.println("Where file?");
			System.exit(0);
			
		}
		
		//split the data
		dataSet= file.nextLine().split(", ");
		
		//check if the entries are seperated correctly
		if(dataSet.length<=1){
			System.out.println("Where commas and/or spaces?");
			System.out.println("Each entry in the file should be separated by a comma and a space \", \"");
			System.out.println("Example:");
			System.out.println("3, 71, 6, 2, 876, 1, 4, 7, 43");
			System.out.println("Note that this error can be caused due to the file containg only 1 entry, in that case you should wonder where sorting only 1 number is applicable.");
			System.exit(0);
		}
	
	
	
	
		//get the data from String to int array
		input= new int[dataSet.length];
		for(int i=0; i<input.length; i++){
			try {
				input[i]=Integer.parseInt(dataSet[i]);
			}catch(NumberFormatException e) {
			System.out.println("Invalid file format: file should contain only numbers (and commas)");
			
			//print out which entry is invalid
			System.out.print("Error at "+i);
			switch(i){
				case 1:
					System.out.print("st");
					break;
				case 2:
					System.out.print("nd");
					break;
				case 3:
					System.out.print("rd");
					break;
				default:
					System.out.print("th");
					break;
			}
			System.out.println(" entry:");
			
			
			
			//print out where the entry is
			//print entrues around the failed entry
			System.out.println(dataSet[i-1]+", "+dataSet[i]+", "+dataSet[i+1]);
			
			//print to where the entry is
			for(int j=0; j<dataSet[i-1].length(); j++)
				System.out.print(" ");
			System.out.print("  ");
				
			//underline the entry with arrows ("^")
			for(int j=0; j<dataSet[i].length(); j++)
				System.out.print("^");
				
			System.out.println();
			System.exit(0);			
			}
		}
		
		
		long time1=0, time2=0;
		
		System.out.println("Next, input a soritng algorithm.");
		System.out.println("Sorting algorithms:\n1: Bubble\n2: Selection\n3: Table\n4: Quicksort");
		System.out.print("Input a sorting algorithm: ");
		
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
			default:
				System.out.println("Input a number between 1 and 4 (inclusive) next time.");
				System.exit(0);
		}
		System.out.println("Sample of 50 numbers:");
		for(int i=0; i<50; i++){
			System.out.print(input[i*input.length/50]+", ");
		}

		System.out.println("\ntime: "+(time2-time1));
		
		
	}

	public static void main(String[] args) {
		new Main();
	}

}
