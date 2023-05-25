package red.project.uni.lu.lists.WatchedList;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import red.project.uni.lu.R;
import red.project.uni.lu.lists.WatchedList.WatchedDataSource;
import red.project.uni.lu.lists.WatchedList.WatchedViewHolder;
import red.project.uni.lu.ui.Watched.WatchedFragment;

public class WatchedAdaptater extends RecyclerView.Adapter<WatchedViewHolder> {

    List<WatchedItem> watchedItems;
    WatchedFragment wf;

    public WatchedAdaptater(List<WatchedItem> watchedItems, WatchedFragment wf) {
        this.watchedItems = watchedItems;
        this.wf = wf;
    }

    @NonNull
    @Override
    public WatchedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WatchedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.watched_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WatchedViewHolder holder, int position) {
        WatchedItem watchedItem = watchedItems.get(position);

        holder.title.setText(watchedItem.getTitle());
        holder.notes.setText(watchedItem.getNotes());
        holder.dateOfRelease.setText(watchedItem.getDateOfRelease());
        holder.dateWatched.setText(watchedItem.getDateWatched());
        holder.dateAdded.setText(watchedItem.getDateAdded());
        holder.ratingBar.setRating(watchedItem.getRating());
        Picasso.get().load(watchedItem.getPreviewUrl()).into(holder.preview);

        if (holder.notes != null) {
            holder.notes.setOnTouchListener((v, event) -> {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            });
        }

        if (holder.itemView != null) {
            holder.itemView.setOnLongClickListener(v -> {
                // Create a dialog to confirm the deletion
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Do you want to delete this movie from your To Watch list ?");
                builder.setCancelable(true);
                builder.setTitle("Delete movie ?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    // Delete the movie from the database
                    WatchedDataSource WatchedDataSource = new WatchedDataSource(v.getContext());
                    WatchedDataSource.open();
                    WatchedDataSource.deleteWatchedItemByMovieId(watchedItem.getMovieID());
                    WatchedDataSource.close();
                    // Delete the movie from the list
                    watchedItems.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, watchedItems.size());
                    // If the list is empty, display a text
                    if (watchedItems.size() == 0) {
                        // set the text to visible and the recycler view to invisible
                        wf.hasItem(false);
                    }
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            });

            holder.itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt("movieID", watchedItem.getMovieID());
                // Change the label of the action bar
                Navigation.findNavController(v).navigate(R.id.action_nav_Watched_to_FilmDetailledFragment, bundle);

            });
        }
    }

    @Override
    public int getItemCount() {
        return watchedItems.size();
    }
}
