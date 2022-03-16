package edu.sdsu.cs160l.university.course;

import java.util.*;

public class CS150 implements Course{
    private CS150(){}

    public static CS150 getInstance(){
        //TODO implement a single
        return null;
    }
    @Override
    public String courseName() {
        return "CS150";
    }

    @Override
    public List<String> courseDescription() {
        return Arrays.asList("Java Programming", "ZyBooks");
    }

    @Override
    public Set<String> prerequisites() {
        return new HashSet<>();
    }
}
