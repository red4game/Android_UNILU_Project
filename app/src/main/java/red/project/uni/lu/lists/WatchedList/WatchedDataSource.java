package red.project.uni.lu.lists.WatchedList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class WatchedDataSource {

    private WatchedSQLiteHelper dbHelper;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SQLiteDatabase database;
    private String[] allColumns = {WatchedSQLiteHelper.COLUMN_MOVIE_ID, WatchedSQLiteHelper.COLUMN_TITLE, WatchedSQLiteHelper.COLUMN_PREVIEW_URL, WatchedSQLiteHelper.COLUMN_NOTES, WatchedSQLiteHelper.COLUMN_DATE_OF_RELEASE, WatchedSQLiteHelper.COLUMN_DATE_WATCHED, WatchedSQLiteHelper.COLUMN_DATE_ADDED, WatchedSQLiteHelper.COLUMN_RATING};

    public WatchedDataSource(Context context) {
        dbHelper = new WatchedSQLiteHelper(context);
    }

    private WatchedItem cursorToWatchedItem(android.database.Cursor cursor) {
        WatchedItem WatchedItem = new WatchedItem();
        WatchedItem.setMovieID(cursor.getInt(0));
        WatchedItem.setTitle(cursor.getString(1));
        WatchedItem.setPreviewUrl(cursor.getString(2));
        WatchedItem.setNotes(cursor.getString(3));
        WatchedItem.setDateOfRelease(cursor.getString(4));
        WatchedItem.setDateWatched(cursor.getString(5));
        WatchedItem.setDateAdded(cursor.getString(6));
        WatchedItem.setRating(cursor.getInt(7));
        return WatchedItem;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public WatchedItem createWatchedItem(String title, String previewUrl, String notes, String dateOfRelease, String dateWatched, int movieId, int rating) {
        android.database.Cursor cursor = database.query(WatchedSQLiteHelper.TABLE_WATCHED, allColumns, WatchedSQLiteHelper.COLUMN_MOVIE_ID + " = " + movieId, null, null, null, null);
        cursor.moveToFirst();
        WatchedItem newWatchedItem = null;
        if (cursor.getCount() == 0) {
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(WatchedSQLiteHelper.COLUMN_TITLE, title);
            values.put(WatchedSQLiteHelper.COLUMN_PREVIEW_URL, previewUrl);
            values.put(WatchedSQLiteHelper.COLUMN_NOTES, notes);
            values.put(WatchedSQLiteHelper.COLUMN_DATE_OF_RELEASE, dateOfRelease);
            values.put(WatchedSQLiteHelper.COLUMN_DATE_WATCHED, dateWatched);
            values.put(WatchedSQLiteHelper.COLUMN_MOVIE_ID, movieId);
            values.put(WatchedSQLiteHelper.COLUMN_DATE_ADDED, dateFormat.format(new java.util.Date()));
            values.put(WatchedSQLiteHelper.COLUMN_RATING, rating);
            long insertId = database.insert(WatchedSQLiteHelper.TABLE_WATCHED, null, values);
            android.database.Cursor cursor2 = database.query(WatchedSQLiteHelper.TABLE_WATCHED, allColumns, WatchedSQLiteHelper.COLUMN_MOVIE_ID + " = " + insertId, null, null, null, null);
            cursor2.moveToFirst();
            newWatchedItem = cursorToWatchedItem(cursor2);
            cursor2.close();
        }
        cursor.close();
        return newWatchedItem;
    }

    public WatchedItem createWatchedItem(WatchedItem item){
        android.database.Cursor cursor = database.query(WatchedSQLiteHelper.TABLE_WATCHED, allColumns, WatchedSQLiteHelper.COLUMN_MOVIE_ID + " = " + item.getMovieID(), null, null, null, null);
        cursor.moveToFirst();
        WatchedItem newWatchedItem = null;
        if (cursor.getCount() == 0) {
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(WatchedSQLiteHelper.COLUMN_TITLE, item.getTitle());
            values.put(WatchedSQLiteHelper.COLUMN_PREVIEW_URL, item.getPreviewUrl());
            values.put(WatchedSQLiteHelper.COLUMN_NOTES, item.getNotes());
            values.put(WatchedSQLiteHelper.COLUMN_DATE_OF_RELEASE, item.getDateOfRelease());
            values.put(WatchedSQLiteHelper.COLUMN_DATE_WATCHED, item.getDateWatched());
            values.put(WatchedSQLiteHelper.COLUMN_MOVIE_ID, item.getMovieID());
            values.put(WatchedSQLiteHelper.COLUMN_DATE_ADDED, dateFormat.format(new java.util.Date()));
            values.put(WatchedSQLiteHelper.COLUMN_RATING, item.getRating());
            long insertId = database.insert(WatchedSQLiteHelper.TABLE_WATCHED, null, values);
            android.database.Cursor cursor2 = database.query(WatchedSQLiteHelper.TABLE_WATCHED, allColumns, WatchedSQLiteHelper.COLUMN_MOVIE_ID + " = " + insertId, null, null, null, null);
            cursor2.moveToFirst();
            newWatchedItem = cursorToWatchedItem(cursor2);
            cursor2.close();
        }
        cursor.close();
        return newWatchedItem;
    }

    public void deleteWatchedItem(WatchedItem WatchedItem) {
        int id = WatchedItem.getMovieID();
        database.delete(WatchedSQLiteHelper.TABLE_WATCHED, WatchedSQLiteHelper.COLUMN_MOVIE_ID + " = " + id, null);
    }

    public void deleteWatchedItemByMovieId(int movieId) {
        database.delete(WatchedSQLiteHelper.TABLE_WATCHED, WatchedSQLiteHelper.COLUMN_MOVIE_ID + " = " + movieId, null);
    }


    public java.util.List<WatchedItem> getAllWatchedItems() {
        java.util.List<WatchedItem> WatchedItems = new java.util.ArrayList<WatchedItem>();
        android.database.Cursor cursor = database.query(WatchedSQLiteHelper.TABLE_WATCHED, this.allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WatchedItem WatchedItem = cursorToWatchedItem(cursor);
            WatchedItems.add(WatchedItem);
            cursor.moveToNext();
        }
        cursor.close();
        return WatchedItems;
    }
}
