package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegTests {
    private Course mockCourse, mockCourse2;
    private Student mockStudent;
    private RegistrationImpl registration = new RegistrationImpl();

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        mockCourse = mock(Course.class);
        mockCourse2 = mock(Course.class);
        mockStudent = mock(Student.class);
    }
    @Test
    public void test_isEnrollmentFull(){
        when(mockCourse.getEnrollmentCap()).thenReturn(10);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(10);
        assertTrue(registration.isEnrollmentFull(mockCourse));
    }
    @Test
    public void test_isEnrollmentFullOver(){
        when(mockCourse.getEnrollmentCap()).thenReturn(10);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(15);
        assertTrue(registration.isEnrollmentFull(mockCourse));
    }
    @Test
    public void test_isEnrollmentNotFull(){
        when(mockCourse.getEnrollmentCap()).thenReturn(10);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(9);
        assertFalse(registration.isEnrollmentFull(mockCourse));
    }
    @Test
    public void test_isWaitListFull(){
        when(mockCourse.getWaitListCap()).thenReturn(10);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(10);
        assertTrue(registration.isWaitListFull(mockCourse));
    }
    @Test
    public void test_isWaitListFullOver(){
        when(mockCourse.getWaitListCap()).thenReturn(10);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(15);
        assertTrue(registration.isWaitListFull(mockCourse));
    }
    @Test
    public void test_isWaitListNotFull(){
        when(mockCourse.getWaitListCap()).thenReturn(10);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(9);
        assertFalse(registration.isWaitListFull(mockCourse));
    }
    @Test
    public void test_areCoursesConflicted(){
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(60);
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(12);
        assertTrue(registration.areCoursesConflicted(mockCourse,mockCourse2));
    }
    @Test
    public void test_hasConflictWithStudentSchedule(){

    }
    @Test
    public void test_hasStudentMeetsPrerequisites(){

    }
    @Test
    public void test_registerStudentForCourse(){

    }
    @Test
    public void dropCourse(){

    }
}
