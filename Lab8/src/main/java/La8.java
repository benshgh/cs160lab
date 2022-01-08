import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
 * Lab 8 - Java Exceptions 
 * 
 * When used properly, exceptions can improve code debuggability, 
 * reliability, maintainability, and readability; however, misuse 
 * of them could bring just the opposite of the benefits of using them.
 * 
 * In this lab, we will look into some of the misconceptions and 
 * best practices of using exceptions in Java.
 * 
 * We will exercise the use of checked and unchecked exceptions. 
 * 
 * Follow the TODO comments in Lab8.java for your coding exercises and 
 * reflections as part of your submission for this lab.
 * 
 ***/

class Lab8 {

	public enum StudentLevel
	{
	    freshman,
	    sophomore,
	    junior,
	    senior
	}
	
	public enum StudentMajor
	{
	    computersci,
	    computereng,
	    biology,
	    math,
	    politicalsci
	}
	
	public enum LearningPathType
	{
	    gepath, 
	    lowerdivisionpath,
	    upperdivisionpath,
	    mathpath,
	    miscpath
	}
	
	public enum CourseType
	{
	    cs310, 
	    cs210,
	    cs496
	}
	
	public interface ICourse {
		String GetCourseName();
		Date OfferedFrom();
		Date Ended();
		
		List<String> GetCoveredTopics();
	    List<String> GetPrerequisites();
	}
	
	// Data Structure interface that every data structure course needs to implement
	public interface IDataStructure extends ICourse 
	{
	    List<String> GetCoursesUsingDataStructures();
	}
	
	// Provides fake method implementations of the IDataStructure interface
	public class DSCourseBase {
		public Date OfferedFrom() {
	    	// fake implementation
	    	return new Date();
	    }
	    
		public Date Ended() {
			// fake implementation
	    	return new Date();
		}
		
		public List<String> GetCoveredTopics() {
			// fake implementation
			return new ArrayList<String>();
		}
		
		public List<String> GetPrerequisites() {
			// fake implementation
			return new ArrayList<String>();
		}
		
		public List<String> GetCoursesUsingDataStructures() {
			// fake implementation
			return new ArrayList<String>();
		}
	}
	
	public class CS310 extends DSCourseBase 
	                   implements IDataStructure
	{
		@Override
	    public String GetCourseName() {
	    	return "CS310";
	    }
	}
	
	public class CS210 extends DSCourseBase
					   implements IDataStructure
	{
		@Override
	    public String GetCourseName() {
	    	return "CS210";
	    }
	}
	
	public class CS496 extends DSCourseBase
					   implements IDataStructure
	{
		@Override
	    public String GetCourseName() {
	    	return "CS496";
	    }
	}
	
	/**
	* A factory that creates Course objects based on CourseType.
	* Created Objects are of IDataStructure
	*/
	public static class CSCourseFactory
	{
		private static CSCourseFactory CSCourseFactoryInstance;
		
		private Lab8 lab8 = new Lab8();
		
		// Empty constructor, setting it to private to avoid instance 
		// getting created from constructor
		private CSCourseFactory() {
			
		}
		
		public static CSCourseFactory getInstance() {
			if(CSCourseFactoryInstance == null) {
				CSCourseFactoryInstance = new CSCourseFactory();
			}
			
			return CSCourseFactoryInstance;
		}
		
	    public ICourse GetDataStructureCourse(CourseType type)
	    {
	        switch (type)
	        {
	            case cs310:
	                return lab8.new CS310();
	            case cs210:
	                return lab8.new CS210();
	            case cs496:
	                return lab8.new CS496();
	            default:
	                throw new UnsupportedOperationException();
	        }
	    }
	    
	    public ICourse GetDataStructureCourse(IStudentStatus status)
	    {
	    	// Non Computer Science major students will only be offered 
	    	// with CS496 data structure class
	    	if(status.GetStudentMajor() != StudentMajor.computersci) {
	    		return lab8.new CS496();
	    	}
	    	
	    	// For Computer Science major students, based on their current student level,
	    	// return the data structure course that were offered to them 
	        switch (status.GetStudentLevel())
	        {
	            case junior:
	                return lab8.new CS310();
	            case freshman:
	            case sophomore:
	                return lab8.new CS210();
	            default:
	                throw new UnsupportedOperationException();
	        }
	    }
	}
	
	public class MajorInfo {
		
		private StudentMajor major;
		
		public MajorInfo(StudentMajor m) {
			major = m;
		}
		
		public StudentMajor getMajor() {
			return major;
		}
		
		@Override
		public String toString() {
			return major.toString();
		}
	}
	
	// Learning Path Behavior is recorded as a string
	public interface ILearningPathBehavior {
		String GetPathBehavior();
	}
	
	public class LearningPathBehaviorBase {
		public String behavior;
		
		public String GetPathBehavior() {
			return behavior;
		}
	}
		
	public class GEPathBehavior extends LearningPathBehaviorBase
	                            implements ILearningPathBehavior {
		
		public GEPathBehavior(String pb) {
			behavior = pb;
		}
	}
	
	public class LowerDivisionPathBehavior extends LearningPathBehaviorBase
	   									   implements ILearningPathBehavior {

		public LowerDivisionPathBehavior(String pb) {
			behavior = pb;
		}
	}
	
	public class UpperDivisionPathBehavior extends LearningPathBehaviorBase
	   									   implements ILearningPathBehavior {

		public UpperDivisionPathBehavior(String pb) {
			behavior = pb;
		}
	}

	public class MathPathBehavior extends LearningPathBehaviorBase
		  						  implements ILearningPathBehavior {

		public MathPathBehavior(String pb) {
			behavior = pb;
		}
	}
	
	public class MiscPathBehavior extends LearningPathBehaviorBase
								  implements ILearningPathBehavior {

		public MiscPathBehavior(String pb) {
			behavior = pb;
		}
	}	
	
	// Student interfaces
	public interface IStudentStatus {
		StudentLevel GetStudentLevel();
		StudentMajor GetStudentMajor();
		int GetStudentID();
	}
	
	public interface IGraduationPath {
		// Set course path for each required learning category 
		void SetGEPathBehavior(ILearningPathBehavior lpb);
		void SetLowerDivisionPathBehavior(ILearningPathBehavior lpb);
		void SetUpperDivisionPathBehavior(ILearningPathBehavior lpb);
		void SetMathPathBehavior(ILearningPathBehavior lpb);
		void SetMiscPathBehavior(ILearningPathBehavior lpb);
		
		// Fulfill learning paths for graduation
		void FulfillGEPath();
		void FulfillLowerDivisionPath();
		void FulfillUpperDivisionPath();
		void FulfillMathPath();
		void FulfillMiscPath();
		
		// Implement as a Template method following the template pattern. 
		void CompleteGraduationPath();
	}
	
	// IRegistrarChangeListener for listening to changes from registrar.
	public interface IRegistrarChangeListener {
		void CoursePrerequisitesChanged(String courseName, List<String> prereqs);
		void LearningPathChanged(LearningPathType path, ILearningPathBehavior lpb);
	}
	
	public class Student implements IStudentStatus, 
									IGraduationPath,
									IRegistrarChangeListener {
		private int s_ID;
		private StudentLevel s_Level;
		private StudentMajor s_Major;
		
		//private ArrayList<ICourse> s_courses;  //for future uses
		private ArrayList<MajorInfo> s_majors; //for future uses, a student could have multiple majors
		
		private ILearningPathBehavior gePath = null;
		private ILearningPathBehavior lowerDivisionPath = null;
		private ILearningPathBehavior upperDivisionPath = null;
		private ILearningPathBehavior mathPath = null;
		private ILearningPathBehavior miscPath = null;
				
		// Constructor - initialize with Student Level and Major 
		public Student(int id, StudentLevel l, StudentMajor m) {
			s_ID = id;
			s_Level = l;
			s_Major = m;
			
			s_majors = new ArrayList<MajorInfo>(); //for future uses
			s_majors.add(new MajorInfo(m)); //for future uses
		}
		
		//for future uses
		public void AddMajor(StudentMajor m) {
			if(s_majors != null) 
				s_majors.add(new MajorInfo(m));
		}
		
		@Override
		public String toString() {
			
			StringBuilder sb = new StringBuilder("");
			s_majors.forEach(m -> sb.append(m.toString() + " "));
			
			return "Level = " + s_Level + ", Major/s = " + sb.toString();
		}
		
		/**
		 * IStudentStatus implementation
		 * */
		@Override
		public StudentLevel GetStudentLevel() {
			return s_Level;
		}
		
		@Override
		public StudentMajor GetStudentMajor() {
			return s_Major;
		}
		
		@Override
		public int GetStudentID() {
			return s_ID;
		}
		
		/**
		 * IGraduationPath implementation
		 * */
		// Set the Graduation path for each learning category
		@Override
		public void SetGEPathBehavior(ILearningPathBehavior lpb) {
			gePath = lpb;
		}
		
		@Override
		public void SetLowerDivisionPathBehavior(ILearningPathBehavior lpb) {
			lowerDivisionPath = lpb;
		}
		
		@Override
		public void SetUpperDivisionPathBehavior(ILearningPathBehavior lpb) {
			upperDivisionPath = lpb;
		}
		
		@Override
		public void SetMathPathBehavior(ILearningPathBehavior lpb) {
			mathPath = lpb;
		}
		
		@Override
		public void SetMiscPathBehavior(ILearningPathBehavior lpb) {
			miscPath = lpb;
		}
		
		// Fulfill learning paths for graduation
		@Override
		public void FulfillGEPath() 
		{
			System.out.println("Complete the GE learning path based on the GE path behavior");
			if(gePath != null)
			{
				System.out.println(gePath.GetPathBehavior());
			}
		}
				
		@Override
		public void FulfillLowerDivisionPath() 
		{
			System.out.println("Complete the Lower Division learning path based on the Lower division path behavior");
			// Do the same as FulfillGEPath to carry out the path behavior
		}
		
		@Override
		public void FulfillUpperDivisionPath() 
		{
			System.out.println("Complete the Upper Division learning path based on the Upper division path behavior");
			// Do the same as FulfillGEPath to carry out the path behavior
		}
		
		@Override
		public void FulfillMathPath() 
		{
			System.out.println("Complete the Math learning path based on the Math path behavior");
			// Do the same as FulfillGEPath to carry out the path behavior
		}
		
		@Override
		public void FulfillMiscPath() 
		{
			System.out.println("Complete the Misc learning path based on the Misc path behavior");
			// Do the same as FulfillGEPath to carry out the path behavior
		}
		
		// Implement as a Template method following the template pattern. 
		@Override
		public void CompleteGraduationPath()
		{
			// Hint: To complete the graduation path, one has to fulfill all learning paths (the mmethods
			// above in a good order.
						
		}
		
		/**
		 * IRegistrarChangeListener implementation
		 * */
		@Override
		public void CoursePrerequisitesChanged(String courseName, List<String> prereqs) 
		{
			System.out.println("Course " + courseName + " prerequisites have been changed to: " );
			if(prereqs != null) {
				prereqs.forEach(p -> System.out.println(p.toString()));
			}
			
			// Do something according to course prerequisite change
		}
		
		@Override
		public void LearningPathChanged(LearningPathType path, ILearningPathBehavior lpb)
		{
			switch(path) {
				case gepath:
					System.out.println("GE learning path has changed, check and fulfill GE Path.");
					gePath = lpb;
					FulfillGEPath();
					break;
				case lowerdivisionpath:
					System.out.println("Lower Divsion learning path has changed, check and fulfill Lower Division Path.");
					lowerDivisionPath = lpb;
					FulfillLowerDivisionPath();
					break;
				case upperdivisionpath:
					System.out.println("Upper Divsion learning path has changed, check and fulfill Upper Division Path.");
					upperDivisionPath = lpb;
					FulfillUpperDivisionPath();
					break;
				case mathpath:
					System.out.println("Math learning path has changed, check and fulfill Math Path.");
					mathPath = lpb;
					FulfillMathPath();
					break;
				case miscpath:
					System.out.println("Misc learning path has changed, check and fulfill Misc Path.");
					miscPath = lpb;
					FulfillMiscPath();
					break;
			}
		}
		
		
		public void GetGraduationLearningPaths() {
			IRegistrar r = new Registrar();
			
			r.SetGraduationPath(this);
		}
	}
	
	public class ClassFullException extends Exception { 
		
		public ClassFullException(String errorMessage) {
	        super(errorMessage);
	    }
	}
	
	// TODO: define a StudentAlreadyEnrolledException checked exception
	public class StudentAlreadyEnrolledException extends Exception { 
		// write your implementation
	}
	
	// TODO: define a customized IllegalCourseNameException subclassing
	//       IllegalArgumentException runtime exception
	public class IllegalCourseNameException extends IllegalArgumentException {
	    // write your implementation
	}
	
	public interface IRegistrar {
		// Set graduation path (strategy) based on student major and level
		// Set course path for each required learning category 
		void SetGraduationPath(IStudentStatus s_status); 
	}
	
	public class Registrar implements IRegistrar {
			
		private final int MaxClassSize = 2;  
		
		/**
		 * Implement an Observer pattern to have students subscribe to notifications
		 * from the registrar on changes to course prerequisites or learning paths.
		 * */
		public static final String CourseChange = "coursechange";
		public static final String LearningPathChange = "learningpathchange";
		
		// Courses
		public final String[] CourseNames = 
			{ "CS210", "CS460", "CS530", "CS480"};

		// Map, change type with the subscribed listeners to the change type  
		Map<String, List<IRegistrarChangeListener>> stu_listeners = new HashMap<>();
		
		// Map: course name, student ids
		Map<String, List<Integer>> course_students = new HashMap<>();
		
		public Registrar() {
	        this.stu_listeners.put(CourseChange, new ArrayList<>());
	        this.stu_listeners.put(LearningPathChange, new ArrayList<>());
	    }

		public void enrollStudentCourse(IStudentStatus stu, String course) throws 
																		   Lab8.ClassFullException, 
																		   Lab8.StudentAlreadyEnrolledException,
																		   FileNotFoundException, 
																		   IOException {
			/** TODO: Implement the function:
			 *        
			 *        1) Throw a checked ClassFullException if the class size is already 
			 *           at the MaxClassSize.
			 *        
			 *        2) Create another checked exception StudentAlreadyEnrolledException for the 
			 *           "student already enrolled in the class" condition:
			 *           Specify in function definition - throws the StudentAlreadyEnrolledException
			 *           
			 *           Throw a StudentAlreadyEnrolledException if the student with the same ID 
			 *           is found already added to the class
			 *           
             *        3) Enroll a student to a course - 
			 *           add the student id to the course student list in the course_students map.
			 *           
			 *        4) This function also writes to a log file when a student gets added to a course.
			 *           Code is given for this part, and commented out. This part of the code could throw 
			 *           FileNotFoundException, IOException
			 *        
			 *  TODO: explain:
			 *        1) why a checked exception FullEnrolledException is used for
			 *           the failed condition - adding a student when the class size is full
			 *        2) why a checked exception StudentAlreadyEnrolledException is used for 
			 *           the failed condition - the student is already enrolled in the class
			 *        3) what are some other checked exceptions could be thrown from this code
			 *        4) why the unchecked exceptions (NullPointerException, IllegalArgumentException) 
			 *           are used for the bad conditions of the two arguments.
			 *           If stu is null, throw NullPointerException; if course is not a valid course name,
			 *           throw IllegalArgumentException. 
			 *           If a more specific exception is needed for representing the invalid course name 
			 *           condition, create a customized IllegalCourseNameException, and use it.  
			 *           
			 *           What would be the benefit of using a more specific customized exception here.
			 *        
	    	 *  [Your names]: Write your answers to the above questions.  
			 * 
			 */
			
			// TODO:
			// Throw NullPointerException if stu is null
			
			
			
			// TODO:
			// Throw IllegalArgumentException or IllegalArgumentException if stu is null
			
			
			// TODO:
			// Throw a checked ClassFullException if the class size is already 
			// at the MaxClassSize.
			
			
			
			// TODO:
			// Throw a checked StudentAlreadyEnrolledException if the student already enrolled in the class
			
			
			// TODO:
			// Enroll a student to a course - 
			// add the student id to the course student list in the course_students map.
			
			// stuLst.add(stu.GetStudentID());
			
			// Write to the log file that a student was added to a course
	        
			// TODO: 
			// To uncomment the following code for testing FileNotFoundException exception 
			
			/*String fileName = "/home/tmp/log.txt";

	        FileOutputStream fos = new FileOutputStream(fileName);
	        
	        try (OutputStreamWriter osw =  new OutputStreamWriter(fos, 
	                StandardCharsets.UTF_8)) {

	            String text = "Added " + stu.GetStudentID() + " to " + course + "\n";

	            osw.write(text);
	        }*/ 
		}
		
	    public void subscribe(String changeType, IRegistrarChangeListener listener) {
	        List<IRegistrarChangeListener> students = stu_listeners.get(changeType);
	        students.add(listener);
	    }

	    public void unsubscribe(String changeType, IRegistrarChangeListener listener) {
	        
	    	// Implement the unsubscribe
	    	
	    }

	    // Notify students of course prerequisite changes
	    public void notifyCourseChange(String courseName, List<String> prereqs) {
	        List<IRegistrarChangeListener> students = stu_listeners.get(CourseChange);
	        
	        for (IRegistrarChangeListener listener : students) {
	            listener.CoursePrerequisitesChanged(courseName, prereqs);
	        } 
	    }
		
	    /**
	     * Implement the function to notify all subscribed students about a learning path change
	     * */
	    public void notifyLearningPathChange(LearningPathType pathType, ILearningPathBehavior lpb) {
	    	List<IRegistrarChangeListener> students = stu_listeners.get(LearningPathChange);
	    	
	    	/**
	    	 * TODO: comment on what is wrong with the following code 
	    	 * 
	    	 * Not only the following has the problems described in the lab instructions, but
	    	 * if there is a bug in the LearningPathChanged() call that has an NoSuchElementException
	    	 * or an exception of a subclass of NoSuchElementException, what would happen with this code.
	    	 * 
	    	 * Write your comments here:
	    	 * [Your names]:  
	    	 * 
	    	 * 
	    	 * TODO: change the following looping code with the instructions below
	    	 * 
	    	 * An alternative without using the exception handling that provides a separate state-testing 
	    	 * method to end the loop below is to have the state dependent method return an empty 
	    	 * optional or a distinct value such as null to be checked in the looping condition.
	    	 * 
	    	 * Hint: instead of using true in the while(true), what condition you could use.
	    	 * 
	    	 * */
	    	
	    	try {
	    		Iterator<IRegistrarChangeListener> i = students.iterator();
	    		while(true) {
	    			IRegistrarChangeListener s = i.next();
	    			s.LearningPathChanged(pathType, lpb);
	    		// ...
	    		}
	    	} catch (NoSuchElementException e) {
	    		
		    }
		}	    
	    
		
		// Set graduation path (strategy) based on student major and level
		// Set course path for each required learning category 
		@Override
		public void SetGraduationPath(IStudentStatus s_status) {
			if (s_status != null) {
				IGraduationPath gp = (IGraduationPath) s_status;
				if(gp != null) {
					switch (s_status.GetStudentMajor())
			        {
				        case computersci:
							// Run-time injection of the graduation path behaviors.
				        	// Dependency injections
				        	gp.SetGEPathBehavior(new GEPathBehavior("CS GE Behavior"));
							gp.SetLowerDivisionPathBehavior(new LowerDivisionPathBehavior("CS Lower Behavior"));
							gp.SetUpperDivisionPathBehavior(new LowerDivisionPathBehavior("CS Upper Behavior"));
							gp.SetMathPathBehavior(new LowerDivisionPathBehavior("CS Math Behavior"));
							gp.SetMiscPathBehavior(new LowerDivisionPathBehavior("CS Miscellaneous Behavior"));
							break;
			            case computereng:
			                // implement similar Path behaviors for computer engineering students
			            	break;	
			            case biology:
			            	// implement similar Path behaviors for biology students
			            	break;
			            case math:
			            	// implement similar Path behaviors for math students
			            	break;
			            case politicalsci:
			            	// implement similar Path behaviors for political science students
			            	break;
			            default:
			                throw new UnsupportedOperationException();
			        }
					
					// add some setting the path behaviors (GE, LowerDivision, etc.) based on 
					//       student level
					
					// add setting the path behaviors (GE, LowerDivision, etc.) based on  
					//       a combination of student major and level
					
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		/**
		 * TODO: Try "add students to courses", catch and print exceptions. 
		 * */
		
		// TODO: create some students, add students to a particular course, with the following combinations:
		//       1) valid course name, student ID has not been added to the course
		//       2) valid course name, student ID has been added to the course
		//       3) invalid course name
		//       4) student reference is null
		//       5) valid course name, class size of the course is at Max size.
		//       6) valid course name, class size of the course is within the Max size
		
		// TODO: uncomment the "writing to log file" portion in enrollStudentCourse, test 
		//       without the log file put there, capture the FileNotFoundException. 
		//       And with the log file put, check the output written to the file in
		//       the successful writing to file case. 
		
		Lab8 lab8 = new Lab8();
		Student s1 = lab8.new Student(1285, Lab8.StudentLevel.freshman, Lab8.StudentMajor.computersci);
		Student s2 = lab8.new Student(1286, Lab8.StudentLevel.sophomore, Lab8.StudentMajor.computereng);
		Student s3 = lab8.new Student(1286, Lab8.StudentLevel.junior, Lab8.StudentMajor.math);
		
		// Instantiate registrar
		Registrar r = lab8.new Registrar();
		
		// Write your implementation here.
	}
}