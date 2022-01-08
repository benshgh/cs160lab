import java.util.*;

/*
 * Lab 7 - Basic OO Design Principles & Patterns Continued 
 * 
 * Patterns - Template, Observer, Singleton 
 * 
 * In this lab, we will explore a couple of more design patterns in "Observer and Template" 
 * to exercise the "separation of concerns” practice of OO design. 
 * 
 * For demonstrating the Observer pattern, we use a number of interfaces and classes for 
 * having Students subscribe to Registrar to get notified for changes to the course 
 * prerequisites or changes to learning paths.
 * 
 * For demonstrating the Template pattern, we use a number of interfaces and classes for 
 * having students fulfill the same sequence of learning paths for graduation.
 * 
 * We will also use Course Factory to demonstrate a quick example of the Singleton pattern, 
 * which can be an anti-pattern if it gets used improperly, mostly in the sense of the Singleton
 * behaving more or less like global variables which should be avoided.   
 ***/

class Lab7 {

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
		
		private Lab7 lab7 = new Lab7();
		
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
	                return lab7.new CS310();
	            case cs210:
	                return lab7.new CS210();
	            case cs496:
	                return lab7.new CS496();
	            default:
	                throw new UnsupportedOperationException();
	        }
	    }
	    
	    public ICourse GetDataStructureCourse(IStudentStatus status)
	    {
	    	// Non Computer Science major students will only be offered 
	    	// with CS496 data structure class
	    	if(status.GetStudentMajor() != StudentMajor.computersci) {
	    		return lab7.new CS496();
	    	}
	    	
	    	// For Computer Science major students, based on their current student level,
	    	// return the data structure course that were offered to them 
	        switch (status.GetStudentLevel())
	        {
	            case junior:
	                return lab7.new CS310();
	            case freshman:
	            case sophomore:
	                return lab7.new CS210();
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
		
		// TODO: to be implemented as a Template method following the template pattern. 
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
		public Student(StudentLevel l, StudentMajor m) {
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
			// TODO: do the same as FulfillGEPath to carry out the path behavior
		}
		
		@Override
		public void FulfillUpperDivisionPath() 
		{
			System.out.println("Complete the Upper Division learning path based on the Upper division path behavior");
			// TODO: do the same as FulfillGEPath to carry out the path behavior
		}
		
		@Override
		public void FulfillMathPath() 
		{
			System.out.println("Complete the Math learning path based on the Math path behavior");
			// TODO: do the same as FulfillGEPath to carry out the path behavior
		}
		
		@Override
		public void FulfillMiscPath() 
		{
			System.out.println("Complete the Misc learning path based on the Misc path behavior");
			// TODO: do the same as FulfillGEPath to carry out the path behavior
		}
		
		// TODO: to be implemented as a Template method following the template pattern. 
		@Override
		public void CompleteGraduationPath()
		{
			// Hint: To complete the graduation path, one has to fulfill all learning paths (the mmethods
			// above in a good order.
			
			// TODO: report - reflect on how the steps of this template method can be changed
			//                over the time without changing the orchestration of carrying out 
			//                the learning paths leading to graduation.
			
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
			
			/*System.out.println("Student Graudation Path: ");
			
			if(gePath != null)
				System.out.println("GE - " + gePath.GetPathBehavior());
			if(lowerDivisionPath != null)
				System.out.println("Lower Division - " + lowerDivisionPath.GetPathBehavior());
			if(mathPath != null)
				System.out.println("Math - " + mathPath.GetPathBehavior());
			if(upperDivisionPath != null)
				System.out.println("Upper Division - " + upperDivisionPath.GetPathBehavior());
			if(miscPath != null)
				System.out.println("Miscellaneous - " + miscPath.GetPathBehavior());*/
		}
	}
	
	
	public interface IRegistrar {
		// Set graduation path (strategy) based on student major and level
		// Set course path for each required learning category 
		void SetGraduationPath(IStudentStatus s_status); 
	}
	
	public class Registrar implements IRegistrar {
			
		/**
		 * Implement an Observer pattern to have students subscribe to notifications
		 * from the registrar on changes to course prerequisites or learning paths.
		 * */
		public static final String CourseChange = "coursechange";
		public static final String LearningPathChange = "learningpathchange";

		// Map, change type with the subscribed listeners to the change type  
		Map<String, List<IRegistrarChangeListener>> stu_listeners = new HashMap<>();
		
		public Registrar() {
	        this.stu_listeners.put(CourseChange, new ArrayList<>());
	        this.stu_listeners.put(LearningPathChange, new ArrayList<>());
	    }

	    public void subscribe(String changeType, IRegistrarChangeListener listener) {
	        List<IRegistrarChangeListener> students = stu_listeners.get(changeType);
	        students.add(listener);
	    }

	    public void unsubscribe(String changeType, IRegistrarChangeListener listener) {
	        
	    	// TODO: implement the unsubscribe
	    	
	    }

	    // Notify students of course prerequisite changes
	    public void notifyCourseChange(String courseName, List<String> prereqs) {
	        List<IRegistrarChangeListener> students = stu_listeners.get(CourseChange);
	        for (IRegistrarChangeListener listener : students) {
	            listener.CoursePrerequisitesChanged(courseName, prereqs);
	        }
	    }
		
	    /**
	     * TODO: implement the function to notify all subscribed students about a learning path change
	     * */
	    public void notifyLearningPathChange(LearningPathType pathType, ILearningPathBehavior lpb) {
	        
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
		 * TODO: Observer pattern coding exercises and reflections
		 * 		Coding - Create multiple students of different combinations of majors and levels 
		 * 			     (each enumerated major of the StudentMajor combined with each 
		 *                enumerated level of StudentLevel), subscribe to the Registrar
		 *               to listen to the changes to course prerequisites and learning paths.
		 *               
		 *               Have registrar send out notifications of changes to course prerequisites, and 
		 *               changes to all learning paths - gepath, lowerdivisionpath, upperdivisionpath,
	     *               mathpath, miscpath.
		 *               
		 *               
		 *      Report - Reflect on: 
		 *               What are the benefits of using the observer pattern, be specific in your elaboration
		 *               using the student registrar example.
		 *               
		 *               Also list the design pattern principles used in this observer pattern example.
		 *               (see lab 6 and 7 instructions for the principles discussed)
		 *                              
		 *               And if this observer subscription model is not used, how would students get notified
		 *               by the Registrar on changes to course prerequisites or changes to graduation criteria 
		 *               in learning paths.
		 *               
		 *               Hint: students have to periodically check changes from registrar.               
		 *               
		 * */
		
		// TODO: create some students
		Lab7 lab7 = new Lab7();
		Student s1 = lab7.new Student(Lab7.StudentLevel.freshman, Lab7.StudentMajor.computersci);
		
		// TODO: subscribe students to a registrar for listening to both course changes and learning path changes.
		Registrar r = lab7.new Registrar();
		
		r.subscribe(Registrar.CourseChange, s1);
		r.subscribe(Registrar.LearningPathChange, s1);
		
		// TODO: create course prerequisite changes, and notify subscribed students 
		
		
		// TODO: create many learning path changes, and notify subscribed students
				
		/**
		 * TODO: Template pattern coding exercises and reflections
		 * 		Coding - See TODO above in CompleteGraduationPath method. 
		 *             
		 *      Report - elaborate on how each step of this template method can be changed
		 *               without changing the orchestration of carrying out the learning paths 
		 *               leading to graduation.
		 *               
		 *               With this approach, what code would be affected if rules are changed to the 
		 *               learning paths for students of different majors and levels to graduate.
		 *                              
		 *               List the design pattern principles used in this template pattern example.
		 *               (see lab 6 and 7 instructions for the principles discussed)
		 * */
		 
		Student s3 = lab7.new Student(Lab7.StudentLevel.freshman, Lab7.StudentMajor.computersci);
		
		System.out.println("\n" + s1);
		
		// TODO: Ask Registrar to set learning path behaviors.
		
		
		// TODO: Carry out the graduation path based on the learning path behaviors, 
		//       also see TODO in CompleteGraduationPath
				
		
		/**
		 * TODO: Singleton pattern coding exercises and reflections
		 * 		Coding - Use the singleton CSCourseFactory to get data structure course name for the student,
		 * 				 then print it out.
		 *             
		 *      Report - Reflect on: What would be the best scenario of using a Singleton pattern 
		 * */
		
		// TODO: create some students with different majors and / or student levels, ask Course Factory Singleton
		// to return the data structure course for students, then print out the course name.
		 
	}
	
}