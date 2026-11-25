import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Loads distinct CS courses from the supplied Fall 2026 schedule CSV.
 * Uses an ArrayList and linear searches so it needs no HashMap or libraries.
 * CourseSection and the full schedule importer remain separate team tasks.
 *
 * @author Andrey Motchenko
 */
public class CourseCatalog {
    private ArrayList<Course> courses;

    /**
     * Reads the CSV and creates one Course per distinct CS course code.
     * Repeated sections, rooms, and instructors do not create extra courses.
     *
     * @param filename the path to fall_2026_course_schedule.csv
     * @throws IOException if the file cannot be read or its header is unexpected
     * @throws IllegalArgumentException if a CSV row has unmatched quotation marks
     */
    public CourseCatalog(String filename) throws IOException {
        courses = new ArrayList<>();

        try (Scanner input = new Scanner(new File(filename), "UTF-8")) {
            if (!input.hasNextLine()) {
                throw new IOException("The CSV file is empty.");
            }

            // Remove an optional UTF-8 byte-order mark before reading the header.
            String headerLine = input.nextLine();
            if (headerLine.startsWith("\uFEFF")) {
                headerLine = headerLine.substring(1);
            }
            ArrayList<String> header = parseCsvLine(headerLine);
            if (header.size() < 4
                    || !header.get(0).equals("course_number")
                    || !header.get(1).equals("course_title")
                    || !header.get(2).equals("department")
                    || !header.get(3).equals("college")) {
                throw new IOException("Expected the supplied course schedule CSV header.");
            }

            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (!line.trim().isEmpty()) {
                    ArrayList<String> fields = parseCsvLine(line);
                    if (fields.size() != header.size()) {
                        throw new IOException("Unexpected number of columns in CSV row.");
                    }

                    String code = fields.get(0).trim();
                    String title = fields.get(1).trim();
                    String department = fields.get(2).trim();
                    String college = fields.get(3).trim();

                    if (department.equals("CSCI") && findCourse(code) == null) {
                        // The CSV has neither descriptions nor prerequisite lists.
                        // Empty values mean unknown here, not 'no prerequisites'.
                        Course course = new Course(code, title, department, college,
                                                   "", new ArrayList<String>());
                        courses.add(course);
                    }
                }
            }

            if (input.ioException() != null) {
                throw input.ioException();
            }
        }
    }

    /**
     * Finds a course by its exact CSV code using a linear search.
     * Spaces and college suffixes are part of the code and are preserved.
     *
     * @param code a code such as CSCI140 PO or CSCI181AGPO
     * @return the matching Course, or null if it is absent
     */
    private Course findCourse(String code) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    /**
     * Gets a course needed by a track, reporting missing data explicitly.
     *
     * @param code the exact course code in the CSV
     * @return the existing Course object from this catalog
     * @throws IllegalArgumentException if the CSV does not contain that course
     */
    public Course getCourse(String code) {
        Course course = findCourse(code);
        if (course == null) {
            throw new IllegalArgumentException("Course not found in the CSV: " + code);
        }
        return course;
    }

    /**
     * Returns the number of distinct CS course codes read from the schedule.
     *
     * @return the catalog size, including lab and research course codes
     */
    public int size() {
        return courses.size();
    }

    /**
     * Splits one CSV row while keeping quoted commas and escaped quotes intact.
     * The supplied CSV has one complete record per line. Multiline quoted fields
     * are deliberately unsupported and produce an error rather than wrong data.
     *
     * @param line one complete line from the CSV file
     * @return the fields in their original column order
     * @throws IllegalArgumentException if quotation marks do not close
     */
    private ArrayList<String> parseCsvLine(String line) {
        ArrayList<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);
            if (character == '"') {
                if (insideQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    field.append('"');
                    i++; // Two quotes inside a quoted field represent one quote.
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (character == ',' && !insideQuotes) {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(character);
            }
        }

        if (insideQuotes) {
            throw new IllegalArgumentException("Unclosed quoted field in CSV row.");
        }
        fields.add(field.toString()); // Also preserves an empty final column.
        return fields;
    }
}
