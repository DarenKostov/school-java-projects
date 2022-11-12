/*
	Daren Kotsov
    
    class containgin several sorting algorithms
	functions return a new, sorted, array
		
			    
	11/12/2022
	
	resources used:
	https://www.geeksforgeeks.org/quick-sort/
	
 */





public class Sort{
    
    
    //bubble sort
    public static int[] Bubble(int[] in){
		
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
		
		return in;

	
    }
    
    //selection sort
    public static int[] Selection(int[] in){
    	//find next biggest number and flip it with the current one
		for(int i=0; i<in.length; i++){
			int forMoving=i;
			for(int j=i; j<in.length; j++){
				if(in[forMoving]>in[j])
					forMoving=j;
			}
			int temp= in[forMoving];
			in[forMoving]=in[i];
			in[i]=temp;
		}
		
		return in;
		
    }
    
    //table sort
    public static int[] Table(int[] in){
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
		}
		
		return in;
	
    }
    
    //quicksort sort
    public static int[] Quicksort(int[] in, int low, int high){
    	if(low>=high || low<0)
			return in;
			
			
		//PARTITIONING
		
		// value of the right most element (of this partition)
	    int pivot=in[high];  

		//index of smaller element
	    int indexL=low-1;
		
		
	    for (int i=low; i<high; i++){

			//if the given element is smaller than the pivot swap it with indexL (incremeting indexL before hand)
	        if (in[i]<pivot){
				int temp=in[i];
				in[i]=in[++indexL];
				in[indexL]=temp;
	        }
	    }
		//swap the right most value with indexL
		int temp=in[high];
		in[high]=in[++indexL];
		in[indexL]=temp;
		
	    //partitioning index 
		int indexP=indexL;
		
		
		//partition the input into 2, and repeat
		in=Quicksort(in, low, indexP-1);
		in=Quicksort(in, indexP+1, high);
		
		return in;
		
		
    }
	
    public static int[] Quicksort(int[] in){
		return Quicksort(in, 0, in.length-1);
	}

}