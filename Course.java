import java.util.ArrayList;

/**
 * Represents a course at one of the Claremont Colleges
 * and stores its relevant information.
 * @author Ohemaa Yaa Premuh
 */

public class Course {
    private String courseCode;
    private String courseName;
    private String department;
    private String college;
    private String professor;
    private String description; //maybe?
    private ArrayList<String> prerequisites;

    public Course(String courseCode, String courseName, String department, String college, String professor, String description, ArrayList<String> prerequisites){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.department = department;
        this.college = college;
        this.professor = professor;
        this.description = description;
        this.prerequisites = prerequisites;
    }
}