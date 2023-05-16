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
        // TODO : when the view is changed, put good data in the view
    }

    @Override
    public int getItemCount() {
        return toWatchItems.size();
    }
}
