import java.util.*;

class Lab1 {
	
	//Sort carInventory based on the order in promotionOrder
	 public static String[] sortCarInventory(String[] carInventory, String[] promotionOrder) {

        String[] sortedCarInventory = new String[carInventory.length];

        int index = 0;

        //Loop for each item in the promotion order
        for (int pIndex = 0; pIndex < promotionOrder.length; pIndex ++) {
            //loop for each car in the carInventory list
            for (String c : carInventory) {
                if (c.equals(promotionOrder[pIndex])) {
                    sortedCarInventory[index] = c;
                    index++;
                    
                }
            }
        }
        return sortedCarInventory;
    }

    public static void main(String[] args) {

        //Test sorting car inventory based on a promotion order
        String[] carInventory = {"mz", "my", "my", "mx", "mz", "mx", "my", "mz", "mx"};
        String[] promotionOrder = {"mz", "mx", "my"};

        String[] sortedList  = Lab1.sortCarInventory(carInventory, promotionOrder);

        for (String s : sortedList) {
            System.out.print(s  + " ");
        }
    }

}