package edu.sdsu.cs160l.university.exceptions;

/**
 * General blueprint to how custom exceptions are created
 */
public class ClassFullException extends Exception {
  public ClassFullException(String message) {
    super(message);
  }
}
