package red.project.uni.lu.lists.HomeList;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ArrowKeyMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import red.project.uni.lu.R;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<HomeItem> homeItems;
    static final int LOAD = 0;
    static final int ITEM = 1;
    boolean LoadingActive = false;



    public HomeAdapter() {
        this.homeItems = new ArrayList<>();
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        if (viewType == ITEM) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.home_item_view, parent, false);
            return new HomeViewHolder(itemView);
        } else {
            View loadView = LayoutInflater.from(context).inflate(R.layout.home_load_item_view, parent, false);
            return new HomeProgressViewHolder(loadView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeItem item = homeItems.get(position);
        if (getItemViewType(position) == ITEM) {
            Picasso.get().load(item.getPreviewUrl()).into(((HomeViewHolder) holder).HomeItemPreview);
            HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
            homeViewHolder.HomeItemTitle.setText(item.getTitle());
            homeViewHolder.HomeItemDescription.setText(item.getDescription());
            homeViewHolder.HomeItemDescription.setMovementMethod(new ArrowKeyMovementMethod());
            homeViewHolder.HomeItemRating.setText(item.getRating());

            homeViewHolder.HomeItemDescription.setOnTouchListener((v, event) -> {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            });
        } else {
            HomeProgressViewHolder homeProgressViewHolder = (HomeProgressViewHolder) holder;
            homeProgressViewHolder.progressBar.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (getItemViewType(position) == LOAD) return;

            Bundle bundle = new Bundle();
            bundle.putInt("movieID", item.getId());
            Navigation.findNavController(v).navigate(R.id.action_nav_home_to_noWatchedDetailledFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount()-1 && LoadingActive)  ? LOAD : ITEM;
    }

    public void add(HomeItem item){
        homeItems.add(item);
        notifyItemInserted(homeItems.size()-1);
    }

    public void replace(List<HomeItem> homeItems) {
        this.homeItems.clear();
        this.homeItems.addAll(homeItems);
        notifyDataSetChanged();
    }

    public void addAll(List<HomeItem> homeItems) {
        for (HomeItem item : homeItems) {
            add(item);
        }
    }

    public void addLoading() {
        LoadingActive = true;
        add(new HomeItem());
    }

    public void removeLoading(){
        LoadingActive = false;
        int pos = homeItems.size()-1;
        HomeItem item = homeItems.get(pos);
        if (item != null) {
            homeItems.remove(pos);
            notifyItemRemoved(pos);
        }

    }
}
