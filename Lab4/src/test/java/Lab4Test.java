import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class Lab4Test {

	Lab4.Employee[] eArray = null;
	ArrayList<Lab4.Employee> eLst = null;
	
    @BeforeClass
    public static void beforeClass() {
        // This method will be executed once on initialization time
    }
    
	@Before
	public void before() {
		// This method will be executed once before each test execution
		eLst = new ArrayList<>();
		eLst.add(new Lab4.Employee(new Date(), "John McNeil", 1));
		eLst.add(new Lab4.Employee(new Date(), "Jennifer Hanks", 2));
		eLst.add(new Lab4.Employee(new Date(), "Saumil Shah", 3));
    }

    @AfterClass
    public static void afterClass() {
        // This method will be executed once when all tests are executed
    }

    @After
    public void after() {
        // This method will be executed once after each test execution
    }
    
	@Test
	public void declareAndInitRawArrays() {
		int[] intArray = new int[] {10, 20, 30};
		String[] sArray = {"Java", "is", "a", "great", "programming", "language."};
		eArray = new Lab4.Employee[] { 
				new Lab4.Employee(new Date(), "John McNeil", 1),
				new Lab4.Employee(new Date(), "Jennifer Hanks", 2),
				new Lab4.Employee(new Date(), "Saumil Shah", 3)
		};
		
		System.out.println(intArray);	
		System.out.println(sArray);	
	}

	@Test
	public void declareAndInitArrayLists() {
		//Use ArrayList to instantiate the same arrays in the above declareAndInitRawArrays method 
		//declare as an abstract / interface type, and instantiation of concrete type.
		List<Integer> intArrayLst = new ArrayList<>();
		intArrayLst.add(10);
		intArrayLst.add(20);
		intArrayLst.add(30);
		
		Integer[] intArray = new Integer[] {10, 20, 30};
		List<Integer> intArrayLst1 = new ArrayList<Integer>(Arrays.asList(intArray));
		System.out.println(intArrayLst1);	
		
		// TODO: use the same asList way to create ArrayLists for the sArray and eArray in the above
		// declareAndInitRawArrays method
		// Use JGrasp or eclipse debugging perspectives to inspect the Array lists, 
		// TODO:grab screenshots of debugging views of the array lists as part of the lab submission
		
		// TODO: write your code here
	}
	
	@Test
	public void shallowDeepCloneArrays() {
		// Shallow copy of an ArrayList
        List<Lab4.Employee> clonedELst = (ArrayList<Lab4.Employee>) eLst.clone();
 
        // Change employee information in the cloned list
        // Use JGrasp to display the content of existing employee list eLst and the 
        // cloned employee list clonedELst
        // TODO:grab screenshots of debugging views of the two array lists as part of the lab submission
        clonedELst.get(1).setName("Michael Jordan");
        System.out.println("Employee List");
        eLst.forEach(e -> System.out.println(e.toString()));
        System.out.println("Cloned Employee List");
        clonedELst.forEach(e -> System.out.println(e.toString()));
        
        // TODO: perform a deep copy of the eLst ArrayList by using the overriden Employee clone method.
        // After the deep copy implementation, change the second employee name back to Jennifer Hanks 
        // in the cloned employee list, then use the same printout statements above to 
        // print out the original list and the cloned list
        // TODO: use JGrasp to display the content of original employee list eLst and the cloned employee list
        // and grab screenshots of debugging views of the two array lists as part of the lab submission
        
        clonedELst = new ArrayList<>();
        
        // TODO: write your code here
        
        // After cloning, Update cloned list
        clonedELst.get(1).setName("Jennifer Hanks");
        System.out.println("Employee List");
        // Second employee of the eLst Arraylist should still be Michael Jordan 
        eLst.forEach(e -> System.out.println(e.toString()));

        System.out.println("Cloned Employee List");
        clonedELst.forEach(e -> System.out.println(e.toString()));
	}

	@Test
	public void traverseArrayList() {
		
		for (Lab4.Employee e : eLst) {
			System.out.println(e.toString()); 
		}
		
		// TODO: Setup a breakpoint in the middle of the while loop using Iterator, 
		// and inspect and grab the screenshots of jGrasp or debugging perspective of 
		// showing the employee object and iterator structure during each loop execution.
		Iterator<Lab4.Employee> it = eLst.iterator();
		Lab4.Employee e;
		while (it.hasNext()) {
		    e = (Lab4.Employee) it.next();
		    System.out.println(e.toString());
		}
	}
	
	@Test
	public void removeByValueFromArrayList() {
		
		// This test demonstrates removing by value could
		// result in ConcurrentModificationException where traversing 
		// the array list.
		
		// The right way would be to use the Iterator to remove elements while
		// traversing.
		
		// Removing by value results in ConcurrentModificationException
		try {
			for (Lab4.Employee e : eLst) {
				if(e.getId() % 2 == 1) {
					eLst.remove(e); //remove by value
				}
			}
		} catch (ConcurrentModificationException ex) {
			System.out.println("A concurrenModificationException exception has occurred.");
			System.out.println(ex.getMessage());
		}
	}
	
	@Test
	public void removeByIteratorFromArrayList() {
		
		// The right way to remove elements from ArrayList while traversing 
		// is to use an Iterator 
				
		// TODO: Use an Iterator to remove objects from an ArrayList while traversing.
		// Refer to the previous test for using an iterator.
		// Setup a breakpoint in the middle of the while loop using Iterator, 
		// and inspect and grab the screenshots of jGrasp or debugging perspective of 
		// showing the employee object and iterator structure during each loop execution.
		Iterator<Lab4.Employee> it = eLst.iterator();
		
		// TODO: write your code here
	}
	
	@Test
	public void arrayListToArray() {
		
		// TODO: 
		// Setup a breakpoint after the toArray is called, 
		// inspect and grab the screenshots of jGrasp or debugging perspective of 
		// showing the emplArray array object.

		String[] emplArray = new String[eLst.size()];
		eLst.toArray(emplArray);
		for (String es : emplArray)
			System.out.println(es);
		
	}
	
	@Test
	public void checkIfArrayContainsAString() {
		String[] sArray = {"Java", "is", "a", "great", "programming", "language."};
		
		boolean b = Arrays.asList(sArray).contains("programming");
		System.out.println(b);
	}
	
	@Test
	public void sortArray() {
		
		int[] iArr = {12, 25, 7, 9, 41};
		Arrays.sort(iArr);
		for(int i : iArr){
            System.out.print(i + " ");
        }
		
		String[] sArr = {"Java", "is", "a", "great", "programming", "language."};
        Arrays.sort(sArr);
        
        System.out.println("\n");
        for(String s : sArr){
            System.out.print(s + " ");
        }
        
        System.out.println("\nPerform a sort with the reverse order");
        
        // sort in reverse order using the comparator
        Arrays.sort(sArr, new ReverseComparator()); 
        
        // TODO: use jGrasp or the debugging perspectives to inspect the sArr and grab the screenshot
                
        for(String s : sArr){
            System.out.print(s + " ");
        }
    }
	
	class ReverseComparator<T extends Comparable<T>> implements Comparator<T>
	{
	    @Override
	    public int compare(T a, T b) {
	        return b.compareTo(a);
	    }
	}
	
}