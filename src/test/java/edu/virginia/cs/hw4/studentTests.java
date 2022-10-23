package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

// Got help from Dan's office hours for test_addCourseGrade, line 34

public class studentTests {
    private Course mockCourse;
    private List<Student> mockEnrollment, mockWaitList;
    private Student student;
    private Map<Course,Grade> studentCourseHistory = new HashMap<>();
//    private Map<Course,Grade> studentCourseHistory;
    private Transcript studentTranscript;
    private Prerequisite prereq;
    private Student mockStudent;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        mockCourse = mock(Course.class);
//        studentCourseHistory = mock(Map.class);
        studentCourseHistory.put(mockCourse,Grade.B);
        studentTranscript = new Transcript(student, studentCourseHistory);
        student = new Student(12345, "name", "email", studentTranscript);
        prereq = new Prerequisite(mockCourse, Grade.C);
        mockStudent = mock(Student.class);
    }

    @Test
    public void test_addCourseGrade(){
        when(mockCourse.getCreditHours()).thenReturn(3);
        student.addCourseGrade(mockCourse,Grade.B);
        assertEquals(3.0, student.getGPA());
    }

    @Test
    public void test_hasStudentTakenCourse(){
        assertTrue(student.hasStudentTakenCourse(mockCourse));
    }

    @Test
    public void test_getCourseGrade(){
        when(student.hasStudentTakenCourse(mockCourse)).thenReturn(Boolean.TRUE);
        assertEquals(Grade.B, student.getCourseGrade(mockCourse));
    }

    @Test
    public void test_meetsPrerequisite(){
        when(mockStudent.hasStudentTakenCourse(mockCourse)).thenReturn(false);
        assertEquals(false, mockStudent.meetsPrerequisite(prereq));
        //assertTrue(student.meetsPrerequisite(prereq));
    }

    @Test
    public void test_getGPA(){
        when(mockCourse.getCreditHours()).thenReturn(3);
        assertEquals(3.0, student.getGPA());
    }

}
