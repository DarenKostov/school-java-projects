/*
	Daren Kotsov
    
    class containgin several sorting algorithms
    none of the function return anything
    
	11/04/2022
	
	
 */





public class Sort{
    
    
    //bubble sort
    public static void Bubble(int[] in){
		
		int end=1;
		
		while(end>0){
			end=in.length-1;
			for(int i=0; i<in.length-1; i++){//loop through the data
			
				if(in[i]>in[i+1]){//flip the numbers
					int temp= in[i+1];
					in[i+1]=in[i];
					in[i]=temp;
				}else{
					end--;
					continue;
				}
			}
			
			
		}
		
			

	
    }
    
    //selection sort
    public static void Selection(int[] in){
    	//find next biggest number and flip it with the current one
		for(int i=0; i<in.length-1; i++){
			int forMoving=i;
			for(int j=i; j<in.length-1; j++){
				if(in[forMoving]<in[j])
					forMoving=j;
			}
			int temp= in[forMoving];
			in[forMoving]=in[i];
			in[i]=temp;
		}
		
		
		
    }
    
    //table sort
    public static void Table(int[] in){
    	//smallest number in the input is 0 and biggest is 9999
		int[] occurrence= new int[9999+1];
		
		//set the occurance of each unique number
		for(int i=0; i<in.length; i++)
			occurrence[in[i]]++;
		
		//put all numbers times their occurances in the array (they are in order since they are in order in the occurance array)
		int count=0;
		for(int i=0; i<occurrence.length; i++)
			for(int j=0; j<occurrence[i]; j++){
				in[count]=i;
				count++;
				System.out.println(i+j+", "+count);
		}
	
	
    }
    
    //quickshot sort
    public static void Quickshot(int[] in){
    
    }


}