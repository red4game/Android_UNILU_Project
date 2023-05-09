package red.project.uni.lu.lists.HomeList;

public class HomeItem {
    String previewUrl;
    String title;
    String description;
    String dateOfRelease;
    String Rating;

    int id;

    public HomeItem() {}
    public HomeItem(String previewUrl, String title, String description, String dateOfRelease, String Rating) {
        this.previewUrl = previewUrl;
        this.title = title;
        this.description = description;
        this.dateOfRelease = dateOfRelease;
        this.Rating = Rating;
    }


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

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
