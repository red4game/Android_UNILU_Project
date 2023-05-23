package red.project.uni.lu.lists.WatchedList;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import red.project.uni.lu.R;

public class WatchedViewHolder extends RecyclerView.ViewHolder {

    ImageView preview;
    TextView title, notes, dateWatched, dateOfRelease, dateAdded;
    RatingBar ratingBar;

    public WatchedViewHolder(@NonNull View itemView) {
        super(itemView);
        preview = itemView.findViewById(R.id.WatchedItemPreview);
        title = itemView.findViewById(R.id.WatchedItemTitle);
        notes = itemView.findViewById(R.id.WatchedItemNotes);
        dateWatched = itemView.findViewById(R.id.WatchedItemDateWatched);
        dateOfRelease = itemView.findViewById(R.id.WatchedItemDateOfRelease);
        dateAdded = itemView.findViewById(R.id.WatchedItemDateAdded);
        ratingBar = itemView.findViewById(R.id.WatchedItemRatingBar);
    }
}
