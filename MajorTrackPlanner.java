import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs the Status Report 1 prototype: choose a major, view tracks, select a track.
 * Only Computer Science is implemented. Courses come from the uploaded CSV.
 * The five themes are suggested study areas, not official major tracks.
 *
 * @author Andrey Motchenko
 */
public class MajorTrackPlanner {

    /**
     * Loads the CSV and runs the major and track menus until the user exits.
     *
     * @param args optional CSV filename; otherwise uses fall_2026_course_schedule.csv
     */
    public static void main(String[] args) {
        String filename = "fall_2026_course_schedule.csv";
        if (args.length > 0) {
            filename = args[0];
        }

        try {
            CourseCatalog catalog = new CourseCatalog(filename);
            ArrayList<Major> majors = new ArrayList<>();
            majors.add(createComputerScienceMajor(catalog));

            System.out.println("5C Course Planner - Status Report 1 Prototype");
            System.out.println("Loaded " + catalog.size() + " distinct CS course codes.");
            System.out.println("Source: Fall 2026 schedule, not a future course catalog.");
            System.out.println("Tracks are suggestions, not official degree requirements.");
            System.out.println("Prerequisites, progress, and time conflicts are not checked yet.");

            Scanner keyboard = new Scanner(System.in);
            boolean running = true;
            while (running) {
                System.out.println("\nChoose a major (CS only in this prototype):");
                for (int i = 0; i < majors.size(); i++) {
                    System.out.println((i + 1) + ". " + majors.get(i).getName());
                }
                System.out.println("0. Exit");

                int choice = readChoice(keyboard, majors.size());
                if (choice == 0) {
                    running = false;
                } else {
                    // Menu numbers start at 1, but ArrayList indexes start at 0.
                    showTrackMenu(keyboard, majors.get(choice - 1));
                }
            }
            System.out.println("Goodbye!");
        } catch (IOException exception) {
            System.out.println("Could not load the CSV: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("Could not build the tracks: " + exception.getMessage());
        }
    }

    /**
     * Builds five project-created CS themes using exact course codes in the CSV.
     * Shared foundation examples are stored once, separately from the tracks.
     * List order is for display; it does not assert prerequisite relationships.
     *
     * @param catalog the distinct CS courses read from the supplied schedule
     * @return the Computer Science major and its suggested tracks
     */
    public static Major createComputerScienceMajor(CourseCatalog catalog) {
        Major major = new Major("Computer Science");
        major.addFoundationCourse(catalog.getCourse("CSCI051 PO"));
        major.addFoundationCourse(catalog.getCourse("CSCI054 PO"));
        major.addFoundationCourse(catalog.getCourse("CSCI062 PO"));
        major.addFoundationCourse(catalog.getCourse("CSCI101 PO"));
        major.addFoundationCourse(catalog.getCourse("CSCI105 PO"));
        major.addFoundationCourse(catalog.getCourse("CSCI140 PO"));

        ArrayList<Course> software = new ArrayList<>();
        software.add(catalog.getCourse("CSCI123 HM"));
        software.add(catalog.getCourse("CSCI131 HM"));
        software.add(catalog.getCourse("CSCI133 HM"));
        software.add(catalog.getCourse("CSCI134 PO"));
        major.addTrack("Software Engineering and Systems",
                "Explore software projects, programming languages, databases, and operating systems.",
                software);

        ArrayList<Course> artificialIntelligence = new ArrayList<>();
        artificialIntelligence.add(catalog.getCourse("CSCI158 PZ"));
        artificialIntelligence.add(catalog.getCourse("CSCI152 HM"));
        artificialIntelligence.add(catalog.getCourse("CSCI159 HM"));
        major.addTrack("Artificial Intelligence and Machine Learning",
                "Explore machine learning, neural networks, and natural language processing.",
                artificialIntelligence);

        ArrayList<Course> data = new ArrayList<>();
        data.add(catalog.getCourse("CSCI036 CM"));
        data.add(catalog.getCourse("CSCI133 HM"));
        data.add(catalog.getCourse("CSCI143 CM"));
        data.add(catalog.getCourse("CSCI145 CM"));
        major.addTrack("Data Science and Databases",
                "Explore data science foundations, database systems, big data, and data mining.",
                data);

        ArrayList<Course> theory = new ArrayList<>();
        theory.add(catalog.getCourse("CSCI181AFHM"));
        theory.add(catalog.getCourse("CSCI181AGPO"));
        major.addTrack("Theory and Advanced Algorithms",
                "Explore advanced data structures and algorithmic game theory.",
                theory);

        ArrayList<Course> humanCentered = new ArrayList<>();
        humanCentered.add(catalog.getCourse("CSCI120 HM"));
        humanCentered.add(catalog.getCourse("CSCI181ACHM"));
        humanCentered.add(catalog.getCourse("CSCI181E PO"));
        major.addTrack("Human-Centered and Responsible Computing",
                "Explore human-centered methods, experiments in human vision, and software ethics.",
                humanCentered);

        return major;
    }

    /**
     * Displays a major's tracks, saves each selection, and prints its courses.
     * Entering zero returns to the major menu.
     *
     * @param keyboard the existing console-input Scanner
     * @param major the major selected by the user
     */
    private static void showTrackMenu(Scanner keyboard, Major major) {
        boolean choosing = true;
        while (choosing) {
            System.out.println("\nSuggested tracks for " + major.getName() + ":");
            for (int i = 0; i < major.getTrackCount(); i++) {
                System.out.println((i + 1) + ". " + major.getTrackName(i));
            }
            System.out.println("0. Back to majors");

            int choice = readChoice(keyboard, major.getTrackCount());
            if (choice == 0) {
                choosing = false;
            } else {
                major.selectTrack(choice - 1);
                int selected = major.getSelectedTrackIndex();
                System.out.println("\nSelected track: " + major.getTrackName(selected));
                System.out.println(major.getTrackDescription(selected));
                System.out.println("\nShared foundation examples:");
                printCourses(major.getFoundationCourses());
                System.out.println("\nTrack-specific course suggestions:");
                printCourses(major.getTrackCourses(selected));
                System.out.println("These lists are not a complete degree checklist or prerequisite sequence.");
                System.out.println("Check required labs, permissions, and prerequisites before enrolling.");
            }
        }
    }

    /**
     * Prints course codes and titles from the existing Course objects.
     *
     * @param courses the courses to display
     */
    private static void printCourses(ArrayList<Course> courses) {
        for (Course course : courses) {
            System.out.println("  " + course.getCourseCode() + " - " + course.getCourseName());
        }
    }

    /**
     * Reads a menu number, repeating the prompt for invalid input.
     * End-of-input behaves like choosing zero, allowing redirected input to end.
     *
     * @param keyboard the existing console-input Scanner
     * @param maximum the largest accepted menu number
     * @return a valid number from zero through maximum
     */
    private static int readChoice(Scanner keyboard, int maximum) {
        while (true) {
            System.out.print("Enter your choice: ");
            if (!keyboard.hasNextLine()) {
                return 0;
            }

            String answer = keyboard.nextLine().trim();
            try {
                int choice = Integer.parseInt(answer);
                if (choice >= 0 && choice <= maximum) {
                    return choice;
                }
            } catch (NumberFormatException exception) {
                // A word, decimal, or oversized integer is not a valid menu number.
            }
            System.out.println("Please enter a whole number from 0 to " + maximum + ".");
        }
    }
}
