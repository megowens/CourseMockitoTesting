package edu.virginia.cs.hw4;

import java.util.ArrayList;
import java.util.List;

public class RegistrationImpl implements Registration {
    //TODO: Implement class
    CourseCatalog coursecatalog = new CourseCatalog();

    @Override
    public CourseCatalog getCourseCatalog() {
        return coursecatalog;
    }

    @Override
    public void setCourseCatalog(CourseCatalog courseCatalog) {
        this.coursecatalog = courseCatalog;
    }

    @Override
    public boolean isEnrollmentFull(Course course) {
        return course.getEnrollmentCap() < course.getCurrentEnrollmentSize();
    }

    @Override
    public boolean isWaitListFull(Course course) {
        return course.getWaitListCap() < course.getCurrentWaitListSize();
    }

    @Override
    public Course.EnrollmentStatus getEnrollmentStatus(Course course) {
        return course.getEnrollmentStatus();
    }

    private ArrayList<Integer> getCourseTime(Course course) {
        int courseEndMin = course.getMeetingStartTimeMinute() + course.getMeetingDurationMinutes();
        int courseEndHour = course.getMeetingStartTimeHour();
        if (courseEndMin >= 60) {
            courseEndMin -= 60;
            courseEndHour++;
        }
        int courseStart = course.getMeetingStartTimeHour() + course.getMeetingStartTimeMinute();
        int courseEnd = courseEndHour + courseEndMin;
        ArrayList<Integer> CourseTimes = new ArrayList<Integer>();
        CourseTimes.add(courseStart);
        CourseTimes.add(courseEnd);
        return CourseTimes;
    }

    @Override
    public boolean areCoursesConflicted(Course first, Course second) {
        if (first.getMeetingDays() == second.getMeetingDays()) {
            int firstStart = getCourseTime(first).get(0);
            int firstEnd = getCourseTime(first).get(1);
            int secondStart = getCourseTime(second).get(0);
            int secondEnd = getCourseTime(second).get(1);
            if (firstStart < secondStart && secondStart < firstEnd) {
                return true;
            }
            else if (secondStart < firstStart && firstStart < secondEnd) {
                return true;
            }
            else if (firstStart < secondEnd && secondEnd < firstEnd){
                return true;
            }
            else if (secondStart < firstEnd && firstEnd < secondEnd) {
                return true;
            }
            else {return false;}
        }
        return false;
    }

    @Override
    public boolean hasConflictWithStudentSchedule(Course course, Student student) {
        return false;
    }

    @Override
    public boolean hasStudentMeetsPrerequisites(Student student, List<Prerequisite> prerequisites) {
        return false;
    }

    @Override
    public RegistrationResult registerStudentForCourse(Student student, Course course) {
        return null;
    }

    @Override
    public void dropCourse(Student student, Course course) {

    }
}
