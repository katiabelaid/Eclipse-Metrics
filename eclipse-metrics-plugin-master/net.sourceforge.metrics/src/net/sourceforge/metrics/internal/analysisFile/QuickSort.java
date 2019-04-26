package net.sourceforge.metrics.internal.analysisFile;

import java.util.ArrayList;

public class QuickSort {
 
	public static void quickSort(ArrayList<Double> arr, int begin, int end) {
	    if (begin < end) {
	        int partitionIndex = partition(arr, begin, end);
	 
	        quickSort(arr, begin, partitionIndex-1);
	        quickSort(arr, partitionIndex+1, end);
	    }
	}
	private static int partition(ArrayList<Double> arr, int begin, int end) {
	    
		Double pivot = arr.get(end);
	    int i = (begin-1);
	 
	    for (int j = begin; j < end; j++) {
	        if (arr.get(j) <= pivot) {
	            i++;
	 
	            Double swapTemp = arr.get(i);
	            arr.set(i,arr.get(j));
	            arr.set(j,swapTemp);
	        }
	    }
	 
	    Double swapTemp = arr.get(i+1);
	    arr.set((i+1),arr.get(end));
	    arr.set((end) , swapTemp);
	 
	    return i+1;
	}
}