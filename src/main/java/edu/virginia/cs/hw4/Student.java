package edu.virginia.cs.hw4;

import java.util.Set;

public class Student {
    private int studentNumber;
    private String name;
    private String email;

    private Transcript transcript;

    public Student(int studentNumber, String name, String email) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.email = email;
        this.transcript = new Transcript(this);
    }

    public Student(int studentNumber, String name, String email, Transcript transcript) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.email = email;
        this.transcript = transcript;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addCourseGrade(Course course, Grade grade) {
        transcript.addCourseGrade(course, grade);
    }

    public boolean hasStudentTakenCourse(Course course) {
        Set<Course> takenCourses = transcript.getKeySet();
        for(Course c : takenCourses){
            if(c.getDepartment().equals(course.getDepartment())){
                if(c.getCatalogNumber() == course.getCatalogNumber()){return true;}
            }
        }
        return false;
    }

    public Grade getCourseGrade(Course course) {
        if (hasStudentTakenCourse(course)) {
            return transcript.getCourse(course);
        }
        throw new IllegalArgumentException("ERROR: Student has no grade for " + course);
    }

    public boolean meetsPrerequisite(Prerequisite prerequisite) {
        if (!hasStudentTakenCourse(prerequisite.course)) {
            return false;
        }
        Grade studentGrade = transcript.getCourse(prerequisite.course);
        return studentGrade.gpa >= prerequisite.minimumGrade.gpa;

    }

    public double getGPA() {
        if (transcript.getKeySet().isEmpty()) {
            throw new IllegalStateException("No courses taken, cannot get GPA");
        }
        double totalGradePoints = 0.0;
        int creditsAttempted = 0;
        for (Course course : transcript.getKeySet()) {
            Grade grade = transcript.getCourse(course);
            int credits = course.getCreditHours();
            totalGradePoints += grade.gpa * credits;
            creditsAttempted += credits;
        }
        return totalGradePoints / creditsAttempted;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof Student otherStudent) {
            return this.studentNumber == otherStudent.studentNumber;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return studentNumber;
    }
}
