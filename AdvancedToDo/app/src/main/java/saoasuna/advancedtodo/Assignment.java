package saoasuna.advancedtodo;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Ryan on 17/11/2015.
 */
// purpose: main data object, holds important information about an "assignment"
public class Assignment {

    private UUID mUUID; // identification ID
    private String mTitle;  // title given by user
    private String mDetails;    // details given by user
    private int mRepeats; // the assignment will update every 'mRepeats' hours; 0 represents no repeat
    private Date mDueDate; // due date of the assignment
    private int mHourOfDay; // hour of day that the assignment is due
    private int mMinuteOfDay;   // minute of hour that the assignment is due

    // CONSTRUCTORS
    public Assignment() {       // generate an empty assignment
        this(UUID.randomUUID());
    }

    public Assignment(UUID uuid) {  // generate an empty assignment using the given UUID
        mUUID = uuid;
    }

    public Assignment(UUID uuid, String title, String details, int repeats, Date dueDate) { // generate assignment using fields
        mUUID = uuid;
        mTitle = title;
        mDetails = details;
        mRepeats = repeats;
        mDueDate = dueDate;
    }

    public Assignment(String title, String details, int repeats, Date dueDate) { // generate assignment using fields
        mUUID = UUID.randomUUID();
        mTitle = title;
        mDetails = details;
        mRepeats = repeats;
        mDueDate = dueDate;
    }

    public Assignment(String title, String details, int repeats, Date dueDate, int hour, int minute) { // generate assignment using fields
        mUUID = UUID.randomUUID();
        mTitle = title;
        mDetails = details;
        mRepeats = repeats;
        mDueDate = dueDate;
        mHourOfDay = hour;
        mMinuteOfDay = minute;
    }

    //GETTERS AND SETTERS

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public int getRepeats() {
        return mRepeats;
    }

    public void setRepeats(int repeats) {
        mRepeats = repeats;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public long getAssignmentTime() {
        return mDueDate.getTime();
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void updateDate(Date currentDate) {

    }

}
