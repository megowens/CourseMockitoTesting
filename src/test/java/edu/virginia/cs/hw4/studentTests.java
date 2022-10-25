package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Got help from Dan's office hours for test_addCourseGrade, line 40
// Also got help from Santiago, Leigh, and Celeste, will specify later

public class studentTests {
    private Course mockCourse, mockCourse2;
    private Student student, testStudent;
    private Map<Course,Grade> studentCourseHistory = new HashMap<>();
    private Map<Course,Grade> mockCourseHistory;
    private Transcript studentTranscript, testTranscript;
    private Prerequisite prereq;
    private Student mockStudent;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        mockCourse = mock(Course.class);
        mockCourseHistory = (Map<Course,Grade>) mock(Map.class);
        testTranscript = new Transcript(testStudent, mockCourseHistory);
        testStudent = new Student(12345, "name", "email", testTranscript);
        prereq = new Prerequisite(mockCourse, Grade.C);
        mockStudent = mock(Student.class);
    }

    @Test
    public void test_addCourseGrade(){
        testStudent.addCourseGrade(mockCourse, Grade.A);
        verify(mockCourseHistory).put(mockCourse,Grade.A);
    }

    @Test
    public void test_hasStudentTakenCourse(){
        when(mockCourseHistory.containsKey(mockCourse)).thenReturn(true);
        assertTrue(testStudent.hasStudentTakenCourse(mockCourse));
    }
    @Test
    public void test_hasStudentTakenCourseFalse(){
        when(mockCourseHistory.containsKey(mockCourse)).thenReturn(false);
        assertFalse(testStudent.hasStudentTakenCourse(mockCourse2));
    }

    @Test
    public void test_getCourseGrade(){
        when(mockCourseHistory.containsKey(mockCourse)).thenReturn(true);
        when(mockCourseHistory.get(mockCourse)).thenReturn(Grade.B_MINUS);
        assertEquals(Grade.B_MINUS, testStudent.getCourseGrade(mockCourse));
    }

    @Test
    public void test_meetsPrerequisite(){
        when(mockCourseHistory.containsKey(prereq.course)).thenReturn(true);
        when(mockCourseHistory.get(prereq.course)).thenReturn(Grade.B_MINUS);
        assertTrue(testStudent.meetsPrerequisite(prereq));
    }
    @Test
    public void test_didntTakePrerequisite(){
        when(mockCourseHistory.containsKey(prereq.course)).thenReturn(false);
        assertFalse(testStudent.meetsPrerequisite(prereq));
    }
    @Test
    public void test_doesntMeetPrerequisite(){
        when(mockCourseHistory.containsKey(prereq.course)).thenReturn(true);
        when(mockCourseHistory.get(prereq.course)).thenReturn(Grade.F);
        assertFalse(testStudent.meetsPrerequisite(prereq));
    }

    @Test
    public void test_getGPA(){
        when(mockCourseHistory.isEmpty()).thenReturn(false);
        when(mockCourseHistory.keySet()).thenReturn(Set.of(mockCourse));
        when(mockCourseHistory.get(mockCourse)).thenReturn(Grade.B_MINUS);
        when(mockCourse.getCreditHours()).thenReturn(3);
        assertEquals(2.7, testStudent.getGPA(),1e-4);
    }

}
