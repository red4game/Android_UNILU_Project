package red.project.uni.lu.lists.ToWatchList;

import android.media.Image;

import java.util.List;

public class ToWatchItem {
    String previewUrl;
    String title;
    String notes;
    String dateOfRelease;
    String dateToWatch;

    int id;
    int MovieID;

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(String dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public String getDateToWatch() {
        return dateToWatch;
    }

    public void setDateToWatch(String dateToWatch) {
        this.dateToWatch = dateToWatch;
    }

    public int getMovieID() {
        return MovieID;
    }

    public void setMovieID(int id) {
        this.MovieID = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public ToWatchItem(String previewUrl, String title, String notes, String dateOfRelease, String dateToWatch) {
        this.previewUrl = previewUrl;
        this.title = title;
        this.notes = notes;
        this.dateOfRelease = dateOfRelease;
        this.dateToWatch = dateToWatch;
    }

    public ToWatchItem() {}
}
