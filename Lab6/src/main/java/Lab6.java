import java.util.*;

/*
 * Lab 6 - Basic OO Design Principles & Patterns 
 * 
 * In this Lab, we will learn some of the OO design principles to accomplish the 
 * above-mentioned objectives and demonstrate those design principles by introducing 
 * two basic OO Design Patterns – Factory (Creational) pattern, 
 * Strategy / Policy (Behavioral) pattern with dependency injection.

	The OO design principles we are learning in this lab are summarized as follows:
	•	Use loose coupling between modules related to each other.
		o	Use interfaces or abstraction for implementing dependencies between modules.
		o	Program to an interface and not an implementation.
	•	Identify the parts of your program that vary and separate them from what stays the same.
	•	Use composition / aggregation over inheritance
		o	Prefer Has-A relationship (composition / aggregation) over Is-A relationship (Inheritance)
	•	Use run-time behavior injection (using interfaces for dependency injection) for 
		accomplishing flexibility in dealing with changing requirements.
	•	Each code module should be open for extension, but closed for modification (Open/Close principle)
	
	OO design patterns are used to address / solve common problems faced 
	in Object-Oriented Programming.

 * For demonstrating the Factory pattern, we use a number of interfaces and classes for 
 * tracking which data structure class a student should take based on student's major and level.
 * 
 * For demonstrating the Strategy / Policy pattern, we use a number of interfaces and classes for 
 * tracking the graduation path changes for students based on student's major and level.
 * */

class Lab6 {

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
	
	public enum CourseType
	{
	    cs310, 
	    cs210,
	    cs496
	}

	public interface IStudentStatus {
		StudentLevel GetStudentLevel();
		StudentMajor GetStudentMajor();
	}
	
	public interface ICourse {
		String GetCourseName();
	}
	
	// Data Structure interface that every data structure course needs to implement
	public interface IDataStructure extends ICourse 
	{
		Date OfferedFrom();
		Date Ended();
		
		List<String> GetCoveredTopics();
	    List<String> GetPrerequisites();
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
	    public String GetCourseName() {
	    	return "CS310";
	    }
	}
	
	public class CS210 extends DSCourseBase
					   implements IDataStructure
	{
	    public String GetCourseName() {
	    	return "CS210";
	    }
	}
	
	public class CS496 extends DSCourseBase
					   implements IDataStructure
	{
	    public String GetCourseName() {
	    	return "CS496";
	    }
	}
	
	public class Student implements IStudentStatus, IGraduationPath {
		private StudentLevel s_Level;
		private StudentMajor s_Major;
		private ILearningPathBehavior gePath = null;
		private ILearningPathBehavior lowerDivisionPath = null;
		private ILearningPathBehavior upperDivisionPath = null;
		private ILearningPathBehavior mathPath = null;
		private ILearningPathBehavior miscPath = null;
				
		public Student(StudentLevel l, StudentMajor m) {
			s_Level = l;
			s_Major = m;
		}
		
		public StudentLevel GetStudentLevel() {
			return s_Level;
		}
		
		public StudentMajor GetStudentMajor() {
			return s_Major;
		}
		
		// Set the Graduation path for each learning category
		public void SetGEPathBehavior(ILearningPathBehavior lpb) {
			gePath = lpb;
		}
		public void SetLowerDivisionPathBehavior(ILearningPathBehavior lpb) {
			lowerDivisionPath = lpb;
		}
		public void SetUpperDivisionPathBehavior(ILearningPathBehavior lpb) {
			upperDivisionPath = lpb;
		}
		public void SetMathPathBehavior(ILearningPathBehavior lpb) {
			mathPath = lpb;
		}
		public void SetMiscPathBehavior(ILearningPathBehavior lpb) {
			miscPath = lpb;
		}
		
		public void GraduationPath() {
			IRegistrar r = new Registrar();
			
			r.SetGraduationPath(this);
			
			System.out.println("Student Graudation Path: ");
			
			if(gePath != null)
				System.out.println("GE - " + gePath.GetPathBehavior());
			if(lowerDivisionPath != null)
				System.out.println("Lower Division - " + lowerDivisionPath.GetPathBehavior());
			if(upperDivisionPath != null)
				System.out.println("Upper Division - " + upperDivisionPath.GetPathBehavior());
			if(mathPath != null)
				System.out.println("Math - " + mathPath.GetPathBehavior());
			if(miscPath != null)
				System.out.println("Miscellaneous - " + miscPath.GetPathBehavior());
		}
	}
	
	// Learning Path Behavior is recorded as a string
	public interface ILearningPathBehavior {
		String GetPathBehavior();
	}
	
	public interface IRegistrar {
		// Set graduation path (strategy) based on student major and level
		// Set course path for each required learning category 
		void SetGraduationPath(IStudentStatus s_status); 
	}
	
	public interface IGraduationPath {
		// Set course path for each required learning category 
		void SetGEPathBehavior(ILearningPathBehavior lpb);
		void SetLowerDivisionPathBehavior(ILearningPathBehavior lpb);
		void SetUpperDivisionPathBehavior(ILearningPathBehavior lpb);
		void SetMathPathBehavior(ILearningPathBehavior lpb);
		void SetMiscPathBehavior(ILearningPathBehavior lpb);
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
	
	public class Registrar implements IRegistrar {
		// Set graduation path (strategy) based on student major and level
		// Set course path for each required learning category 
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
			                // TODO: implement similar Path behaviors for computer engineering students
			            	break;	
			            case biology:
			            	// TODO: implement similar Path behaviors for biology students
			            	break;
			            case math:
			            	// TODO: implement similar Path behaviors for math students
			            	break;
			            case politicalsci:
			            	// TODO: implement similar Path behaviors for political science students
			            	break;
			            default:
			                throw new UnsupportedOperationException();
			        }
					
					// TODO: add some setting the path behaviors (GE, LowerDivision, etc.) based on 
					//       student level
					
					// TODO: add setting the path behaviors (GE, LowerDivision, etc.) based on  
					//       a combination of student major and level
					
					// Hint: create new PathBehavior classes implementing ILearningPathBehaviorimplementing  
					// and extending LearningPathBehaviorBase
                    // and set (inject) instances of those behaviors to the student
					
				}
				
			}
		}
	}

	
	/**
	* A factory that creates Course objects based on CourseType.
	* Created Objects are of IDataStructure
	*/
	public class CSCourseFactory
	{
	    public IDataStructure GetDataStructureCourse(CourseType type)
	    {
	        switch (type)
	        {
	            case cs310:
	                return new CS310();
	            case cs210:
	                return new CS210();
	            case cs496:
	                return new CS496();
	            default:
	                throw new UnsupportedOperationException();
	        }
	    }
	    
	    public IDataStructure GetDataStructureCourse(IStudentStatus status)
	    {
	    	// Non Computer Science major students will only be offered 
	    	// with CS496 data structure class
	    	if(status.GetStudentMajor() != StudentMajor.computersci) {
	    		return new CS496();
	    	}
	    	
	    	// For Computer Science major students, based on their current student level,
	    	// return the data structure course that were offered to them 
	        switch (status.GetStudentLevel())
	        {
	            case junior:
	                return new CS310();
	            case freshman:
	            case sophomore:
	                return new CS210();
	            default:
	                throw new UnsupportedOperationException();
	        }
	        
	        // TODO: change above implementation to return Data Structure instance 
	        //		 based on a combination of student major and level
	        //		 and write test code in main method to test.
	    }
	}
	
	public static void main(String[] args) {
		
		/**
		 * TODO: Factory coding exercises and reflections
		 * 		Coding - Create students of different combinations of majors and levels 
		 * 			     (each enumerated major of the StudentMajor combined with each 
		 *                enumerated level of StudentLevel), ask the CSCourseFactory to return the 
		 *               Data structure course that the student should take.
		 *               Print out: Student Major, space, Student Level, space, and the Data Structure 
		 *                          Course name student would take.  
		 *               
		 *               Change the code to allow students from computereng major to be 
		 *               able to take CS210 only if they are with sophomore or a greater student 
		 *               level than sophomore. 
		 *               
		 *               Test this change, and
		 *               Print out: Student Major, space, Student Level, space, and the Data Structure 
		 *                          Course name student would take. 
		 *               
		 *      Report - Reflect on: 
		 *               If factory and interfaces are not used; instead, 
		 *               use inheritance to extend the student class to have different
		 *               student sub classes (ComputerSciStudent, BioSciStudent, etc.) to
		 *               track which data structure class the student would take based on 
		 *               the student Major and level, what would be the problems there?
		 *               
		 *               Hint: what would happen if rules are changed on which data structure
		 *               classes should be offered to students based on their major and level, 
		 *               what problems the inheritance approach would have?
		 *               
		 *               List the design pattern principles from the interface and factory approach.
		 *               (see lab instructions for the principles discussed)
		 *               With this approach, what would happen if rules are changed to data structure
		 *               classes offered to students of different majors and levels. 
		 * */
		
		Lab6 lab6 = new Lab6();
		Student s1 = lab6.new Student(Lab6.StudentLevel.freshman, Lab6.StudentMajor.computersci);
		
		CSCourseFactory cf = lab6.new CSCourseFactory();
		System.out.println(cf.GetDataStructureCourse(s1).GetCourseName());
		
		
		/**
		 * TODO: Strategy coding exercises and reflections
		 * 		Coding - Create students of different combinations of majors and levels 
		 * 			     (each enumerated major of the StudentMajor combined with each 
		 *                enumerated level of StudentLevel), ask the Registrar to set the 
		 *               Graduation Path for the student.
		 *               
		 *               If the graduation path behavior changes over the time for each learning category,
		 *               what code changes are needed with the above registrar and student setup
		 *               
		 *               Hint: create or extend new path behavior for the changed behavior for a learning category
		 *                     implementing the ILearningPathBehavior, and update the Registrar
		 *                     SetGraduationPath method.
		 *             
		 *      Report - Reflect on: If strategy and interfaces are not used, instead, 
		 *               use inheritance to extend the student class to have different
		 *               student sub classes (ComputerSciStudent, BioSciStudent, etc.) to
		 *               track the graduation path for each learning category based on 
		 *               the student Major and level, what would be the problems there?
		 *               
		 *               Hint: what would happen if rules are changed on the learning paths 
		 *               for students to graduate based on their major and level, 
		 *               what problems the inheritance approach would have?
		 *               
		 *               List the design pattern principles from the interface and strategy approach.
		 *               (see lab instructions for the principles discussed)
		 *               With this approach, what would happen if rules are changed to the learning paths 
		 *               for students of different majors and levels to graduate. 
		 * */
		 
		Lab6.Student s2 = lab6.new Student(Lab6.StudentLevel.freshman, Lab6.StudentMajor.computersci);
		
		s2.GraduationPath();
		 
	}
	
}