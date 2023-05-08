package red.project.uni.lu.lists.ToWatchList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
        Picasso.get().load(toWatchItems.get(position).getPreview()).into(holder.preview);
        holder.title.setText(toWatchItems.get(position).getTitle());
        holder.description.setText(toWatchItems.get(position).getDescription());
        holder.date.setText(toWatchItems.get(position).getDateOfRelease());
        holder.genre.setText(toWatchItems.get(position).getGenre());
        holder.director.setText(toWatchItems.get(position).getDirector());
    }

    @Override
    public int getItemCount() {
        return toWatchItems.size();
    }
}
