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
    private Student mockStudent2;
    private RegistrationImpl registration = new RegistrationImpl();
    private Prerequisite prereq = new Prerequisite(mockCourse, Grade.C);
    private Registration mockReg;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        mockCourse = mock(Course.class);
        mockCourse2 = mock(Course.class);
        mockStudent = mock(Student.class);
        mockStudent2 = mock(Student.class);
        mockReg = mock(Registration.class);
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
    //Start times are the same, same day
    public void test_areCoursesConflictedDiffStart(){
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(13);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(75);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(12);
        assertTrue(registration.areCoursesConflicted(mockCourse,mockCourse2));
    }
    @Test
    //Test to see if lists are working and
    public void test_areCoursesConflictedOneDaySame_StartSame(){
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY,DayOfWeek.TUESDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.TUESDAY));
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(75);
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(12);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(75);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(12);
        assertTrue(registration.areCoursesConflicted(mockCourse,mockCourse2));
    }
    @Test
    public void test_areCoursesNotConflictedDiffDays(){
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY,DayOfWeek.WEDNESDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.TUESDAY,DayOfWeek.THURSDAY));
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(75);
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(12);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(75);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(12);
        assertFalse(registration.areCoursesConflicted(mockCourse,mockCourse2));
    }
    @Test
    public void test_areCoursesNotConflictedDiffTimes(){
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(75);
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(8);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(10);
        assertFalse(registration.areCoursesConflicted(mockCourse,mockCourse2));
    }
    @Test
    public void test_hasConflictWithStudentSchedule(){

    }
    Prerequisite prereq2 = new Prerequisite(mockCourse2, Grade.C);
    List<Prerequisite> prereq_list = new ArrayList<>();
    public List<Prerequisite> getPrereq_list(List<Prerequisite> plist) {
        plist.add(prereq);
        plist.add(prereq2);
        return plist;
    }
    List<Prerequisite> testPreList = getPrereq_list(prereq_list);
    @Test
    public void test_hasStudentMeetsPrerequisites(){
        when(mockStudent.meetsPrerequisite(prereq)).thenReturn(true);
        when(mockStudent.meetsPrerequisite(prereq2)).thenReturn(true);
        assertTrue(registration.hasStudentMeetsPrerequisites(mockStudent, testPreList));
    }
    @Test
    public void test_registerStudentForCourse_courseClosed(){
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
        assertEquals(registration.registerStudentForCourse(mockStudent, mockCourse), RegistrationResult.COURSE_CLOSED);
    }

    @Test
    public void test_registerStudentForCourse_courseFull(){
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.OPEN);

        when(mockCourse.getEnrollmentCap()).thenReturn(100);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(100);

        when(mockCourse.getWaitListCap()).thenReturn(100);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(100);

        assertEquals(registration.registerStudentForCourse(mockStudent, mockCourse), RegistrationResult.COURSE_FULL);
    }


    @Test
    public void test_registerStudentForCourse_ScheduleConflict(){
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.OPEN);
        when(mockCourse.getEnrollmentCap()).thenReturn(50);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(50);
        when(mockCourse.getWaitListCap()).thenReturn(50);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(0);

        when(mockReg.hasConflictWithStudentSchedule(mockCourse, mockStudent)).thenReturn(true);

        assertEquals(RegistrationResult.SCHEDULE_CONFLICT, registration.registerStudentForCourse(mockStudent, mockCourse));

    }

    @Test
    public void dropCourse(){

    }
}
