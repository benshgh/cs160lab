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

public class Lab5Test extends NaiveSortTest {

	String[] promotionOrder = null;
	
	final int concatStartN = 5000;
	final int concatStopN = 100000;
	
	@BeforeClass
    public static void beforeClass() {
        // This method will be executed once on initialization time
    }
    
	@Before
	public void before() {
		// This method will be executed once before each test execution
		promotionOrder = new String[] { "mz", "mx", "my" };
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
	public void testWithStringLiterals() {
		// carInventory String array is created and initialized with a number of string literals.
		String[] carInventory = { "mz", "my", "my", "mx", "mz", "mx", "my", "mz" };
		String[] expectedRes = { "mz", "mz", "mz", "mx", "mx", "my", "my", "my" };
		
		// TODO: Set a breakpoint at the start of this method and do step-by-step execution. 
		// Use the variable / expression window in the Debugging perspective to see if 
		// the String objects from literals are referring to the same object value if they
		// are from the same string literal value.
		// For example, compare carInventory[0] object and expectedRes[0] object, they have the
		// same string value "mz", are these two objects are also referring the same String object?
		// You can check if the two objects have the same reference by 
		// adding a following entry in the evaluation window under the name column:
		// carInventory[0] == expectedRes[0]
		// if the two objects are of the same reference, the evaluation should give you a true on
		// the Value column.
		
		// Grab screenshots from TODO above to be included as part of your Lab 5 submission.
		
		assertThat((new Lab5.NaiveSortLab5()).sortCarInventory(carInventory, promotionOrder))
        .hasSize(8)
        .containsExactly(expectedRes);
		
		// TODO: report the outcome of the above assertThat - succeed or fail?
		// Include this part of discussion in the lab report
	}
	
	@Test
	public void testWithStringsFromConstructor() {
		//Change the above testWithStringLiterals method for the instantiation of 
		//the string objects from using literals to using the String constructor (New Operator)
		String[] carInventory = { "mz", "my", "my", "mx", "mz", "mx", "my", "mz" };
		String[] expectedRes = { "mz", "mz", "mz", "mx", "mx", "my", "my", "my" };
		
		String[] carInventoryUsingNew = new String[8]; 
		String[] expectedResUsingNew = new String[8]; 
		
		for (int i = 0; i < 8; i ++) {
			//This creates a new String copy of the carInventory[i] string
			carInventoryUsingNew[i] = new String(carInventory[i]); 
			//This creates a new String copy of the expectedRes[i] string
			expectedResUsingNew[i] = new String(expectedRes[i]);
		}
		
		// TODO: follow the same debugging steps above in the testWithStringLiterals method
		// to inspect and compare the values of carInventoryUsingNew[0] and expectedRes[0]
		
		// Grab screenshots from TODO above to be included as part of your Lab 5 submission.
		
		assertThat((new Lab5.NaiveSortLab5()).sortCarInventory(carInventoryUsingNew, promotionOrder))
        .hasSize(8)
        .containsExactly(expectedRes);
		
		// TODO: report the outcome of the above assertThat - succeed or fail? 
		// If the test result is different from testWithStringLiterals, explain why in your Lab report?
		// Is there a problem in the logic of this test method or there is a problem in
		// Lab5.NaiveSortLab5()).sortCarInventory?
		// If there is a problem, how to fix it?
		
		// TODO: change the code to fix the problem
		
		// Include this part of discussion in the lab report
	}

	@Test
	public void testPassStringArgumentToMethod() {
		// TODO: Using the debugging perspective to inspect the String object 
		// passed to tryToChangePassInString before and after the call 
		
		// Explain why the String object passed to tryToChangePassInString will NOT be
		// changed by the function.
		
		// Include discussion here as part of your Lab report to be submitted.
		
		String s = new String("Hello World");
		String retS = tryToChangePassInString(s);
		
		System.out.println("String passed in: " + s + ", returned String: " + retS);
	}
	
	private String tryToChangePassInString(String s) {
		s = "Change the passed in String";
		return s;
	}
	
	@Test
	public void testReplaceSubString() {
		// Use the String replace method to replace 
		// "Java" in a String "Java is a great language" to "C/C++"
		String s = new String("Java is a great language");
		
		String s_repl = s.replace("Java", "C/C++");
		
		assertTrue(s != s_repl);
		
		// TODO: Use the variable / expression window in the Debugging perspective to see if 
		// the s_repl object is the same as the s object.
		// You can check if the two objects have the same reference by 
		// adding a following entry in the evaluation window under the name column:
		// s_repl == s
		// if the two objects are of the same reference, the evaluation should give you a true on
		// the Value column.
		
		// Grab screenshots from TODO above to be included as part of your Lab 5 submission.
		
		// Use the StringBuilder replace method to replace the first 4 characters of
		// "Java is a great language" to "C/C++"
		StringBuilder sb = new StringBuilder("Java is a great language");
		StringBuilder sb_repl = sb.replace(0, 4, "C/C++");
		
		// TODO: Use the variable / expression window in the Debugging perspective to see if 
		// the s_repl object is the same as the s object.
		
		// Grab screenshots from TODO above to be included as part of your Lab 5 submission.
		
		assertTrue(sb == sb_repl);
	}
	
	@Test
	public void testConcatenateString() {
		// TODO: Compare the performance of using String and StringBuilder to 
		// build a large string, generate the data and plots, draw a conclusion 
		// on the performance comparison, and include the data, plots, conclusion 
		// as part of the Lab 5 report to be submitted.
		
		// Use the following code to measure the elapsed time for executing a piece of logic 
		/*long timeBeforeExec = System.nanoTime();
		// TODO: Test logic
		
		long timeAfterExec = System.nanoTime();
		long elapsedTime = timeAfterExec - timeBeforeExec; 
		
		System.out.println("String, N = " + N + ", elapsed: " + elapsedTime);
		or
		System.out.println("String Builder, N = " + N + ", elapsed: " + elapsedTime);
		*/ 
		
		// TODO: Test with N being 5000, 10000, 15000, ..., 100000
		// Put measured data into a table, plot the results with (x, y) as (N, elapsed)
		
		profileStringConcat();
		
		// TODO: Do the same test logic as in profileStringConcat() by using
		// StringBuilder to concatenate
		profileStringBuilderConcat(); 
		
		// TODO: Discuss why StringBuilder performs much better than String in
		// the concatenation test.
		// Include discussion as part of your Lab report to be submitted.
	}
	
	private void profileStringConcat() {
		// Measure concatenation time using String + method 
		double elapsedTime = 0;
		
		for (int N = concatStartN; N <= concatStopN; N += concatStartN) {
			//Use String method to concatenate
			String s = "";
			long timeBeforeExec = System.nanoTime();
			
			// Test Logic
			for (int i = 0; i < N; i++) {
			  s = s + "o";
			}
			
			long timeAfterExec = System.nanoTime();
			
			// Calculate elapsed time and convert it to seconds
			elapsedTime = (double) (timeAfterExec - timeBeforeExec) / Math.pow(10, 9); 
			
			System.out.println("String, N = " + N + ", elapsed: " + elapsedTime);

		}
	}
	
	private void profileStringBuilderConcat() {
		// TODO: write your implementation here
		
	}
}