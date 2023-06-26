package red.project.uni.lu.lists.ToWatchList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ToWatchDataSource {

    private ToWatchSQLiteHelper dbHelper;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SQLiteDatabase database;
    private String[] allColumns = {ToWatchSQLiteHelper.COLUMN_MOVIE_ID, ToWatchSQLiteHelper.COLUMN_TITLE, ToWatchSQLiteHelper.COLUMN_PREVIEW_URL, ToWatchSQLiteHelper.COLUMN_NOTES, ToWatchSQLiteHelper.COLUMN_DATE_OF_RELEASE, ToWatchSQLiteHelper.COLUMN_DATE_TO_WATCH, ToWatchSQLiteHelper.COLUMN_DATE_ADDED};

    public ToWatchDataSource(Context context) {
        dbHelper = new ToWatchSQLiteHelper(context);
    }

    private ToWatchItem cursorToToWatchItem(android.database.Cursor cursor) {
        ToWatchItem toWatchItem = new ToWatchItem();
        toWatchItem.setMovieID(cursor.getInt(0));
        toWatchItem.setTitle(cursor.getString(1));
        toWatchItem.setPreviewUrl(cursor.getString(2));
        toWatchItem.setNotes(cursor.getString(3));
        toWatchItem.setDateOfRelease(cursor.getString(4));
        toWatchItem.setDateToWatch(cursor.getString(5));
        toWatchItem.setDateAdded(cursor.getString(6));
        return toWatchItem;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ToWatchItem createToWatchItem(String title, String previewUrl, String notes, String dateOfRelease, String dateToWatch, int movieId) {
        android.database.Cursor cursor = database.query(ToWatchSQLiteHelper.TABLE_TO_WATCH, allColumns, ToWatchSQLiteHelper.COLUMN_MOVIE_ID + " = " + movieId, null, null, null, null);
        cursor.moveToFirst();
        ToWatchItem newToWatchItem = null;
        if (cursor.getCount() == 0) {
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(ToWatchSQLiteHelper.COLUMN_MOVIE_ID, movieId);
            values.put(ToWatchSQLiteHelper.COLUMN_TITLE, title);
            values.put(ToWatchSQLiteHelper.COLUMN_PREVIEW_URL, previewUrl);
            values.put(ToWatchSQLiteHelper.COLUMN_NOTES, notes);
            values.put(ToWatchSQLiteHelper.COLUMN_DATE_OF_RELEASE, dateOfRelease);
            values.put(ToWatchSQLiteHelper.COLUMN_DATE_TO_WATCH, dateToWatch);


            values.put(ToWatchSQLiteHelper.COLUMN_DATE_ADDED, dateFormat.format(new java.util.Date()));
            long insertId = database.insert(ToWatchSQLiteHelper.TABLE_TO_WATCH, null, values);
            android.database.Cursor cursor2 = database.query(ToWatchSQLiteHelper.TABLE_TO_WATCH, allColumns, ToWatchSQLiteHelper.COLUMN_MOVIE_ID + " = " + insertId, null, null, null, null);
            cursor2.moveToFirst();
            newToWatchItem = cursorToToWatchItem(cursor2);
            cursor2.close();
        }
        cursor.close();
        return newToWatchItem;
    }

    public ToWatchItem createToWatchItem(ToWatchItem item){
        android.database.Cursor cursor = database.query(ToWatchSQLiteHelper.TABLE_TO_WATCH, allColumns, ToWatchSQLiteHelper.COLUMN_MOVIE_ID + " = " + item.getMovieID(), null, null, null, null);
        cursor.moveToFirst();
        ToWatchItem newToWatchItem = null;
        if (cursor.getCount() == 0) {
            android.content.ContentValues values = new android.content.ContentValues();
            values.put(ToWatchSQLiteHelper.COLUMN_MOVIE_ID, item.getMovieID());
            values.put(ToWatchSQLiteHelper.COLUMN_TITLE, item.getTitle());
            values.put(ToWatchSQLiteHelper.COLUMN_PREVIEW_URL, item.getPreviewUrl());
            values.put(ToWatchSQLiteHelper.COLUMN_NOTES, item.getNotes());
            values.put(ToWatchSQLiteHelper.COLUMN_DATE_OF_RELEASE, item.getDateOfRelease());
            values.put(ToWatchSQLiteHelper.COLUMN_DATE_TO_WATCH, item.getDateToWatch());
            values.put(ToWatchSQLiteHelper.COLUMN_DATE_ADDED, dateFormat.format(new java.util.Date()));
            long insertId = database.insert(ToWatchSQLiteHelper.TABLE_TO_WATCH, null, values);
            android.database.Cursor cursor2 = database.query(ToWatchSQLiteHelper.TABLE_TO_WATCH, allColumns, ToWatchSQLiteHelper.COLUMN_MOVIE_ID + " = " + insertId, null, null, null, null);
            cursor2.moveToFirst();
            newToWatchItem = cursorToToWatchItem(cursor2);
            cursor2.close();
        }
        cursor.close();
        return newToWatchItem;
    }

    public boolean isInToWatchList(int movieID){
        android.database.Cursor cursor = database.query(ToWatchSQLiteHelper.TABLE_TO_WATCH, allColumns, ToWatchSQLiteHelper.COLUMN_MOVIE_ID + " = " + movieID, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean ToWatchToday(){
        String today = dateFormat.format(new java.util.Date());
        android.database.Cursor cursor = database.query(ToWatchSQLiteHelper.TABLE_TO_WATCH, allColumns, ToWatchSQLiteHelper.COLUMN_DATE_TO_WATCH + " = " + today, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void deleteToWatchItem(ToWatchItem toWatchItem) {
        int id = toWatchItem.getMovieID();
        database.delete(ToWatchSQLiteHelper.TABLE_TO_WATCH, ToWatchSQLiteHelper.COLUMN_MOVIE_ID + " = " + id, null);
    }

    public void deleteToWatchItemByMovieId(int movieId) {
        database.delete(ToWatchSQLiteHelper.TABLE_TO_WATCH, ToWatchSQLiteHelper.COLUMN_MOVIE_ID + " = " + movieId, null);
    }


    public java.util.List<ToWatchItem> getAllToWatchItems() {
        java.util.List<ToWatchItem> toWatchItems = new java.util.ArrayList<ToWatchItem>();
        android.database.Cursor cursor = database.query(ToWatchSQLiteHelper.TABLE_TO_WATCH, this.allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ToWatchItem toWatchItem = cursorToToWatchItem(cursor);
            toWatchItems.add(toWatchItem);
            cursor.moveToNext();
        }
        cursor.close();
        return toWatchItems;
    }
}
