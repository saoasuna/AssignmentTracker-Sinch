package saoasuna.advancedtodo.database;

/**
 * Created by Ryan on 17/11/2015.
 */
// purpose: define format of database
// describes the format of the table for storing data
public class AssignmentDbSchema {
    public static final class AssignmentTable {
        public static final String NAME = "assignments"; // name of the table in our database

        public static final class Cols { // columns of our table
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DETAILS = "details";
            public static final String REPEATS = "repeats";
            public static final String DUEDATE = "duedate";
            public static final String HOUR = "hour";
            public static final String MINUTE  = "minute";
        }
    }
}
