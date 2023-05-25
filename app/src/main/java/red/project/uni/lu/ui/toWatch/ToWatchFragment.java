package red.project.uni.lu.ui.toWatch;

import android.app.AlertDialog;
import android.app.Dialog;
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

public class ToWatchFragment extends Fragment {

    private ToWatchDataSource toWatchDataSource;
    private ToWatchAdapter toWatchAdapter;
    private RecyclerView toWatchList;
    private TextView noItemText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_towatch, container, false);

        toWatchList = view.findViewById(R.id.recyclerToWatch);
        noItemText = view.findViewById(R.id.NoItemText);

        toWatchDataSource = new ToWatchDataSource(getContext());
        toWatchDataSource.open();
        List<ToWatchItem> toWatchItems = toWatchDataSource.getAllToWatchItems();
        toWatchDataSource.close();




        toWatchAdapter = new ToWatchAdapter(toWatchItems, this);
        toWatchAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        toWatchList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        toWatchList.setAdapter(toWatchAdapter);
        toWatchList.setOnTouchListener((v, event) -> {
            v.findViewById(R.id.ToWatchItemNotes).getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        });

        hasItem(toWatchAdapter.getItemCount() > 0);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void hasItem(boolean hasItem){
        if(hasItem) {
            toWatchList.setVisibility(View.VISIBLE);
            noItemText.setVisibility(View.GONE);
        } else {
            toWatchList.setVisibility(View.GONE);
            noItemText.setVisibility(View.VISIBLE);
        }
    }
}