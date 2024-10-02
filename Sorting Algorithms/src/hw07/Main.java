package hw07;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
public class Main {
	private static ArrayList<String> allColors = new ArrayList<>();
	private static final int N = 10;
	

	public static void main(String[] args) {
		Main.initAllColors();

		try {
			runSortTest(Sorting.class.getMethod("bubblesort", new Class[] {ArrayList.class}), false);
			runSortTest(Sorting.class.getMethod("selectionsort", new Class[] {ArrayList.class}), false);
			runSortTest(Sorting.class.getMethod("insertionsort", new Class[] {ArrayList.class}), false);
			runSortTest(Sorting.class.getMethod("mergesort", new Class[] {ArrayList.class}), false);
			runSortTest(Sorting.class.getMethod("quicksort", new Class[] {ArrayList.class}), false);
			runSortTest(Sorting.class.getMethod("countingsort", new Class[] {ArrayList.class}), true);
			runSortTest(Sorting.class.getMethod("radixsort", new Class[] {ArrayList.class}), true);
		} catch (NoSuchMethodException | SecurityException e) {

			System.out.println("ERROR: Something went wrong with the main method.");
			System.out.println("Talk to Keenan.");
		} catch (Throwable e) {

			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method to run a sort method with some test data. This method can choose which sort method to run
	 * based on the parameters given to the method.
	 */
	public static void runSortTest(Method sortMethodName, Boolean numbersOnly) throws Throwable {
		ArrayList<Integer> numbers = Main.getRandomNumberList(N);
		ArrayList<String> colors = Main.getRandomStringList(N);
		ArrayList<Integer> srcNumbers = new ArrayList<>(numbers);
		ArrayList<String> srcColors = new ArrayList<>(colors);
		
		
		System.out.println(sortMethodName.getName() + "() Test: ");
		System.out.println("\tBEFORE:\t" + numbers);
		if (!numbersOnly) {System.out.println("\tBEFORE:\t" + colors);}
		System.out.println();
		try {
			sortMethodName.invoke(Sorting.class, numbers);
			if (!numbersOnly) {sortMethodName.invoke(Sorting.class, colors);}
		} 
		catch (IllegalAccessException ex) {
			ex.printStackTrace();
			
			System.out.println("ERROR: Something went wrong calling the sorting method.");
			System.out.println("Perhaps you changed the method header by accident?");
			System.out.println("You might need to talk to Keenan.");
		}
		catch (InvocationTargetException e) {
			if(e.getCause() != null) {
				throw e.getCause();
			}
			else {
				System.out.println("ERROR: Something went wrong calling the sorting method.");
				System.out.println("Perhaps you changed the method header by accident?");
				System.out.println("You might need to talk to Keenan.");
			}
		}
		
		System.out.println("\tAFTER:\t" + numbers);
		if (!numbersOnly) {System.out.println("\tAFTER:\t" + colors);}
		System.out.println();
		System.out.println("\tNumbers Result Correct?: " + isCorrect(srcNumbers, numbers));
		if (!numbersOnly) {System.out.println("\tColors Result Correct?: " + isCorrect(srcColors, colors));}
		System.out.println("-----------------------------------------------------------------------\n");
	}
	
	/**
	 * Method to test whether or not the given ArrayList values are sorted.  This method is called by the 
	 * runSortTest() method.
	 */
	public static <E extends Comparable<E>>boolean isCorrect(ArrayList<E> sourceList, ArrayList<E> sortedList) {
		for (int i = 0 ; i < sortedList.size() - 1 ; i++) {
			if (sortedList.get(i).compareTo(sortedList.get(i+1)) > 0) {
				return false;
			}
		}
		
		ArrayList<E> temp = new ArrayList<>(sourceList);
		Collections.sort(temp);
		
		if(!temp.equals(sortedList)) {
			return false;
		}
	
		return true;
	}

	/**
	 * This method initializes the allColors ArrayList.  The allColors ArrayList is used to generate
	 * a smaller ArrayList of random color values.
	 */
	private static void initAllColors() {
		Scanner in;
		try {
			in = new Scanner(new File("/Users/adamdixon/Documents/Homework07/src/input_files/colors.txt"));

			while(in.hasNextLine()) {
				String nextColor = in.nextLine().toLowerCase().trim();

				Main.allColors.add(nextColor);

				Collections.shuffle(Main.allColors);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * This method generates an ArrayList of random integers from 1 to 100 inclusive.
	 */
	public static ArrayList<Integer> getRandomNumberList(int N) {
		Random r = new Random();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0 ; i < N ; i++) {
			list.add((r.nextInt(100) + 1));
		}
		return list;
	}

	/**
	 * This method generates an ArrayList of Strings which are random color names.
	 */
	public static ArrayList<String> getRandomStringList(int N) {
		Random r = new Random();
		
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i = 0 ; i < N ; i++) {
			int randomIndex = r.nextInt(Main.allColors.size());
			
			list.add(Main.allColors.get(randomIndex));
		}
		
		return list;
	}
}