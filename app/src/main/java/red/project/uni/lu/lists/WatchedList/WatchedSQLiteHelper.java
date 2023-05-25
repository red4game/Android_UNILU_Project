package red.project.uni.lu.lists.WatchedList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WatchedSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_WATCHED = "watched";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PREVIEW_URL = "preview_url";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_DATE_OF_RELEASE = "date_of_release";
    public static final String COLUMN_DATE_WATCHED = "date_watched";

    public static final String COLUMN_DATE_ADDED = "date_added";
    public static final String COLUMN_RATING = "rating";



    private static final String DATABASE_NAME = "watched.db";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_WATCHED + "(" + COLUMN_MOVIE_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_PREVIEW_URL + " text not null, "
            + COLUMN_NOTES + " text not null, "
            + COLUMN_DATE_OF_RELEASE + " text not null, "
            + COLUMN_DATE_WATCHED + " text not null, "
            + COLUMN_DATE_ADDED + " text not null, "
            + COLUMN_RATING + " float not null check(" + COLUMN_RATING + " >= 0 and " + COLUMN_RATING + " <= 10));";


    public WatchedSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed and create fresh table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCHED);
        onCreate(sqLiteDatabase);
    }
}
