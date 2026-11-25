import java.util.ArrayList;

/**
 * Stores a major's name, shared foundation courses, and suggested study tracks.
 * The tracks are project-created themes, not official degree requirements.
 *
 * @author Andrey Motchenko
 */
public class Major {
    private String name;
    private ArrayList<Course> foundationCourses;
    private ArrayList<String> trackNames;
    private ArrayList<String> trackDescriptions;

    // Matching indexes connect a track's name, description, and course list.
    // Each inner ArrayList contains the courses for exactly one track.
    private ArrayList<ArrayList<Course>> courseTracks;
    private int selectedTrackIndex;

    /**
     * Creates a major with no courses or tracks and no selected track.
     *
     * @param name the name displayed in the major menu
     */
    public Major(String name) {
        this.name = name;
        foundationCourses = new ArrayList<>();
        trackNames = new ArrayList<>();
        trackDescriptions = new ArrayList<>();
        courseTracks = new ArrayList<>();
        selectedTrackIndex = -1;
    }

    /**
     * Adds a course to the shared foundation, avoiding repeated course codes.
     *
     * @param course the course to add
     */
    public void addFoundationCourse(Course course) {
        for (Course existing : foundationCourses) {
            if (existing.getCourseCode().equals(course.getCourseCode())) {
                return;
            }
        }
        foundationCourses.add(course);
    }

    /**
     * Adds a named track and copies its course list into the nested ArrayList.
     * Courses are suggestions, not a verified prerequisite sequence.
     *
     * @param trackName the name of the track
     * @param description a short explanation of the track
     * @param courses the courses belonging to the track
     */
    public void addTrack(String trackName, String description,
                         ArrayList<Course> courses) {
        trackNames.add(trackName);
        trackDescriptions.add(description);
        courseTracks.add(new ArrayList<>(courses));
    }

    /**
     * Returns the major's name.
     *
     * @return the name displayed in the major menu
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a copy of the shared foundation list.
     *
     * @return foundation courses without exposing the internal list
     */
    public ArrayList<Course> getFoundationCourses() {
        return new ArrayList<>(foundationCourses);
    }

    /**
     * Returns the number of tracks available for this major.
     *
     * @return the number of inner lists in courseTracks
     */
    public int getTrackCount() {
        return courseTracks.size();
    }

    /**
     * Returns a track's name using its zero-based list index.
     *
     * @param index the track index, from zero through getTrackCount() - 1
     * @return the track name
     */
    public String getTrackName(int index) {
        return trackNames.get(index);
    }

    /**
     * Returns the description of a track using its zero-based list index.
     *
     * @param index the track index
     * @return the track description
     */
    public String getTrackDescription(int index) {
        return trackDescriptions.get(index);
    }

    /**
     * Returns a copy of one track's course list.
     *
     * @param index the track index
     * @return the suggested courses in that track
     */
    public ArrayList<Course> getTrackCourses(int index) {
        return new ArrayList<>(courseTracks.get(index));
    }

    /**
     * Saves the selected track for this major during the current program run.
     *
     * @param index the zero-based index of the selected track
     * @throws IllegalArgumentException if the track index does not exist
     */
    public void selectTrack(int index) {
        if (index < 0 || index >= courseTracks.size()) {
            throw new IllegalArgumentException("Invalid track index: " + index);
        }
        selectedTrackIndex = index;
    }

    /**
     * Returns the current track selection, or -1 if no track has been selected.
     *
     * @return the selected track's zero-based index
     */
    public int getSelectedTrackIndex() {
        return selectedTrackIndex;
    }
}
