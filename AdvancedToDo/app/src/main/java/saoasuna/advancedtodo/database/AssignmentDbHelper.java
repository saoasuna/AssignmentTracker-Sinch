package saoasuna.advancedtodo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import saoasuna.advancedtodo.database.AssignmentDbSchema.AssignmentTable;

/**
 * Created by Ryan on 17/11/2015.
 */

// purpose: open database
public class AssignmentDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "assignmentDb.db";

    public AssignmentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // create our table in the database
        db.execSQL("create table " + AssignmentTable.NAME + "(" + " _id integer primary key autoincrement, " +
                AssignmentTable.Cols.UUID + ", " + AssignmentTable.Cols.TITLE + ", " + AssignmentTable.Cols.DETAILS +
                ", " + AssignmentTable.Cols.REPEATS + ", " + AssignmentTable.Cols.DUEDATE + ", " + AssignmentTable.Cols.HOUR +
                ", " + AssignmentTable.Cols.MINUTE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
