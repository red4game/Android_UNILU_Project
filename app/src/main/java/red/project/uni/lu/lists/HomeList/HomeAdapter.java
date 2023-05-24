package red.project.uni.lu.lists.HomeList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
        this.LoadingActive = false;
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

            HomeViewHolder homeViewHolder =  new HomeViewHolder(holder.itemView);
            

            if (item.getTitle() != null) {
                homeViewHolder.HomeItemTitle.setText(item.getTitle());
            }
            if (item.getDescription() != null) {
                homeViewHolder.HomeItemDescription.setText(item.getDescription());
                homeViewHolder.HomeItemDescription.setMovementMethod(new ScrollingMovementMethod());
            }
            if (item.getRating() != null) {
                homeViewHolder.HomeItemRating.setText(item.getRating());
            }
            if (item.getDateOfRelease() != null) {
                homeViewHolder.HomeItemRelease.setText(item.getDateOfRelease());
            }
            if (item.getVoteCount() != null) {
                homeViewHolder.HomeItemVoteCount.setText(item.getVoteCount());
            }
            if (item.getPreviewUrl() != null) {
                Picasso.get().load(item.getPreviewUrl()).into(homeViewHolder.HomeItemPreview);
            }

            if (homeViewHolder.HomeItemDescription != null) {
                homeViewHolder.HomeItemDescription.setOnTouchListener((v, event) -> {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                });
            }
        } else {
            HomeProgressViewHolder homeProgressViewHolder = (HomeProgressViewHolder) holder;
            homeProgressViewHolder.progressBar.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (getItemViewType(position) != LOAD) {
                Bundle bundle = new Bundle();
                bundle.putInt("movieID", item.getId());
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_FilmDetailledFragment, bundle);
            }
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
        if (item.getRating() != null && item.getTitle()!=null && item.getDateOfRelease()!=null && item.getDescription()!=null && item.getPreviewUrl() != null){
            homeItems.add(item);
            notifyItemInserted(homeItems.size()-1);
        }


    }

    public void addNullable(HomeItem item){
        homeItems.add(item);
        notifyItemInserted(homeItems.size()-1);
    }

    public void replace(List<HomeItem> homeItems) {
        this.homeItems.clear();
        this.homeItems.addAll(homeItems);
        notifyDataSetChanged();
    }

    public void clear() {
        homeItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<HomeItem> homeItems) {
        for (HomeItem item : homeItems) {
            add(item);
        }
    }

    public void addLoading() {
        LoadingActive = true;
        addNullable(new HomeItem());
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

    public void setLoadingActiveFalse(){
        LoadingActive = false;
    }

    private int nullConditionTest(String homeItem,String t1){
        if (homeItem == null && t1 == null){
            return 0;
        } else if (homeItem == null) {
            return 1;
        } else if (t1 == null) {
            return 1;
        }
        return 1000;
    }

    private int nullConditionTestReverse(String homeItem,String t1){
        if (homeItem == null && t1 == null){
            return 0;
        } else if (homeItem == null) {
            return -1;
        } else if (t1 == null) {
            return -1;
        }
        return 1000;
    }

    public void sortByTitleAsc(){
        homeItems.sort(new Comparator<HomeItem>() {
            @Override
            public int compare(HomeItem homeItem, HomeItem t1) {
                int NullTest = nullConditionTest(homeItem.getTitle(),t1.getTitle());
                if (NullTest != 1000){
                    return NullTest;
                } else {
                    return homeItem.getTitle().compareTo(t1.getTitle());
                }

            }
        });
        notifyDataSetChanged();
    }

    public void sortByTitleDsc(){
        homeItems.sort(new Comparator<HomeItem>() {
            @Override
            public int compare(HomeItem homeItem, HomeItem t1) {
                int NullTest = nullConditionTestReverse(homeItem.getTitle(),t1.getTitle());
                if (NullTest != 1000){
                    return NullTest;
                } else {
                    return homeItem.getTitle().compareTo(t1.getTitle());
                }
            }
        });
        Collections.reverse(homeItems);
        notifyDataSetChanged();
    }

    public void sortByRatingAsc(){
        homeItems.sort(new Comparator<HomeItem>() {
            @Override
            public int compare(HomeItem homeItem, HomeItem t1) {
                int NullTest = nullConditionTest(homeItem.getRating(),t1.getRating());
                if (NullTest != 1000){
                    return NullTest;
                } else {
                    return homeItem.getRating().compareTo(t1.getRating());
                }
            }
        });
        notifyDataSetChanged();
    }

    public void sortByRatingDsc(){
        homeItems.sort(new Comparator<HomeItem>() {
            @Override
            public int compare(HomeItem homeItem, HomeItem t1) {
                int NullTest = nullConditionTestReverse(homeItem.getRating(),t1.getRating());
                if (NullTest != 1000){
                    return NullTest;
                } else {
                    return homeItem.getRating().compareTo(t1.getRating());
                }
            }
        });
        Collections.reverse(homeItems);
        notifyDataSetChanged();
    }

    public void sortByDateAsc(){
        homeItems.sort(new Comparator<HomeItem>() {
            @Override
            public int compare(HomeItem homeItem, HomeItem t1) {
                int NullTest = nullConditionTest(homeItem.getDateOfRelease(),t1.getDateOfRelease());
                if (NullTest != 1000){
                    return NullTest;
                } else {
                    try {
                        LocalDate dateItem = LocalDate.parse(homeItem.getDateOfRelease());
                        LocalDate date1 = LocalDate.parse(t1.getDateOfRelease());
                        return dateItem.compareTo(date1);
                    } catch (DateTimeParseException e){
                        return -1;
                    }
                }
            }
        });
        notifyDataSetChanged();
    }

    public void sortByDateDsc(){
        homeItems.sort(new Comparator<HomeItem>() {
            @Override
            public int compare(HomeItem homeItem, HomeItem t1) {
                int NullTest = nullConditionTestReverse(homeItem.getDateOfRelease(),t1.getDateOfRelease());
                if (NullTest != 1000){
                    return NullTest;
                } else {
                    try {
                        LocalDate dateItem = LocalDate.parse(homeItem.getDateOfRelease());
                        LocalDate date1 = LocalDate.parse(t1.getDateOfRelease());
                        return dateItem.compareTo(date1);
                    } catch (DateTimeParseException e){
                        return -1;
                    }
                }
            }
        });
        Collections.reverse(homeItems);
        notifyDataSetChanged();
    }

    public void sortByVoteAsc(){
        homeItems.sort(new Comparator<HomeItem>() {
            @Override
            public int compare(HomeItem homeItem, HomeItem t1) {
                int NullTest = nullConditionTest(homeItem.getVoteCount(),t1.getVoteCount());
                if (NullTest != 1000){
                    return NullTest;
                } else {
                    try {
                        return Integer.parseInt(homeItem.getVoteCount()) - Integer.parseInt(t1.getVoteCount());
                    } catch (NumberFormatException e){
                        return -1;
                    }
                }
            }
        });
        notifyDataSetChanged();
    }

    public void sortByVoteDsc(){
        homeItems.sort(new Comparator<HomeItem>() {
            @Override
            public int compare(HomeItem homeItem, HomeItem t1) {
                int NullTest = nullConditionTestReverse(homeItem.getVoteCount(),t1.getVoteCount());
                if (NullTest != 1000){
                    return NullTest;
                } else {
                    try {
                        return Integer.parseInt(homeItem.getVoteCount()) - Integer.parseInt(t1.getVoteCount());
                    } catch (NumberFormatException e){
                        return -1;
                    }
                }
            }
        });
        Collections.reverse(homeItems);
        notifyDataSetChanged();
    }
}
