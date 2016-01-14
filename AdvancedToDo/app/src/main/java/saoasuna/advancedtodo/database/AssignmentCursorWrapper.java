package saoasuna.advancedtodo.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import saoasuna.advancedtodo.Assignment;
import saoasuna.advancedtodo.database.AssignmentDbSchema.AssignmentTable;
/**
 * Created by Ryan on 17/11/2015.
 */
// purpose: easily get information from database
// CURSOR WRAPPER FOR extending a cursor while overriding only a subset of its methods
public class AssignmentCursorWrapper extends CursorWrapper {
    public AssignmentCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Assignment getAssignment() { // returns an assignment
    // will be called when the assignmentlist class already
        // moves the cursor to the appropriate place
        String uuid = getString(getColumnIndex(AssignmentTable.Cols.UUID));
        String title = getString(getColumnIndex(AssignmentTable.Cols.TITLE));
        String details = getString(getColumnIndex(AssignmentTable.Cols.DETAILS));
        int repeats = getInt(getColumnIndex(AssignmentTable.Cols.REPEATS));
        long date = getLong(getColumnIndex(AssignmentTable.Cols.DUEDATE));
        int hour = getInt(getColumnIndex(AssignmentTable.Cols.HOUR));
        int min = getInt(getColumnIndex(AssignmentTable.Cols.MINUTE));

        // instantiate new assignment based on the column entries
        Assignment assignment = new Assignment(UUID.fromString(uuid), title, details, repeats, new Date(date));

        return assignment;
    }
}
