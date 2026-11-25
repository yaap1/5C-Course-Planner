import java.util.ArrayList;

/**
 * Represents a section of a course and stores its
 * professor, meeting times, and location.
 *
 * @author Ohemaa Premuh
 */
public class CourseSection{
    private String sectionNumber;
    private ArrayList<String> professors;
    private String meetingDays;
    private String startTime;
    private String endTime;
    private String location;

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
    public CourseSection(String sectionNumber,
                        ArrayList<String> professors,
                        String meetingDays, String startTime,
                        String endTime, String location) {

        this.sectionNumber = sectionNumber;
        this.professors = new ArrayList<>(professors);
        this.meetingDays = meetingDays;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }
}