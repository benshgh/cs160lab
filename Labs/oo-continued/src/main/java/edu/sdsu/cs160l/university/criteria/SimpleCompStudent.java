package edu.sdsu.cs160l.university.criteria;

import edu.sdsu.cs160l.university.course.Course;
import edu.sdsu.cs160l.university.student.Student;
import edu.sdsu.cs160l.university.student.StudentMajor;

/**
 * Advanced student strategy is a student strategy
 * A student has
 * at least 3 course and
 * at least 1 course with 2xx level and
 * at least 1 course with 3xx level and
 * student is from compsci or compengineering major
 */
public class SimpleCompStudent implements StudentStrategy {
    @Override
    public boolean isValid(Student student) {
        boolean satisfiesCourseCount = student.getCoursesEnrolled().size() >= 3;
        boolean satisfiesCourseLevel2xx = false;
        boolean satisfiesCourseLevel3xx = false;
        boolean satisfiesMajor = student.getStudentMajor() == StudentMajor.computersci || student.getStudentMajor() == StudentMajor.computereng;
        for (Course course : student.getCoursesEnrolled()) {
            if (course.courseName().contains("2")) {
                satisfiesCourseLevel2xx = true;
                break;
            }
        }
        for (Course course : student.getCoursesEnrolled()) {
            if (course.courseName().contains("3")) {
                satisfiesCourseLevel3xx = true;
                break;
            }
        }

        return satisfiesCourseCount && satisfiesCourseLevel2xx && satisfiesCourseLevel3xx && satisfiesMajor;
    }
}
