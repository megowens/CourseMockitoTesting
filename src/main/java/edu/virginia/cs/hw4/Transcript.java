package edu.virginia.cs.hw4;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Transcript {
    private Student student;
    private Map<Course, Grade> courseHistory;

    public Transcript(Student student) {
        this.student = student;
        courseHistory = new HashMap<>();
    }
    public Transcript(Student student, Map<Course, Grade> map){
        this.student = student;
        courseHistory = map;
    }

    public void addCourseGrade(Course course, Grade grade) {
            courseHistory.put(course, grade);
    }
    public Grade getCourse(Course course) {
        return courseHistory.get(course);
    }
    public Set<Course> getKeySet() {
        return courseHistory.keySet();
    }
}
