package red.project.uni.lu.lists.ToWatchList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import red.project.uni.lu.R;

public class ToWatchAdapter extends RecyclerView.Adapter<ToWatchViewHolder> {

    List<ToWatchItem> toWatchItems;

    public ToWatchAdapter(List<ToWatchItem> toWatchItems) {
        this.toWatchItems = toWatchItems;
    }

    @NonNull
    @Override
    public ToWatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ToWatchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.towatch_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ToWatchViewHolder holder, int position) {
        ToWatchItem toWatchItem = toWatchItems.get(position);
        holder.title.setText(toWatchItem.getTitle());
        holder.notes.setText(toWatchItem.getNotes());
        holder.notes.setMovementMethod(new ScrollingMovementMethod());
        holder.dateOfRelease.setText(toWatchItem.getDateOfRelease());
        holder.dateToWatch.setText(toWatchItem.getDateToWatch());
        holder.dateAdded.setText(toWatchItem.getDateAdded());
        Picasso.get().load(toWatchItem.getPreviewUrl()).into(holder.preview);

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
                    ToWatchDataSource toWatchDataSource = new ToWatchDataSource(v.getContext());
                    toWatchDataSource.open();
                    toWatchDataSource.deleteToWatchItemByMovieId(toWatchItem.getMovieID());
                    toWatchDataSource.close();
                    // Delete the movie from the list
                    toWatchItems.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, toWatchItems.size());
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            });

            holder.itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt("movieID", toWatchItem.getMovieID());
                Navigation.findNavController(v).navigate(R.id.action_nav_toWatch_to_FilmDetailledFragment, bundle);

            });
        }
    }

    @Override
    public int getItemCount() {
        return toWatchItems.size();
    }
}
