package red.project.uni.lu.lists.WatchedList;

public class WatchedItem {
    String previewUrl;
    String title;
    String notes;
    String dateOfRelease;
    String dateWatched;

    String dateAdded;

    int MovieID;

    int rating;

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

    public String getDateWatched() {
        return dateWatched;
    }

    public void setDateWatched(String dateWatched) {
        this.dateWatched = dateWatched;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getMovieID() {
        return MovieID;
    }

    public void setMovieID(int movieID) {
        MovieID = movieID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public WatchedItem() {}
    public WatchedItem(String previewUrl, String title, String notes, String dateOfRelease, String dateWatched, String dateAdded, int movieID, int rating) {
        this.previewUrl = previewUrl;
        this.title = title;
        this.notes = notes;
        this.dateOfRelease = dateOfRelease;
        this.dateWatched = dateWatched;
        this.dateAdded = dateAdded;
        this.MovieID = movieID;
        this.rating = rating;
    }
}
