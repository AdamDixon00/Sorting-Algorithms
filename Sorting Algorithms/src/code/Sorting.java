package code;
/**
 * Author:        <Adam Dixon>
 **/
import java.util.ArrayList;
import java.util.Collections;

public final class Sorting {

	public static <E extends Comparable<E>> void bubblesort(ArrayList<E> myList) {
		int n = myList.size();
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (myList.get(j).compareTo(myList.get(j + 1)) > 0) {
					E temp = myList.get(j);
					myList.set(j, myList.get(j + 1));
					myList.set(j + 1, temp);
				}
			}
		}
	}

	public static <E extends Comparable<E>> void selectionsort(ArrayList<E> myList) {
		int n = myList.size();
		for(int i = 0; i < n - 1; i++){
			int min_Index = i;
			for(int j = i + 1; j < n; j++){
				if (myList.get(j).compareTo(myList.get(min_Index)) < 0){
					min_Index = j;
				}
			}
			if (min_Index != i){
				E temp = myList.get(i);
				myList.set(i, myList.get(min_Index));
				myList.set(min_Index, temp);
			}
		}

	}

	public static <E extends Comparable<E>> void insertionsort(ArrayList<E> myList) {
		int n = myList.size();
		for (int i = 1; i < n; i++){
			E temp = myList.get(i);
			int j = i - 1 ;
			while (j >= 0 && myList.get(j).compareTo(temp) > 0){
				myList.set(j+1 , myList.get(j));
				j--;
			}
			myList.set(j+1, temp);
		}
	}

	public static <E extends Comparable<E>> void mergesort(ArrayList<E> myList) {
		int n = myList.size();
		if (n < 2) {
			return;
		}
		int mid = n / 2;
		ArrayList<E> leftList = new ArrayList<>(myList.subList(0, mid));
		ArrayList<E> rightList = new ArrayList<>(myList.subList(mid, n));
		mergesort(leftList);
		mergesort(rightList);
		merge(myList, leftList, rightList);
	}

	private static <E extends Comparable<E>> void merge(ArrayList<E> myList, ArrayList<E> leftList, ArrayList<E> rightList) {
		int leftSize = leftList.size();
		int rightSize = rightList.size();
		int i = 0, j = 0, k = 0;
		while (i < leftSize && j < rightSize) {
			if (leftList.get(i).compareTo(rightList.get(j)) <= 0) {
				myList.set(k++, leftList.get(i++));
			} else {
				myList.set(k++, rightList.get(j++));
			}
		}
		while (i < leftSize) {
			myList.set(k++, leftList.get(i++));
		}
		while (j < rightSize) {
			myList.set(k++, rightList.get(j++));
		}
	}

	public static <E extends Comparable<E>> void quicksort(ArrayList<E> myList) {
		quicksort(myList, 0, myList.size() - 1);
	}
	
	private static <E extends Comparable<E>> void quicksort(ArrayList<E> myList, int low, int high) {
		if (low < high) {
			int pivotIndex = partition(myList, low, high);
			quicksort(myList, low, pivotIndex - 1);
			quicksort(myList, pivotIndex + 1, high);
		}
	}

	private static <E extends Comparable<E>> int partition(ArrayList<E> myList, int low, int high) {
		E pivot = myList.get(high);
		int i = low - 1;
		for (int j = low; j < high; j++) {
			if (myList.get(j).compareTo(pivot) <= 0) {
				i++;
				Collections.swap(myList, i, j);
			}
		}
		Collections.swap(myList, i + 1, high);
		return i + 1;
	}

	public static void countingsort(ArrayList<Integer> myList) {
		int k = Collections.max(myList) + 1;
		countingSort(myList, k);
	}

	private static void countingSort(ArrayList<Integer> myList, int k) {
		
		int[] count = new int[k+1];
		for (int i = 0; i < myList.size(); i++) {
			count[myList.get(i)]++;
		}
		for (int i = 1; i <= k; i++) {
			count[i] += count[i-1];
		}
		int[] result = new int[myList.size()];
		for (int i = myList.size() - 1; i >= 0; i--) {
			result[count[myList.get(i)] - 1] = myList.get(i);
			count[myList.get(i)]--;
		}
		for (int i = 0; i < myList.size(); i++) {
			myList.set(i, result[i]);
		}
	}
	

	public static void radixsort(ArrayList<Integer> myList) {
		int maxNumber = Collections.max(myList);
		for (int exp = 1; maxNumber/exp > 0; exp *= 10) {
			ArrayList<ArrayList<Integer>> buckets = new ArrayList<>();
			for (int i = 0; i <= 9; i++) {
				buckets.add(new ArrayList<Integer>());
			}
			for (int i = 0; i < myList.size(); i++) {
				int digit = (myList.get(i) / exp) % 10;
				buckets.get(digit).add(myList.get(i));
			}
			myList.clear();
			for (int i = 0; i <= 9; i++) {
				myList.addAll(buckets.get(i));
			}
		}
	}
}