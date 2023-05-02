package red.project.uni.lu;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToWatchAdapter extends RecyclerView.Adapter<ToWatchViewHolder> {

    Context context;
    List<ToWatchItem> toWatchItems;

    public ToWatchAdapter(Context context, List<ToWatchItem> toWatchItems) {
        this.context = context;
        this.toWatchItems = toWatchItems;
    }

    @NonNull
    @Override
    public ToWatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ToWatchViewHolder(LayoutInflater.from(context).inflate(R.layout.towatch_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ToWatchViewHolder holder, int position) {
        holder.preview.setImageResource(toWatchItems.get(position).getPreview());
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
