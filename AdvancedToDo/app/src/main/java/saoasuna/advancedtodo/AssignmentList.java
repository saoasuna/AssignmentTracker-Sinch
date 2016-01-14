package saoasuna.advancedtodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import saoasuna.advancedtodo.database.AssignmentCursorWrapper;
import saoasuna.advancedtodo.database.AssignmentDbSchema.AssignmentTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import saoasuna.advancedtodo.database.AssignmentDbHelper;

/**
 * Created by Ryan on 17/11/2015.
 */

// purpose: SINGLETON CLASS that is used to access the database
    // 'MODEL' of MVC that holds all the assignments
public class AssignmentList {
    private static AssignmentList sAssignmentList;

    private Context mContext; // needed to open the database
    private SQLiteDatabase mDatabase; // reference to the database

    public static AssignmentList get(Context context) { // method for getting a reference to the singleton
        if (sAssignmentList == null) {
            sAssignmentList=new AssignmentList(context);
        }
        return sAssignmentList;     //return reference to this singleton, so that its methods can be used
    }

    // CONSTRUCTOR
    private AssignmentList (Context context) {
        mContext = context.getApplicationContext(); // context stored for (currently unimplemented) uses involving intents
        mDatabase = new AssignmentDbHelper(mContext).getWritableDatabase(); // //use a sqliteopenhelper to open the database
    }


    // IMPORTANT METHODS

    // put the assignment's information in a storable format for the database
    public ContentValues getContentValues(Assignment assignment) {
        ContentValues values = new ContentValues();
        values.put(AssignmentTable.Cols.UUID, assignment.getUUID().toString());
        values.put(AssignmentTable.Cols.TITLE, assignment.getTitle());
        values.put(AssignmentTable.Cols.DETAILS, assignment.getDetails());
        values.put(AssignmentTable.Cols.REPEATS, assignment.getRepeats());
        values.put(AssignmentTable.Cols.DUEDATE, assignment.getDueDate().getTime());
        values.put(AssignmentTable.Cols.HOUR, assignment.getDueDate().getHours()); // deprecated method, will do for now
        values.put(AssignmentTable.Cols.MINUTE, assignment.getDueDate().getMinutes()); // deprecated method, will do for now
        return values;
    }

    // store the assignment into the database
    public void addAssignment (Assignment assignment) { // write an assignment to the database
        mDatabase.insert(AssignmentTable.NAME, null, getContentValues(assignment)); // store content values
        // into the database
    }

    public void removeAssignmentUsingUUID(UUID uuid) {
        mDatabase.delete(AssignmentTable.NAME, AssignmentTable.Cols.UUID + "=?", new String[]{uuid.toString()});
    }

    // get assignment with the corresponding id
    public Assignment getAssignment(UUID uuid) {
        // move the cursor to the correct row according to the UUID
        AssignmentCursorWrapper cursor =
                queryAssignments(AssignmentTable.Cols.UUID + " = ?", new String[] {uuid.toString()});
        try {
            if (cursor.getCount() == 0) { // if there was no assignment with that uuid
                return null;
            }
            cursor.moveToFirst(); // set cursor up at the beginning of the row
            return cursor.getAssignment();
        }
        finally {
            cursor.close();
        }

    }

    // get a list of all the assignments in the database
    public List<Assignment> getAssignments() { // to be made into AVL structure
        List<Assignment> assignments = new ArrayList<>();
        // setup a cursor, i'm guessing this just sets it up from the beginning of the table?
        AssignmentCursorWrapper cursor = queryAssignments(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) { // while there are more entries in the list, keep on adding them
                assignments.add(cursor.getAssignment());
                cursor.moveToNext(); // onto the next row
            }
        } finally {
            cursor.close();
        }
        return assignments;
    }

    private AssignmentCursorWrapper queryAssignments(String whereClaus, String[] whereArgs) {
        //return a cursor that points to the result set
        Cursor cursor = mDatabase.query(AssignmentTable.NAME, null, whereClaus, whereArgs, null, null, null);
        return new AssignmentCursorWrapper(cursor);
    }
}
