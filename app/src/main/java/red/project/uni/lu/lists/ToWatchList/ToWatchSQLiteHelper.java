package red.project.uni.lu.lists.ToWatchList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToWatchSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_TO_WATCH = "to_watch";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PREVIEW_URL = "preview_url";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_DATE_OF_RELEASE = "date_of_release";
    public static final String COLUMN_DATE_TO_WATCH = "date_to_watch";

    public static final String COLUMN_MOVIE_ID = "movie_id";

    private static final String DATABASE_NAME = "to_watch.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_TO_WATCH + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_PREVIEW_URL + " text not null, "
            + COLUMN_NOTES + " text not null, "
            + COLUMN_DATE_OF_RELEASE + " text not null, "
            + COLUMN_DATE_TO_WATCH + " text not null, "
            // i do not put the movie id as primary key because i prefer to have my own system of id.
            + COLUMN_MOVIE_ID + " integer not null);";


    public ToWatchSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            // Drop older table if existed and create fresh table
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TO_WATCH);
            onCreate(sqLiteDatabase);
    }
}
