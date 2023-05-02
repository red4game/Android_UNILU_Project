package red.project.uni.lu.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import red.project.uni.lu.R;
import red.project.uni.lu.ToWatchAdapter;
import red.project.uni.lu.ToWatchItem;
import red.project.uni.lu.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        RecyclerView toWatchRecyclerView = view.findViewById(R.id.recyclerToWatch);

        List<ToWatchItem> toWatchItems = new ArrayList<>();
        toWatchItems.add(new ToWatchItem(R.drawable.ic_launcher_background, "Title", "Description", "Date", "Genre", "Director"));
        toWatchItems.add(new ToWatchItem(R.drawable.ic_launcher_background, "Other Title", "whatever Description", "somewhat Date", "a normal Genre", "what a Director"));

        toWatchRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        toWatchRecyclerView.setAdapter(new ToWatchAdapter(toWatchItems));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}