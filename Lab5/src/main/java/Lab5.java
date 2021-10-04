import java.util.*;

/*
 * A naive algorithm for sorting an array based on the order of another array
 * */

class Lab5 {

	public static class NaiveSortLab5 extends NaiveSort {
		@Override
		// Sort carInventory based on the order in promotionOrder
		public String[] sortCarInventory(String[] carInventory, String[] promotionOrder) {

			String[] sortedCarInventory = new String[carInventory.length];

			int index = 0;

			// Loop for each item in the promotion order
			for (int pIndex = 0; pIndex < promotionOrder.length; pIndex++) {
				// loop for each car in the carInventory list
				for (String c : carInventory) {
					// TODO: Is there a problem here? Explain in your lab report
					if (c == promotionOrder[pIndex]) {
						sortedCarInventory[index] = c;
						index++;
					}
				}
			}
			return sortedCarInventory;
	    }
	}
	
	public static void main(String[] args) {
		
	}
	
}