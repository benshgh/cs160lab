package edu.sdsu.cs160l.lab10;

import edu.sdsu.cs160l.lab10.college.Registrar;
import edu.sdsu.cs160l.lab10.course.college.LearningPathType;
import edu.sdsu.cs160l.lab10.course.factory.CSCourseFactory;
import edu.sdsu.cs160l.lab10.exception.ClassFullException;
import edu.sdsu.cs160l.lab10.exception.StudentAlreadyEnrolledException;
import edu.sdsu.cs160l.lab10.student.IStudentStatus;
import edu.sdsu.cs160l.lab10.student.Student;
import edu.sdsu.cs160l.lab10.student.StudentGrade;
import edu.sdsu.cs160l.lab10.student.StudentLevel;
import edu.sdsu.cs160l.lab10.student.StudentMajor;
import edu.sdsu.cs160l.lab10.student.StudentUnits;

import org.junit.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Lab10Test {

	static final int NUMOFLEVELS = StudentLevel.values().length;
	static final int NUMOFMAJORS = StudentMajor.values().length;;

	static final int NUMOFSTUDENTS = 100;

	static final int NUMOFLEARNINGPATHS = LearningPathType.values().length;
	static final int MAXUNITSFORALEARNINGPATH = 20;

	static Registrar regis = null;

	@BeforeClass
	public static void beforeClass() {
		// Instantiate registrar
		regis = new Registrar();
		// Create the students data for testing
		Student[][] students = new Student[2 * NUMOFLEVELS][2 * NUMOFMAJORS];
		int sIndex = 0;
		for (int i = 0; i < 2; i++) {
			int lIndex = 0;
			for (StudentLevel level : StudentLevel.values()) {
				int mIndex = 0;
				for (int j = 0; j < 2; j++) {
					for (StudentMajor major : StudentMajor.values()) {
						students[i * NUMOFLEVELS + lIndex][mIndex] = new Student(sIndex, level, major);
						sIndex++;
						mIndex++;
					}
				}
				lIndex++;
			}
		}

		// enroll students to courses with randomized level and major
		try {
			Random rd = new Random();
			// enroll students for testing
			for (String course : CSCourseFactory.COURSE_NAMES) {
				for (int i = 0; i < CSCourseFactory.MAX_CLASS_SIZE; i++) {
					int lIndex = rd.nextInt(2 * NUMOFLEVELS);
					int mIndex = rd.nextInt(2 * NUMOFMAJORS);

					try {
						regis.enrollStudentCourse(students[lIndex][mIndex], course);
					} catch (ClassFullException e) {
						System.out.println("Encountered ClassFullException, " + e.getMessage());
					} catch (StudentAlreadyEnrolledException e) {
						// System.out.println("Encountered StudentAlreadyEnrolledException, " +
						// e.getMessage());
					}
				}
			}

			// enter student grades for testing
			int si = 0;
			for (String course : CSCourseFactory.COURSE_NAMES) {
				si = 0;
				while (si < NUMOFSTUDENTS) {
					int lIndex = rd.nextInt(2 * NUMOFLEVELS);
					int mIndex = rd.nextInt(2 * NUMOFMAJORS);

					double grade = 50 + rd.nextDouble() * 50;
					regis.insertStudentGrade(course, students[lIndex][mIndex], grade);
					si++;
				}
			}

			// enter student course units for testing
			si = 0;
			while (si < NUMOFSTUDENTS) {
				int lIndex = rd.nextInt(2 * NUMOFLEVELS);
				int mIndex = rd.nextInt(2 * NUMOFMAJORS);

				for (int lp = 0; lp < NUMOFLEARNINGPATHS; lp++) {

					int units = rd.nextInt(MAXUNITSFORALEARNINGPATH);

					int lpIndex = rd.nextInt(NUMOFLEARNINGPATHS);

					regis.insertStudentUnitsTakenForALearningPath(students[lIndex][mIndex],
							LearningPathType.values()[lpIndex], units);
				}

				si++;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Encountered FileNotFoundException, " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Encountered IOException, " + e.getMessage());
		} finally {
			System.out.println("Final clean up after all exception catching is done.");
		}
	}

	@Before
	public void before() {
		// This method will be executed once before each test execution

	}

	@AfterClass
	public static void afterClass() {
		// This method will be executed once when all tests are executed
	}

	@After
	public void after() {
		// This method will be executed once after each test execution
	}

	/**
	 * TODO: Conduct programming exercises of using lambdas and method references for 
	 * specifying different sorting strategies for sorting the collections:
	 * 
	 * 1) One level sorting - Sorting by one criterion 2) Multi-level sorting -
	 * Sorting by multiple criteria in a certain order
	 * 
	 * If the test returns a vector, print out the test method name, and the ordered
	 * collection after sorting
	 */

	@Test
	public void testSortingStudentsEnrolledInCourse() {

		List<IStudentStatus> stus = regis.getStudentsEnrolled("CS2");

		// 1) First sort this list stus of IStudentStatus by major name in alphabetical
		// order
		// Print the sorted list for each entry - student ID, major, level
		// Hint: get the string of an enum value by using this example:
		// StudentMajor.biology.toString()
		// You could use the Java stream or Collections.sort
		List<IStudentStatus> sortedStus = stus.stream()
				.sorted((s1, s2) -> s1.getStudentMajor().toString().compareTo(s2.getStudentMajor().toString()))
				.collect(Collectors.toList());

		System.out
				.println("Students enrolled in the CS2XX courses \n " + "sorted by major name in alphabetical order:");
		sortedStus.stream().forEach(s -> System.out.println(s));

		Collections.sort(stus, (s1, s2) -> 
		                 s1.getStudentMajor().toString().compareTo(s2.getStudentMajor().toString()));
		stus.stream().forEach(s -> System.out.println(s));

		// TODO:
		// 2) Sort this list stus of IStudentStatus by level in
		// reverse order of the level ordinal
		//
		// Print the sorted list for each entry - level, major, student ID
		// Hint: get the string of an enum value by using this example:
		// StudentLevel.freshman.ordinal()
		
		System.out.println("Sorting by level in reverse order of the level ordinal");
		
		// Write your code
		
		// 3) Sort this list stus by the ID first, then by student major ordinal
		//    Use Collections.sort
		//    Use combination of method reference and lambdas with fluent style
		System.out.println("Sorting by ID, then by student major ordinal");
		Comparator<IStudentStatus> cstu = 
				Comparator.comparingInt(IStudentStatus::getStudentID) //Use Method reference
				          .thenComparing((s1, s2) -> Integer.compare(s1.getStudentMajor().ordinal(), 
				        		  s2.getStudentMajor().ordinal())); 

		Collections.sort(stus, cstu);
		stus.stream().forEach(s -> System.out.println(s));

		// TODO:
		// 4) Sort this list stus by the major ordinal first, then by student level
		// ordinal, then by student ID.
		System.out.println("Sorting by student major ordinal, then by level ordinal, then by student ID");
		
        // Use Collections.sort and lambdas for sorting
		
		// Write your code
		
		// Print the sorted list for each entry - level, major, student ID
	}

	@Test
	public void testSortingStudentGradesInCourse() {

		List<StudentGrade> stug = regis.getStudentGrades("CS160");
		
		System.out.println("Student Grades in CS160 \n");
		
		//TODO: 
		// 1) Sort this list stug of StudentGrade by grade in the course
		//    in reverse order.
		// Print the sorted list for each entry - student grade, student ID, major, level
		// Hint: refer to above implementations
		
		// Use Collections.sort and lambdas for sorting
		
		System.out.println("");
		System.out.println("Sorting by grades in descending order \n");
		
		// Write your code
		
		//TODO: 
		// 2) Sort this list stug of StudentGrade by level ordinal, 
		//    then major name alphabetical, then by grade in the course
		//    in ascending order, then by id.
		// Print the sorted list for each entry - student grade, student ID, major, level
		// Hint: refer to above implementations
		
		// Use Collections.sort and lambdas for sorting
		
		System.out.println("");
		System.out.println("Sorting by level ordinal, major name, grade, lastly by student ID\n");
		
		//Use combination of method reference and lambdas with fluent style
		
		// Write your code
	}


	@Test
	public void testSortingStudentUnitsInCourse() {

		List<StudentUnits> stuu = regis.getStudentUnits(LearningPathType.upperdivisionpath);
		
		System.out.println("Student Units taken in upper division path \n");
		
		//TODO: 
		// 1) Sort this list stuu of StudentUnits by number of units in ascending order.
		
		// Print the sorted list for each entry - student units, student ID, major, level
		// Hint: refer to above implementations
		
		// Use Java stream and lambdas for sorting
		
		System.out.println("");
		System.out.println("Sorting by units taken in ascending order \n");
		
		// Write your code
		
		//TODO: 
		// 2) First sort this list stuu of StudentUnits by number of units taken in descending order,
		//    then by student level ordinal, then by major ordinal.
		
		// Print the sorted list for each entry - student units, student ID, major, level
		// Hint: refer to above implementations
		
		// Use Collections.sort for sorting
		// Use combination of method reference and lambdas with fluent style 
		// for specifying comparator
		
		System.out.println("");
		System.out.println("Sorting by units taken descending, student level, then major \n");
		
		// Write your code
	}
}
