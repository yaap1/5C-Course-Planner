import java.util.ArrayList;

/**
 * Represents a section of a course and stores its
 * professor, meeting times, and location.
 *
 * @author Ohemaa Premuh
 */
public class CourseSection{
    private String sectionNumber;
    private String meetingDays;
    private String startTime;
    private String endTime;
    private String location;
    private ArrayList<String> professors;

    /**
     * Constructs a CourseSection Object with the given section information.
     *
     * @param sectionNumber the section number
     * @param professors the professors teaching the section
     * @param meetingDays the days the section meets
     * @param startTime the starting time
     * @param endTime the ending time
     * @param location the location of the section
     */
    public CourseSection(String sectionNumber, ArrayList<String> professors, String meetingDays, String startTime, String endTime, String location) {

        this.sectionNumber = sectionNumber;
        this.professors = new ArrayList<>(professors);
        this.meetingDays = meetingDays;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    /**
     * Returns the section number.
     * 
     * @return sectionNumber
     */
    public String getSectionNumber(){
        return sectionNumber;
    }

    /**
     * Returns the meeting days for the course.
     * 
     * @return meetingDays
     */
    public String getMeetingDays(){
        return meetingDays;
    }

    /**
     * Returns the start time of the course.
     * 
     * @return startTime
     */
    public String getStartTime(){
        return startTime;
    }

    /**
     * Returns the end time of the course.
     * 
     * @return endTime
     */
    public String getEndTime(){
        return endTime;
    }

    /**
     * Returns the location of the course.
     * 
     * @return location
     */
    public String getLocation(){
        return location;
    }

    /**
     * Returns the professors who teach the course.
     * 
     * @return professors
     */
    public ArrayList<String> getProfessors(){
        return new ArrayList<>(professors); //a copy of the professors arrayList so the actual proffessors list cant be modified
    }

    @Override
    /**
     * Returns a string representation of the CourseSection object.
     * 
     * @return String version of the CourseSection object
     */
    public String toString(){
        return "Section: " + this.sectionNumber + "\n" + "Meeting days: " + this.meetingDays 
        + "\n" + "Start time: " + this.startTime + "\n" + "End time: " + this.endTime + "\n" 
        + "Location: " + this.location + "\n" + "Professors: " + this.professors;
    }
}