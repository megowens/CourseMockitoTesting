package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Got help from Celeste's office hours for test_addCourseGrade, line 35
// Got help from Leigh's office hours for test_meetsPrerequisite and test_getGPA, lines 64 & 82
// Got help from Santiago's office hours for test_hasStudentTakenCourse and test_getCourseGrade, lines 41 & 52

public class studentTests {
    private Course mockCourse;
    private Student testStudent;
    private Map<Course,Grade> mockCourseHistory;
    private Transcript testTranscript;
    private Prerequisite prereq;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        mockCourse = mock(Course.class);
        mockCourseHistory = (Map<Course,Grade>) mock(Map.class);
        testTranscript = new Transcript(testStudent, mockCourseHistory);
        testStudent = new Student(12345, "name", "email", testTranscript);
        prereq = new Prerequisite(mockCourse, Grade.C);
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
        assertFalse(testStudent.hasStudentTakenCourse(mockCourse));
    }

    @Test
    public void test_getCourseGrade(){
        when(mockCourseHistory.containsKey(mockCourse)).thenReturn(true);
        when(mockCourseHistory.get(mockCourse)).thenReturn(Grade.B_MINUS);
        assertEquals(Grade.B_MINUS, testStudent.getCourseGrade(mockCourse));
    }
    @Test
    public void test_getCourseGradeNotTaken(){
        when(mockCourseHistory.containsKey(mockCourse)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> testStudent.getCourseGrade(mockCourse));
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
    @Test
    public void test_noGPA(){
        when(mockCourseHistory.isEmpty()).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> testStudent.getGPA());
    }

}
