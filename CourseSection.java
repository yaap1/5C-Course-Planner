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
     * 
     * @return sectionNumber
     */
    public String getSectionNumber(){
        return sectionNumber;
    }

    /**
     * 
     * @return meetingDays
     */
    public String getMeetingDays(){
        return meetingDays;
    }

    /**
     * 
     * @return startTime
     */
    public String getStartTime(){
        return startTime;
    }

    /**
     * 
     * @return endTime
     */
    public String getEndTime(){
        return endTime;
    }

    /**
     * 
     * @return location
     */
    public String getLocation(){
        return location;
    }

    /**
     * 
     * @return professors
     */
    public ArrayList<String> getProfessors(){
        return new ArrayList<>(professors); //a copy of the professors arrayList so the actual proffessors list cant be modified
    }
}