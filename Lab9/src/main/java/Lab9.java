import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/*
 * Lab 9 - Streams, Lambdas, Functional Programming  
 * 
 
 * 
 * Follow the TODO comments in Lab9.java for your coding exercises and 
 * reflections as part of your submission for this lab.
 * 
 ***/

class Lab9 {

	/**
	 * Enums - Beginning
	 * */
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
	/**
	 * Enums - Ending
	 * */
	
	/**
	 * Course - Beginning
	 * */
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
		public static final int MAXCLASSSIZE = 20;
		
		private static CSCourseFactory CSCourseFactoryInstance;
		
		// Courses
		public static final List<String> CourseNames = 
			Arrays.asList("CS210", "CS250", "CS460", "CS530", 
			  "CS480", "CS514", "CS240", "CS370", 
			  "CS496", "CS320", "CS150", "CS160", 
			  "CS549", "CS596", "CS582", "CS649");
		
		private Lab9 lab9 = new Lab9();
		
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

		public static boolean IsCourseValid(String course) {
			return CSCourseFactory.CourseNames.stream()
					  .anyMatch(x -> x.equals(course));
		}
		
		
	    public ICourse GetDataStructureCourse(CourseType type)
	    {
	        switch (type)
	        {
	            case cs310:
	                return lab9.new CS310();
	            case cs210:
	                return lab9.new CS210();
	            case cs496:
	                return lab9.new CS496();
	            default:
	                throw new UnsupportedOperationException();
	        }
	    }
	    
	    public ICourse GetDataStructureCourse(IStudentStatus status)
	    {
	    	// Non Computer Science major students will only be offered 
	    	// with CS496 data structure class
	    	if(status.getStudentMajor() != StudentMajor.computersci) {
	    		return lab9.new CS496();
	    	}
	    	
	    	// For Computer Science major students, based on their current student level,
	    	// return the data structure course that were offered to them 
	        switch (status.getStudentLevel())
	        {
	            case junior:
	                return lab9.new CS310();
	            case freshman:
	            case sophomore:
	                return lab9.new CS210();
	            default:
	                throw new UnsupportedOperationException();
	        }
	    }
	}
	
	/**
	 * Course - Ending
	 * */
	
	/**
	 * Student - Beginning
	 * */
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
			switch(major) {
				case computersci:
					return "computersci";
					
				case computereng:
					return "computereng";
					
				case biology:
					return "biology";
					
				case math:
					return "math";
					
				case politicalsci:
					return "politicalsci";
			}
			
			return "";
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
		StudentLevel getStudentLevel();
		StudentMajor getStudentMajor();
		int getStudentID();
	}
	
	public interface IGraduationPath {
		// Set course path for each required learning category 
		void setGEPathBehavior(ILearningPathBehavior lpb);
		void setLowerDivisionPathBehavior(ILearningPathBehavior lpb);
		void setUpperDivisionPathBehavior(ILearningPathBehavior lpb);
		void setMathPathBehavior(ILearningPathBehavior lpb);
		void setMiscPathBehavior(ILearningPathBehavior lpb);
		
		// Fulfill learning paths for graduation
		void fulfillGEPath();
		void fulfillLowerDivisionPath();
		void fulfillUpperDivisionPath();
		void fulfillMathPath();
		void fulfillMiscPath();
		
		// Implement as a Template method following the template pattern. 
		void completeGraduationPath();
	}
	
	// IRegistrarChangeListener for listening to changes from registrar.
	public interface IRegistrarChangeListener {
		void coursePrerequisitesChanged(String courseName, List<String> prereqs);
		void learningPathChanged(LearningPathType path, ILearningPathBehavior lpb);
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
			String sLevel = "";
			
			switch(s_Level) {
				case freshman:
					sLevel = "freshman";
					break;
				case sophomore:
					sLevel = "sophomore";
					break;
				case junior:
					sLevel = "junior";
					break;
				case senior:
					sLevel = "senior";
			}
			
			StringBuilder sb = new StringBuilder("");
			s_majors.forEach(m -> sb.append(m.toString() + " "));
			
			return "Level = " + sLevel + ", Major/s = " + sb.toString();
		}
		
		/**
		 * IStudentStatus implementation
		 * */
		@Override
		public StudentLevel getStudentLevel() {
			return s_Level;
		}
		
		@Override
		public StudentMajor getStudentMajor() {
			return s_Major;
		}
		
		@Override
		public int getStudentID() {
			return s_ID;
		}
		
		/**
		 * IGraduationPath implementation
		 * */
		// Set the Graduation path for each learning category
		@Override
		public void setGEPathBehavior(ILearningPathBehavior lpb) {
			gePath = lpb;
		}
		
		@Override
		public void setLowerDivisionPathBehavior(ILearningPathBehavior lpb) {
			lowerDivisionPath = lpb;
		}
		
		@Override
		public void setUpperDivisionPathBehavior(ILearningPathBehavior lpb) {
			upperDivisionPath = lpb;
		}
		
		@Override
		public void setMathPathBehavior(ILearningPathBehavior lpb) {
			mathPath = lpb;
		}
		
		@Override
		public void setMiscPathBehavior(ILearningPathBehavior lpb) {
			miscPath = lpb;
		}
		
		// Fulfill learning paths for graduation
		@Override
		public void fulfillGEPath() 
		{
			System.out.println("Complete the GE learning path based on the GE path behavior");
			if(gePath != null)
			{
				System.out.println(gePath.GetPathBehavior());
			}
		}
				
		@Override
		public void fulfillLowerDivisionPath() 
		{
			System.out.println("Complete the Lower Division learning path based on the Lower division path behavior");
			// Do the same as FulfillGEPath to carry out the path behavior
		}
		
		@Override
		public void fulfillUpperDivisionPath() 
		{
			System.out.println("Complete the Upper Division learning path based on the Upper division path behavior");
			// Do the same as FulfillGEPath to carry out the path behavior
		}
		
		@Override
		public void fulfillMathPath() 
		{
			System.out.println("Complete the Math learning path based on the Math path behavior");
			// Do the same as FulfillGEPath to carry out the path behavior
		}
		
		@Override
		public void fulfillMiscPath() 
		{
			System.out.println("Complete the Misc learning path based on the Misc path behavior");
			// Do the same as FulfillGEPath to carry out the path behavior
		}
		
		// Implement as a Template method following the template pattern. 
		@Override
		public void completeGraduationPath()
		{
			// Hint: To complete the graduation path, one has to fulfill all learning paths (the mmethods
			// above in a good order.
						
		}
		
		/**
		 * IRegistrarChangeListener implementation
		 * */
		@Override
		public void coursePrerequisitesChanged(String courseName, List<String> prereqs) 
		{
			System.out.println("Course " + courseName + " prerequisites have been changed to: " );
			if(prereqs != null) {
				prereqs.forEach(p -> System.out.println(p.toString()));
			}
			
			// Do something according to course prerequisite change
		}
		
		@Override
		public void learningPathChanged(LearningPathType path, ILearningPathBehavior lpb)
		{
			switch(path) {
				case gepath:
					System.out.println("GE learning path has changed, check and fulfill GE Path.");
					gePath = lpb;
					fulfillGEPath();
					break;
				case lowerdivisionpath:
					System.out.println("Lower Divsion learning path has changed, check and fulfill Lower Division Path.");
					lowerDivisionPath = lpb;
					fulfillLowerDivisionPath();
					break;
				case upperdivisionpath:
					System.out.println("Upper Divsion learning path has changed, check and fulfill Upper Division Path.");
					upperDivisionPath = lpb;
					fulfillUpperDivisionPath();
					break;
				case mathpath:
					System.out.println("Math learning path has changed, check and fulfill Math Path.");
					mathPath = lpb;
					fulfillMathPath();
					break;
				case miscpath:
					System.out.println("Misc learning path has changed, check and fulfill Misc Path.");
					miscPath = lpb;
					fulfillMiscPath();
					break;
			}
		}
		
		
		public void GetGraduationLearningPaths() {
			IRegistrar r = new Registrar();
			
			r.SetGraduationPath(this);
		}
	}
	
	public class StudentGrade {
		private IStudentStatus stuStatus;
		private double grade; //A: 4.0, B: 3.0, C: 2.0, D: 1.0, F: 0
		
		StudentGrade(IStudentStatus stu, double g) {
			stuStatus = stu;
			grade = g;
		}
		
		public IStudentStatus getStudentStatus() {
			return stuStatus;
		}
		
		public double getGrade() {
			return grade;
		}
	}
	
	public class StudentUnits {
		private IStudentStatus stuStatus;
		private int units; 
		
		StudentUnits(IStudentStatus stu, int u) {
			stuStatus = stu;
			units = u;
		}
		
		public IStudentStatus getStudentStatus() {
			return stuStatus;
		}
		
		public int getUnits() {
			return units;
		}
	}
	
	/**
	 * Student - Ending
	 * */
	
	/**
	 * Exceptions - Beginning
	 * */
	public class ClassFullException extends Exception { 
		
		public ClassFullException(String errorMessage) {
	        super(errorMessage);
	    }
	}
	
	// StudentAlreadyEnrolledException checked exception
	public class StudentAlreadyEnrolledException extends Exception { 
		
		public StudentAlreadyEnrolledException(String errorMessage) {
	        super(errorMessage);
	    }
	}
	
	// Customized runtime exception - IllegalCourseNameException 	
	public class IllegalCourseNameException extends IllegalArgumentException {
	    public IllegalCourseNameException(String errorMessage) {
	        super(errorMessage);
	    }
	}
	/**
	 * Exceptions - Ending
	 * */
	
	/**
	 * Registrar - Beginning
	 * */
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
		
		// Course student enrollments
		// Map: Key - course name, Value: List of students
		// enrolled in the course
		Map<String, List<IStudentStatus>> course_students = new HashMap<>();
		
		// Course student grades
		// Map: course name, Map (student id, grade)
		//   Key - course name,
		//   Value - Hash Map: Key - student ID, Value - student grade 
		Map<String, Map<Integer, StudentGrade>> course_grades = new HashMap<>();
		
		// Course student grades
		// Map: course name, list of StudentGrade 
		//   Key - course name,
		//   Value - List of StudentGrade objects
		// For future use
		//Map<String, List<StudentGrade>> course_grades = new HashMap<>();
		
		// Student course units taken for learning paths
		// Map: student ID, Map (learning path, units taken)
		//   Key - student ID,
		//   Value - Hash Map: Key - LearningPathType, Value - course units
		Map<Integer, Map<LearningPathType, StudentUnits>> student_units_taken = new HashMap<>();
				
		public Registrar() {
	        this.stu_listeners.put(CourseChange, new ArrayList<>());
	        this.stu_listeners.put(LearningPathChange, new ArrayList<>());
	    }

		public void enrollStudentCourse(IStudentStatus stu, String course) throws 
																		   ClassFullException, 
																		   StudentAlreadyEnrolledException,
																		   FileNotFoundException, 
																		   IOException {

			// Throw NullPointerException if stu is null
			if(stu == null) {
				throw new NullPointerException("The student reference passed in is NULL, cannot continue.");
			}
						
			// Throw IllegalArgumentException if course argument is not valid
			if(!CSCourseFactory.IsCourseValid(course)) {
				throw new IllegalCourseNameException("The student argument passed in is NULL, cannot continue.");
			}
			
			if(!course_students.containsKey(course)) {
				// if the student list of the course has not been established. 
				this.course_students.put(course, new ArrayList<>());
			}
			
			// Throw a checked ClassFullException if the class size is already 
			// at the MaxClassSize.
			List<IStudentStatus> stuLst = this.course_students.get(course);
			if(stuLst.size() == CSCourseFactory.MAXCLASSSIZE) {
				throw new ClassFullException(course + " Class size is full, " + 
			                                 "failed adding " + stu.getStudentID() +
			                                 " to the class.");
			}
			
			// Throw a checked StudentAlreadyEnrolledException if the student 
			// is already enrolled in the class
			
			// TODO: use the Stream way (the same as above CSCourseFactory.IsCourseValid) 
			//       to check if the student is already enrolled in the class;
			//       if so, throw a StudentAlreadyEnrolledException
			
			
			// Enroll a student to a course - 
			// add the student id to the course student list in the course_students map.
			stuLst.add(stu);

		}
		
		// insert a course grade for a student
		public void insertStudentGrade(String course, IStudentStatus stu, double grade) {
			
			if(stu == null) 
				return;
			
			// Throw IllegalArgumentException if course argument is not valid
			if(!CSCourseFactory.IsCourseValid(course)) {
				throw new IllegalCourseNameException("The student argument passed in is NULL, cannot continue.");
			}
			
			if(course_grades != null) {
				if(!course_grades.containsKey(course)) {
					// if the grade list of the course has not been established. 
					this.course_grades.put(course, new HashMap<>());
				}
				
				HashMap<Integer, StudentGrade> gradeMap = (HashMap<Integer, StudentGrade>)course_grades.get(course);
				
				gradeMap.put(stu.getStudentID(), new StudentGrade(stu, grade));
			}
		}
		
		// insert units taken for a learning path for a student
		public void insertStudentUnitsTakenForALearningPath(IStudentStatus stu, LearningPathType path, int units) {
			
			if(stu == null)
				return;
			
			if(student_units_taken != null) {
				if(!student_units_taken.containsKey(stu.getStudentID())) {
					// if the grade list of the course has not been established. 
					this.student_units_taken.put(stu.getStudentID(), new HashMap<>());
				}
				
				Map<LearningPathType, StudentUnits> unitsTaken = student_units_taken.get(stu.getStudentID());
				unitsTaken.put(path, new StudentUnits(stu, units));
			}
		}
		
		/**
		 * use stream to conduct functional programming exercises for 
		 * map, reduce, filter
		 * */
		
		/**
		* Returns number of students enrolled in a course where the students are 
		* of a particular major 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  major     student major
		* @param  course    course name
		* @return 			number of students enrolled in a course where the students are 
		*                   of the major passed-in
		* @see         
		*/
		public int getNumOfStudentsEnrolled (StudentMajor major, String course) {
			
			List<IStudentStatus> stuLst = this.course_students.get(course);
			
			if(stuLst == null) 
				return 0;
			
			return (int) stuLst.stream()
						       .filter(s -> s.getStudentMajor() == major).count();
		}
		
		// TODO: use the similar stream operations as in the above getNumOfStudenstEnrolled
		//       method to implement the following method
		
		/**
		* Returns number of students enrolled in a course where the students are of a 
		* particular student level 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  sl        student level 
		* @param  course    course name
		* @return 			number of students enrolled in a course where the students are 
		*                   of the student level passed-in
		* @see         
		*/
		public int getNumOfStudentsEnrolled (StudentLevel sl, String course) {
			
			//Write your implementation
			
			return 0;
			
		}
		
		// TODO: use the similar stream operations as in the above getNumOfStudenstEnrolled
		//       method to implement the following method
		
		/**
		* Returns number of students enrolled in a course where the students are 
		* of a particular major and a particular student level 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  major     student major 
		* @param  sl        student level 
		* @param  course    course name
		* @return 			number of students enrolled in a course where the students are 
		*                   of the student major and the student level passed-in
		* @see         
		*/
		public int getNumOfStudentsEnrolled (StudentLevel sl, StudentMajor major, String course) {
			
			//Write your implementation
			
			return 0;
		}
		
		/**
		* Returns a list of IDs of students enrolled in a group of courses of a certain level 
		* where the students are of a particular major 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  major            student major 
		* @param  courseStarting   courses that start with, e.g., "CS2" meaning all courses starting with "CS2"  
		* @return List of IDs of students enrolled in the courses starting with courseStarting,
		*         e.g., CS5XX (CS500 level courses: CS530, CS514, etc.). Note in the case of 
		*         a student enrolled into multiple CS5XX course, its student ID should be added 
		*         to the return list ONCE.
		* @see         
		*/
		public List<Integer> getStudentsEnrolled (StudentMajor major, String courseStarting) {
			
			/**
			 * Note if students have trouble in understanding ONE sequence of streamlined operations,
			 * they could also use the split implementation followed (which I commented out here).
			 * */
			return this.course_students.entrySet().stream()
			  .filter(c -> c.getKey().startsWith(courseStarting))
			  .map(Map.Entry::getValue)
			  .flatMap(s -> s.stream())
			  .filter(s -> s.getStudentMajor() == major)
			  .map(ss -> ss.getStudentID())
			  .distinct()     
		      .collect(Collectors.toList());
			  
			
			/*List<List<IStudentStatus>> lsts = this.course_students.entrySet().stream()
				  .filter(c -> c.getKey().startsWith(courseStarting))
				  .map(Map.Entry::getValue)
				  .collect(Collectors.toList());
			
			// flatmap flattens all List<IStudentStatus> in lsts to
            // individual IStudentStatus objects.
			
			// flattening means following transformation:
			// using an integer array for illustration
			// before flattening - [ [1, 3], [5, 17], [22, 65] ]
			// after flattening - [ 1, 3, 5, 17, 22, 65 ]
			List<IStudentStatus> concatLst = lsts.stream()
			                                 .flatMap(l -> l.stream()) 
			                                 .collect(Collectors.toList());

			//Note distinct() is used here for only counting a student once
			return concatLst.stream()
					        .filter(s -> s.getStudentMajor() == major)
					        .map(ss -> ss.getStudentID())
					        .distinct()     
					        .collect(Collectors.toList()); */
		}
		
		// TODO: following the instructions below
		/**
		* Returns the grades of students of a course
		* where the students are of a particular major 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  major         student major 
		* @param  course   		course name 
		* @return grades of the students enrolled in a course where the students
		*         are of a particular major 
		* @see         
		*/
		public List<Double> getStudentsGrades (StudentMajor major, String course) {
			
			//Write your implementation
			
			return null;
	
		}
		
		/**
		* Returns the average grade of students of a course
		* where the students are of a particular student level
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  level   student level 
		* @param  course  course name  
		* @return average grade of the students in a course where the students
		*         are of a particular student level 
		* @see         
		*/
		public double getAvgGrade (StudentLevel level, String course) {
			
			// get the map of Student Grades given the course.
			Map<Integer, StudentGrade> stuGrades = this.course_grades.get(course);
			
			if(stuGrades == null)
				return 0; // later consider to return an empty list
			
			// use filter and mapToDouble to get the list of Students(IStudentStatus) of 
			// the level passed-in, then map and calculate the average grades
			OptionalDouble avgGrade = stuGrades.entrySet().stream()
											     .filter(s -> s.getValue().getStudentStatus().getStudentLevel() == level)
											     .mapToDouble(s -> s.getValue().getGrade())
											     .average();
			
			if(avgGrade.isPresent())
				return avgGrade.getAsDouble();
			
			return 0;
			 		
		}
		
		// TODO: following the instructions below
		/**
		* Returns the top N students (in gpa) of a course
		* where the students are of a particular major 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  numOfTopStu   number of top students 
		* @param  major         student major 
		* @param  course   		course name 
		* @return grades of the students enrolled in a course where the students
		*         are of a particular major 
		* @see         
		*/
		public List<StudentGrade> getTopStudents (int numOfTopStu, StudentMajor major, String course) {
			
			//Write your implementation
			
			return null;
			 		
		}
		
		// TODO: following the instructions below
		/**
		* Returns the average grade of students enrolled in a group of courses 
		* of a certain course level (CS4XX, CS5XX, or ...)
		* where the students are of a particular major
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  major           student major
		* @param  courseStarting  course Starting, e.g., "CS2" meaning all courses starting with "CS2"  
		* @return average grade of the students in a course level where the students
		*         are of a particular major 
		* @see         
		*/
		public double getAvgGradeCourseLevel (StudentMajor major, String courseStarting) {
			
			// Use filter and map to
			// get the list of the Hashmaps of the students' grades in the courses of the course level
			
			// Use filter to get the grades of students of the passed-in major
			
			// Hint: flatMap needs to used
			
			//Write your implementation
			
			return 0.0;
		}
				
		// 
		// TODO: following the instructions below
		/**
		* Returns List of StudentUnits objects of a learning path for 
		* students of a particular major and a particular level 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  major         student major 
		* @param  level         student level
		* @param  lPathType     student learning path
		* @return A list of StudentUnits for students of a particular major 
		*         and/or a particular level 
		* @see         
		*/
		public List<StudentUnits> getStudentUnits (StudentMajor major, 
												   StudentLevel level,
												   LearningPathType lPathType) {
			
			// use filter and map to get the list of Students(StudentUnits) where
			// students are of the major and the level passed-in
			
			// use filter to sort out the StudentUnits for the passed-in learning path
			
			//Write your implementation
			
			return null;
		}
		
		/**
		* Returns Total student course units a student has taken, use reduce 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  stuID         student ID
		* @return Total student course units a student has taken 
		* @see         
		*/
		public int getStudentTotalUnits (int stuID) {
		
			Map<LearningPathType, StudentUnits> su = student_units_taken.get(stuID);
			
			if (su == null)
				return 0; 
			
			return  su.entrySet().stream()
					.map(Map.Entry::getValue)
					.map(s -> s.getUnits())
					.reduce(0, (subtotal, nUnits) -> subtotal + nUnits);
		}
				
		/**
		* Returns Average number of course units students of a major have taken 
		*         for a particular learning path 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  major         student major
		* @param  lPathType     student learning path
		* @return Average number of course units students of a major have taken 
		*         for the given learning path 
		* @see         
		*/
		public double getAvgCourseUnits (StudentMajor major,
				                      LearningPathType lPathType) {
		
			// use filter and map to get the list of StudentUnits where
			// students are of the passed-in major and the learning Path Type is 
			// of the passed-in lPathType
			OptionalDouble avgUnits =   this.student_units_taken.entrySet().stream()
					  .filter(su -> su.getValue().containsKey(lPathType))
					  .map(Map.Entry::getValue) // get Map<LearningPathType, StudentUnits>
					  .flatMap(su -> su.values().stream()) // flattern each map --> List<StudentUnits> 
					  .filter(su -> (su.getStudentStatus().getStudentMajor() == major))
					  .mapToDouble(su -> su.getUnits())
					  .average();
			
			return avgUnits.isPresent() ? avgUnits.getAsDouble() : 0.0;
							  
		}
		
		// TODO: Max number of units students of a level have taken for a particular
		//       learning path
		/**
		* Returns Max number of course units students of a level have taken 
		*         for a particular learning path 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  level         student level
		* @param  lPathType     student learning path
		* @return Max number of course units students of a level have taken 
		*         for the given learning path 
		* @see         
		*/
		public int getMaxCourseUnits (StudentLevel level,
				                         LearningPathType lPathType) {
		
			//Write your implementation
			
			return 0;
							  
		}
		
		// TODO: Average number of total units students have taken
		/**
		* Returns Average number of total course units students have taken 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  major         student major 
		* @return Average number of total units students have taken 
		* @see         
		*/
		public double getAvgTotalUnits () {
			
			//Write your implementation
			
			return 0.0;
			
		}
		
		// TODO: Student units for students from non-computing majors for a learning path
		/**
		* Returns Average number of course units students of non-computing majors have taken
		*         for a learning path 
		* 
		* <p>
		* 
		* <p>
		* Assumption: 
		*
		* @param  lPathType     student learning path 
		* @return Average number of course units students of non-computing majors have taken
		*         for a learning path 
		* @see         
		*/
		public double getAvgUnitsNonComputing (LearningPathType lPathType) {
			
			//Write your implementation
			
			return 0.0;		
		}
		
		/**
		 * Map, Filter, Reduce. Ending  
		 * */
		
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
	            listener.coursePrerequisitesChanged(courseName, prereqs);
	        } 
	    }
		
	    /**
	     * Implement the function to notify all subscribed students about a learning path change
	     * */
	    public void notifyLearningPathChange(LearningPathType pathType, ILearningPathBehavior lpb) {
	    	List<IRegistrarChangeListener> students = stu_listeners.get(LearningPathChange);
	    	
    		Iterator<IRegistrarChangeListener> i = students.iterator();
    		while(i.hasNext()) {
    			IRegistrarChangeListener s = i.next();
    			s.learningPathChanged(pathType, lpb);
    		}	
		}	    
	    
		
		// Set graduation path (strategy) based on student major and level
		// Set course path for each required learning category 
		@Override
		public void SetGraduationPath(IStudentStatus s_status) {
			if (s_status != null) {
				IGraduationPath gp = (IGraduationPath) s_status;
				if(gp != null) {
					switch (s_status.getStudentMajor())
			        {
				        case computersci:
							// Run-time injection of the graduation path behaviors.
				        	// Dependency injections
				        	gp.setGEPathBehavior(new GEPathBehavior("CS GE Behavior"));
							gp.setLowerDivisionPathBehavior(new LowerDivisionPathBehavior("CS Lower Behavior"));
							gp.setUpperDivisionPathBehavior(new LowerDivisionPathBehavior("CS Upper Behavior"));
							gp.setMathPathBehavior(new LowerDivisionPathBehavior("CS Math Behavior"));
							gp.setMiscPathBehavior(new LowerDivisionPathBehavior("CS Miscellaneous Behavior"));
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
	
	/**
	 * Registrar - Ending
	 * */
	
	
	public static void main(String[] args) {
	

	}
}