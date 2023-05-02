package red.project.uni.lu;

import android.media.Image;

public class ToWatchItem {
    String previewUrl;
    String title;
    String description;
    String dateOfRelease;
    String genre;
    String director;
    // maybe add other fields


    public ToWatchItem(String previewUrl, String title, String description, String dateOfRelease, String genre, String director) {
        this.previewUrl = previewUrl;
        this.title = title;
        this.description = description;
        this.dateOfRelease = dateOfRelease;
        this.genre = genre;
        this.director = director;
    }

    public String getPreview() {
        return previewUrl;
    }

    public void setPreview(String preview) {
        this.previewUrl = preview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(String dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
