/*
	Daren Kotsov
	Sorting
	11/04/2022
	
	
 */




import java.util.Scanner;

public class Main {
		
		
	private Main(){
		int[] input={1,2,4,8,2,6,1,8,90,2,5,8,1,5,9,3,6,2,6,90,1,5,4,1,4,8,9,8,6,5,3,21,
					34,67,9,9,765,43,21,234,8,2,4,7,1,2,6,1,7,3,87,12,87,1,32,45,6,
					7860,436,314,8,291,192,91,46,27,28,2,3,1,554,54,45,45,56,677,6,54,3,
					1212,23,4,56,10,567,5,2,4,6,8,3,1,3,2,7,3,5,63,3,67,3,1,5,7,8,9,53};

		double time=System.currentTimeMillis();

		Sort.Bubble(input);
		
		System.out.println(System.currentTimeMillis()-time);
		
		
	}

	public static void main(String[] args) {
		new Main();
	}

}
