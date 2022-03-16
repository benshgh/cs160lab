package edu.sdsu.cs160l.university.registrar;

import edu.sdsu.cs160l.university.course.Course;
import edu.sdsu.cs160l.university.criteria.StudentStrategy;
import edu.sdsu.cs160l.university.student.Student;

/**
 * Thoughts, does a university have a single registrar office or multiple?
 */
public class Registrar {
    private static Registrar REGISTRAR;

    private Registrar() {}

    public static Registrar getInstance(){
        if(REGISTRAR==null){
            REGISTRAR = new Registrar();
        }
        return REGISTRAR;
    }

    public void enrollStudentToClass(Student student, Course course){
        student.addCourse(course);
    }

    public boolean isStudentValid(Student student, StudentStrategy studentStrategy){
       return studentStrategy.isValid(student);
    }
}
