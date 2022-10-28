import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Binary_Translator {
	public Binary_Translator() {
		
		//System.out.println(DecToBin(99));
		System.out.println("Welcome to the Binary Translator!");
		Scanner scanner = new Scanner(System.in);

		System.out.println("type \"F\" (file) or \"C\" (console) to choose your method of input.");
		
		
		
		
		int number_input=0;

		fileOrConsole:
		while(true) {
			String input_method = scanner.nextLine();
			
			switch(input_method) {
			//for file
			case "f":
			case "file":
			case "FILE":
			case "File":
			case "F":
				System.out.println("File is chosen. Please input the file path.");
				while(true) {
					try {
						String file_path=scanner.nextLine();
						Scanner filescanner = new Scanner(new File(file_path));
						
						try {
							number_input = Integer.parseInt(filescanner.nextLine());
						}catch(NumberFormatException e) {
							System.out.println("File must contain only a number. (no letters and/or symbols)");
							continue;
						}
						
						
					}catch(IOException ex){
						System.out.println("File not found, please try again");
						continue;
					}
					
					
					
					break;
				}
				System.out.println(number_input);
				break fileOrConsole;

			//for console
			case "c":
			case "console":
			case "CONSOLE":
			case "Console":
			case "C":
				System.out.println("Console is chosen. Please type the number you wish to translate.");
				
				while(true) {
						
						try {
							number_input = Integer.parseInt(scanner.nextLine());
						}catch(NumberFormatException e) {
							System.out.println("Type a number. (no letters and/or symbols)");
							continue;
						}
						break;
				}
				
				break fileOrConsole;
			
			default:
				System.out.println("Try again!");
			}
		}
		
	
		 
		
		if(isntBinary(number_input)) {
			System.out.println(DecToBin(number_input));
			System.exit(1);
		}
			
		
			boolean isBinary;

		System.out.println("Translate to Binary (B) or to Decimal (D)?");
		while(true) {
				switch(scanner.nextLine()) {
				case "B":
				case "BIN":
				case "bin":
				case "Bin":
				case "b":
				case "BINARY":
				case "Binary":
				case "binary":

				
				
				
				
				
				
				}
		
		
		
		}
		
		
		
		
		
		
		
		
		/*
		int input=-1;
		while(input!=-1) {
			try {
				input = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException e) {
				System.out.println("Type a number. (no letters and/or symbols)");
				continue;
			}
		}
		*/
		scanner.close();
		
		
	}
	
	//https://javarevisited.blogspot.com/2014/03/how-to-check-if-number-is-binary-in-java.html
    public static boolean isntBinary(int number) {
        int copyOfInput = number;

        while (copyOfInput != 0) {
            if (copyOfInput % 10 > 1) {
                return true;
            }
            copyOfInput = copyOfInput / 10;
        }
        return false;
    }
    
    
    //https://www.rapidtables.com/convert/number/how-binary-to-decimal.html
    //https://stackoverflow.com/questions/3389264/how-to-get-the-separate-digits-of-an-int-number
    public static int BinToDec(int number) {
    	int number2=0;
    	int index=0;
    	while (number > 0) {
    	    number2+= Math.pow(2, index)*(number % 10);
    	    number = number / 10;
    	    index+=1;
    	}
    	return number2;
    }
    //reverse of BinToDec()
    public static int DecToBin(int number) {
    	int number2=0;
    	int index=0;
    	while (number > 0) {
    	    number2+= Math.pow(10, index)*(number % 2);
    	    number = number / 2;
    	    index+=1;
    	}
    	return number2;
    }

	
	public static void main(String[] args) {
		new Binary_Translator();

	}

}
