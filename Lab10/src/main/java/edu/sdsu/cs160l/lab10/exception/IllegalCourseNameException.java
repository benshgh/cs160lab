package edu.sdsu.cs160l.lab10.exception;

// Customized runtime exception - IllegalCourseNameException
public class IllegalCourseNameException extends IllegalArgumentException {
  public IllegalCourseNameException(String errorMessage) {
    super(errorMessage);
  }
}
