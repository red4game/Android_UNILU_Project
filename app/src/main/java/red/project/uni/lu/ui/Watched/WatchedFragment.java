package red.project.uni.lu.ui.Watched;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import red.project.uni.lu.R;
import red.project.uni.lu.lists.ToWatchList.ToWatchAdapter;
import red.project.uni.lu.lists.ToWatchList.ToWatchDataSource;
import red.project.uni.lu.lists.ToWatchList.ToWatchItem;
import red.project.uni.lu.lists.WatchedList.WatchedAdaptater;
import red.project.uni.lu.lists.WatchedList.WatchedDataSource;
import red.project.uni.lu.lists.WatchedList.WatchedItem;

public class WatchedFragment extends Fragment {

    private WatchedDataSource watchedDataSource;
    private WatchedAdaptater watchedAdaptater;
    private RecyclerView watchedList;
    private TextView noItemText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watched, container, false);

        watchedList = view.findViewById(R.id.recyclerWatched);
        noItemText = view.findViewById(R.id.NoItemText);
        watchedDataSource = new WatchedDataSource(getContext());
        watchedDataSource.open();
        List<WatchedItem> watchedItems = watchedDataSource.getAllWatchedItems();
        watchedDataSource.close();




        watchedAdaptater = new WatchedAdaptater(watchedItems, this);
        watchedAdaptater.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        watchedList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        watchedList.setAdapter(watchedAdaptater);
        watchedList.setOnTouchListener((v, event) -> {
            v.findViewById(R.id.WatchedItemNotes).getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        });

        hasItem(watchedAdaptater.getItemCount() > 0);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void hasItem(boolean hasItem){
        if(hasItem) {
            watchedList.setVisibility(View.VISIBLE);
            noItemText.setVisibility(View.GONE);
        } else {
            watchedList.setVisibility(View.GONE);
            noItemText.setVisibility(View.VISIBLE);
        }
    }
}