package edu.virginia.cs.hw4;

public class Prerequisite {
    Course course;
    Grade minimumGrade;

    public Prerequisite(Course course, Grade minimumGrade) {
        this.course = course;
        this.minimumGrade = minimumGrade;
    }
    public double getMinGrade() {
        return minimumGrade.gpa;
    }
}
