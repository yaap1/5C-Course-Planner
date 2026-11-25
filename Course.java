import java.util.ArrayList;

/**
 * Represents a course at one of the Claremont Colleges
 * and stores its relevant information.
 * 
 * @author Ohemaa Yaa Premuh
 */

public class Course {
    private String courseCode;
    private String courseName;
    private String department;
    private String college;
    //private ArrayList<CourseSection> sections; //class needs to be completed
    private String description; //maybe?
    private ArrayList<String> prerequisites;

    /**
     * Constructs a Course object with the given course information.
     *
     * @param courseCode the code of the course
     * @param courseName the name of the course
     * @param department the department offering the course
     * @param college the college offering the course
     * @param description the description of the course
     * @param prerequisites the list of required prerequisite courses
     */
    public Course(String courseCode, String courseName, String department, String college, String description, ArrayList<String> prerequisites){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.department = department;
        this.college = college;
        this.description = description;
        this.prerequisites = prerequisites;
        //this.sections = new ArrayList<>();
    }
}