package red.project.uni.lu.lists.WatchedList;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WatchedAdaptater extends RecyclerView.Adapter<WatchedViewHolder> {

    List<WatchedItem> watchedItems;

    public WatchedAdaptater(List<WatchedItem> watchedItems) {
        this.watchedItems = watchedItems;
    }

    @NonNull
    @Override
    public WatchedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WatchedViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return watchedItems.size();
    }
}
