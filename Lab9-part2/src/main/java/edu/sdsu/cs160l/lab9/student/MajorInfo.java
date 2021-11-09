package edu.sdsu.cs160l.lab9.student;

public class MajorInfo {

  private final StudentMajor major;

  public MajorInfo(StudentMajor m) {
    major = m;
  }

  public StudentMajor getMajor() {
    return major;
  }

  @Override
  public String toString() {
    switch (major) {
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
