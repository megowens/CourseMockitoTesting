package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class part3Testing {
    private Course testCourse;
    private List<Student> mockEnrollment, mockWaitList;
    private Student mockStudent;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        testCourse = getTestCourse();
        mockEnrollment = (List<Student>) mock(List.class);
        mockWaitList = (List<Student>) mock(List.class);
        testCourse.setEnrolledStudents(mockEnrollment);
        testCourse.setWaitListedStudents(mockWaitList);

        mockStudent = mock(Student.class);
    }

    private Course getTestCourse() {
        return new Course(18802,
                "Software Development Essentials", "CS",
                3140, 1, 3, "Paul McBurney",
                "pm8fc@virginia.edu", 175, 99,
                List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                12, 30, 75,"Gilmer 301",
                new ArrayList<>());
    }


    @Test
    public void test_addCourseGrade(){

    }

    @Test
    public void test_hasStudentTakenCouse(){

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
