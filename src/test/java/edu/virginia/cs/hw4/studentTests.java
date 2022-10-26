package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.Null;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Got help from Celeste's office hours for test_addCourseGrade, line 35
// Got help from Leigh's office hours for test_meetsPrerequisite and test_getGPA, lines 69 & 91
// Got help from Santiago's office hours for test_getCourseGrade, line 55

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
        when(mockCourseHistory.keySet()).thenReturn(Set.of(mockCourse));
        when(mockCourse.getDepartment()).thenReturn("CS");
        when(mockCourse.getCatalogNumber()).thenReturn(3140);
        assertTrue(testStudent.hasStudentTakenCourse(mockCourse));
    }
    @Test
    public void test_hasStudentTakenCourseFalse(){
        when(mockCourseHistory.keySet()).thenReturn(Set.of());
        assertFalse(testStudent.hasStudentTakenCourse(mockCourse));
    }

    @Test
    public void test_getCourseGrade(){
        when(mockCourseHistory.keySet()).thenReturn(Set.of(mockCourse));
        when(mockCourse.getDepartment()).thenReturn("CS");
        when(mockCourse.getCatalogNumber()).thenReturn(3140);
        when(mockCourseHistory.get(mockCourse)).thenReturn(Grade.B_MINUS);
        assertEquals(Grade.B_MINUS, testStudent.getCourseGrade(mockCourse));
    }
    @Test
    public void test_getCourseGradeException(){
        when(mockCourseHistory.containsKey(mockCourse)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> testStudent.getCourseGrade(mockCourse));
    }

    @Test
    public void test_meetsPrerequisite(){
        when(mockCourseHistory.keySet()).thenReturn(Set.of(prereq.course));
        when(prereq.course.getDepartment()).thenReturn("CS");
        when(prereq.course.getCatalogNumber()).thenReturn(3140);
        when(mockCourseHistory.get(prereq.course)).thenReturn(Grade.B_MINUS);
        assertTrue(testStudent.meetsPrerequisite(prereq));
    }
    @Test
    public void test_didntTakePrerequisite(){
        when(mockCourseHistory.keySet()).thenReturn(Set.of());
        assertFalse(testStudent.meetsPrerequisite(prereq));
    }
    @Test
    public void test_doesntMeetPrerequisite(){
        when(mockCourseHistory.keySet()).thenReturn(Set.of(prereq.course));
        when(prereq.course.getDepartment()).thenReturn("CS");
        when(prereq.course.getCatalogNumber()).thenReturn(3140);
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
    public void test_GPAException(){
        when(mockCourseHistory.isEmpty()).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> testStudent.getGPA());
    }

}
