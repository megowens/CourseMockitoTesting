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
        if(course.getEnrollmentCap() <= course.getCurrentEnrollmentSize()){
            return true;
        }
        return false;
    }

    @Override
    public boolean isWaitListFull(Course course) {
        return course.getWaitListCap() <= course.getCurrentWaitListSize();
    }

    @Override
    public Course.EnrollmentStatus getEnrollmentStatus(Course course) {
        return course.getEnrollmentStatus();
    }

    @Override
    public boolean areCoursesConflicted(Course first, Course second) {
        if (classOnSameDay(first, second)) {
            int firstStart = getCourseTime(first).get(0);
            int firstEnd = getCourseTime(first).get(1);
            int secondStart = getCourseTime(second).get(0);
            int secondEnd = getCourseTime(second).get(1);
            return conflictingTimes(firstStart, firstEnd, secondStart, secondEnd);

        }
        return false;
    }

    @Override
    public boolean hasConflictWithStudentSchedule(Course course, Student student) {
        List<Course> schedule = coursecatalog.getCoursesEnrolledIn(student);
        for(Course c : schedule) {
            if(areCoursesConflicted(c, course)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasStudentMeetsPrerequisites(Student student, List<Prerequisite> prerequisites) {
        for(Prerequisite prereq : prerequisites) {
            if(!student.meetsPrerequisite(prereq)){
                return false;
            }
        }
        return true;
    }

    @Override
    public RegistrationResult registerStudentForCourse(Student student, Course course) {
        if(!coursecatalog.getAllCourses().contains(course)){
            throw new IllegalArgumentException(course + " is not on the course list");
        }
        if(course.getEnrollmentStatus() == Course.EnrollmentStatus.CLOSED) {return RegistrationResult.COURSE_CLOSED;}
        if(isEnrollmentFull(course) && isWaitListFull(course)) {return RegistrationResult.COURSE_FULL;}
        if(!hasStudentMeetsPrerequisites(student, course.getPrerequisites())){return RegistrationResult.PREREQUISITE_NOT_MET;}
        if(hasConflictWithStudentSchedule(course, student)) {return RegistrationResult.SCHEDULE_CONFLICT;}

        if(!isEnrollmentFull(course)) {
            course.addStudentToEnrolled(student);
            if(isEnrollmentFull(course)) {
                course.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
            }
            return RegistrationResult.ENROLLED;
        }
        else if(!isWaitListFull(course)){
            course.addStudentToWaitList(student);
            if(isWaitListFull(course)){
                course.setEnrollmentStatus(Course.EnrollmentStatus.CLOSED);
            }
            return RegistrationResult.WAIT_LISTED;
        }
        return null;
    }

    @Override
    public void dropCourse(Student student, Course course) {
        if(!course.isStudentEnrolled(student) && !course.isStudentWaitListed(student)){
            throw new IllegalArgumentException("Student is neither enrolled in course nor on waitlist");
        }
        if(course.isStudentEnrolled(student)){
            course.removeStudentFromEnrolled(student);
            if(course.getEnrollmentStatus() == Course.EnrollmentStatus.WAIT_LIST) {
                if(course.isWaitListEmpty()) {
                    course.setEnrollmentStatus(Course.EnrollmentStatus.OPEN);
                }
                else {
                    course.addStudentToEnrolled(course.getFirstStudentOnWaitList());
                }
            }
            else if(course.getEnrollmentStatus() == Course.EnrollmentStatus.CLOSED){
                course.addStudentToEnrolled(course.getFirstStudentOnWaitList());
                course.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
            }
        }
        else if(course.isStudentWaitListed(student)){
            course.removeStudentFromWaitList(student);
            if(course.getEnrollmentStatus() == Course.EnrollmentStatus.CLOSED) {
                course.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
            }
        }
    }

    private ArrayList<Integer> getCourseTime(Course course) {
        int courseEndMin = course.getMeetingStartTimeMinute() + course.getMeetingDurationMinutes();
        int courseEndHour = course.getMeetingStartTimeHour();
        if (courseEndMin >= 60) {
            courseEndMin -= 60;
            courseEndHour++;
        }
        int courseStart = (course.getMeetingStartTimeHour()*100) + course.getMeetingStartTimeMinute();
        int courseEnd = (courseEndHour*100) + courseEndMin;
        ArrayList<Integer> CourseTimes = new ArrayList<Integer>();
        CourseTimes.add(courseStart);
        CourseTimes.add(courseEnd);
        return CourseTimes;
    }
    private boolean classOnSameDay(Course first, Course second){
        for(Object day : first.getMeetingDays()){
            for(Object secondDay : second.getMeetingDays()){
                if(day.equals(secondDay)){return true;}
            }
        }
        return false;
    }
    private boolean conflictingTimes(int firstStart, int firstEnd, int secondStart, int secondEnd){
        if ((firstStart <= secondStart && secondStart <= firstEnd)) {return true;}
        else if (secondStart <= firstStart && firstStart <= secondEnd) {return true;}
        else if (firstStart <= secondEnd && secondEnd <= firstEnd){return true;}
        else if (secondStart <= firstEnd && firstEnd <= secondEnd) {return true;}
        return false;
    }
}
