package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;


import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// Got help from Dan's office hours for test_addCourseGrade, line 34

public class studentTests {
    private Course mockCourse;
    private List<Student> mockEnrollment, mockWaitList;
    private Student mockStudent;
    private Map<Course,Grade> mockCourseHistory = new HashMap<>();
    private Transcript studentTranscript;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        mockCourse = mock(Course.class);
        mockCourseHistory.put(mockCourse,Grade.B);
        studentTranscript = new Transcript(mockStudent, mockCourseHistory);
        mockStudent = new Student(12345, "name", "email", studentTranscript);

    }
    @Test
    public void test_addCourseGrade(){
        when(mockCourse.getCreditHours()).thenReturn(3);
        mockStudent.addCourseGrade(mockCourse,Grade.B);
        assertEquals(3.0, mockStudent.getGPA());
    }

    @Test
    public void test_hasStudentTakenCourse(){

    }

    @Test
    public void test_getCourseGrade(){

    }

    @Test
    public void test_meetsPrerequisite(){

    }

    @Test
    public void test_getGPA(){

    }

}
